package com.itee.bingsheng.pay.base.utils.http;

import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.nio.charset.Charset;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpClientUtil {
	
	private static Logger logger=Logger.getLogger(HttpClientUtil.class);
	
	private HttpClientUtil() {
	}

	public static String sendGetRequest(String reqURL) {
		String respContent = "通信失败"; // 响应内容
		HttpClient httpClient = new DefaultHttpClient(); // 创建默认的httpClient实例
		// 设置代理服务器
//		 httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//		 new HttpHost("218.17.158.37", 8888));
		
//		 httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//		 new HttpHost("127.0.0.1", 8080));
		
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 30000); // 连接超时10s
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				40000); // 读取超时20s
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				// respCharset=EntityUtils.getContentCharSet(entity)也可以获取响应编码,但从4.1.3开始不建议使用这种方式
				Charset respCharset = ContentType.getOrDefault(entity)
						.getCharset();
				respContent = EntityUtils.toString(entity, respCharset);
				// Consume response content
				EntityUtils.consume(entity);
			}
			System.out.println("-------------------------------------------------------------------------------------------");
			StringBuilder respHeaderDatas = new StringBuilder();
			for (Header header : response.getAllHeaders()) {
				respHeaderDatas.append(header.toString()).append("\r\n");
			}
			String respStatusLine = response.getStatusLine().toString(); // HTTP应答状态行信息
			String respHeaderMsg = respHeaderDatas.toString().trim(); // HTTP应答报文头信息
			String respBodyMsg = respContent; // HTTP应答报文体信息
			System.out.println("HTTP应答完整报文=[" + respStatusLine + "\r\n" + respHeaderMsg + "\r\n\r\n" + respBodyMsg + "]");
			System.out.println("-------------------------------------------------------------------------------------------");
		} catch (ConnectTimeoutException cte) {
			// Should catch ConnectTimeoutException, and don`t catch
			// org.apache.http.conn.HttpHostConnectException
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (ClientProtocolException cpe) {
			// 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
			logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
		} catch (ParseException pe) {
			logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
		} catch (IOException ioe) {
			// 该异常通常是网络原因引起的,如HTTP服务器未启动等
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			// 关闭连接,释放资源
			httpClient.getConnectionManager().shutdown();
		}
		return respContent;
	}

	/**
	 * 发送HTTP_POST请求
	 * 
	 * @see 1)该方法允许自定义任何格式和内容的HTTP请求报文体
	 * @see 2)该方法会自动关闭连接,释放资源
	 * @see 3)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 4)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自动对其转码
	 * @see 5)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html;
	 *      charset=GBK]的charset值
	 * @param reqURL
	 *            请求地址
	 * @param reqData
	 *            请求参数,若有多个参数则应拼接为param11=value11&22=value22&33=value33的形式
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,此参数为必填项(不能为""或null)
	 * @return 远程主机响应正文
	 */
	public static Map<String, String> sendPostRequest(String reqURL, String reqData,
			String encodeCharset) {
		String reseContent = "通信失败";
		
		Map<String, String> map=new HashMap<String, String>();
		
		
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 30000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				40000);
		HttpPost httpPost = new HttpPost(reqURL);
		// 由于下面使用的是new
		// StringEntity(....),所以默认发出去的请求报文头中CONTENT_TYPE值为text/plain;
		// charset=ISO-8859-1
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded; charset=" + encodeCharset);
		try {
			httpPost.setEntity(new StringEntity(reqData == null ? "" : reqData,
					encodeCharset));
			
		    
            // 创建一个本地Cookie存储的实例
            CookieStore cookieStore = new BasicCookieStore();
            //创建一个本地上下文信息
            HttpContext localContext = new BasicHttpContext();
            //在本地上下问中绑定一个本地存储
            localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
            
            HttpResponse response = httpClient.execute(httpPost,localContext);
             //获取本地信息
             HttpEntity entity = response.getEntity();
//             System.out.println(response.getStatusLine());
             if (entity != null) {
//                 System.out.println("Response content length: " + entity.getContentLength());
             }
             //获取cookie中的各种信息
             List<Cookie> cookiess = cookieStore.getCookies();
             for (int i = 0; i < cookiess.size(); i++) {
//                 System.out.println("Local cookie: " + cookiess.get(i));
                 if("RUANKO_TOKEN".equals(cookiess.get(i).getName())){
//                     System.out.println(cookiess.get(i).getValue());
                     map.put("RUANKO_TOKEN", cookiess.get(i).getValue());
                 }
                 if("ruanko_auth".equals(cookiess.get(i).getName())){
//                     System.out.println(cookiess.get(i).getValue());
                     map.put("ruanko_auth", cookiess.get(i).getValue());
                 }
                 if("ruanko_user".equals(cookiess.get(i).getName())){
//                     System.out.println(cookiess.get(i).getValue());
                     map.put("ruanko_user", cookiess.get(i).getValue());
                 }
             }
             //获取消息头的信息
             Header[] headers = response.getAllHeaders();
             for (int i = 0; i<headers.length; i++) {
//                 System.out.println(headers[i]);
             }			
			
			if (null != entity) {
				reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
				map.put("reseContent",reseContent);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return map;
	}

	/**
	 * 发送HTTP_POST_SSL请求
	 * 
	 * @see 1)该方法会自动关闭连接,释放资源
	 * @see 2)该方法亦可处理普通的HTTP_POST请求
	 * @see 3)当处理HTTP_POST_SSL请求时,默认请求的是对方443端口,除非reqURL参数中指明了SSL端口
	 * @see 4)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 5)请求参数含中文等特殊字符时,可直接传入本方法,并指明其编码字符集encodeCharset参数,方法内部会自动对其转码
	 * @see 6)方法内部会自动注册443作为SSL端口,若实际使用中reqURL指定的SSL端口非443,可自行尝试更改方法内部注册的SSL端口
	 * @see 7)该方法在解码响应报文时所采用的编码,取自响应消息头中的[Content-Type:text/html;
	 *      charset=GBK]的charset值
	 * @see 8)若响应消息头中未指定Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1
	 * @param reqURL
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param encodeCharset
	 *            编码字符集,编码请求数据时用之,当其为null时,则取HttpClient内部默认的ISO-8859-1编码请求参数
	 * @return 远程主机响应正文
	 */
	public static String sendPostSSLRequest(String reqURL,
			Map<String, String> params, String encodeCharset) {
		String responseContent = "通信失败";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				20000);
		// 创建TrustManager()
		// 用于解决javax.net.ssl.SSLPeerUnverifiedException: peer not authenticated
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		// 创建HostnameVerifier
		// 用于解决javax.net.ssl.SSLException: hostname in certificate didn't match:
		// <123.125.97.66> != <123.125.97.241>
		X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
			@Override
			public void verify(String host, SSLSocket ssl) throws IOException {
			}

			@Override
			public void verify(String host, X509Certificate cert)
					throws SSLException {
			}

			@Override
			public void verify(String host, String[] cns, String[] subjectAlts)
					throws SSLException {
			}

			@Override
			public boolean verify(String arg0, SSLSession arg1) {
				return true;
			}
		};
		try {
			// TLS1.0与SSL3.0基本上没有太大的差别,可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
			SSLContext sslContext = SSLContext
					.getInstance(SSLSocketFactory.TLS);
			// 使用TrustManager来初始化该上下文,TrustManager只是被SSL的Socket所使用
			sslContext.init(null, new TrustManager[] { trustManager }, null);
			// 创建SSLSocketFactory
			SSLSocketFactory socketFactory = new SSLSocketFactory(sslContext,
					hostnameVerifier);
			// 通过SchemeRegistry将SSLSocketFactory注册到HttpClient上
			httpClient.getConnectionManager().getSchemeRegistry()
					.register(new Scheme("https", 443, socketFactory));
			// 创建HttpPost
			HttpPost httpPost = new HttpPost(reqURL);
			// 由于下面使用的是new
			// UrlEncodedFormEntity(....),所以这里不需要手工指定CONTENT_TYPE为application/x-www-form-urlencoded
			// 因为在查看了HttpClient的源码后发现,UrlEncodedFormEntity所采用的默认CONTENT_TYPE就是application/x-www-form-urlencoded
			// httpPost.setHeader(HTTP.CONTENT_TYPE,
			// "application/x-www-form-urlencoded; charset=" + encodeCharset);
			// 构建POST请求的表单参数
			if (null != params) {
				List<NameValuePair> formParams = new ArrayList<NameValuePair>();
				for (Map.Entry<String, String> entry : params.entrySet()) {
					formParams.add(new BasicNameValuePair(entry.getKey(), entry
							.getValue()));
				}
				httpPost.setEntity(new UrlEncodedFormEntity(formParams,
						encodeCharset));
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				responseContent = EntityUtils.toString(entity, ContentType
						.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return responseContent;
	}
	
/*	public static void main(String[] args) {
	    Map<String, String> respron=sendPostRequest("http://login.kocla.com/common/login","userName=zs_teac&password=zhangsi5213","UTF-8");
	    
	    
	    for(Map.Entry<String, String> map:respron.entrySet() ){
	        System.out.println(map.getKey());
	        System.out.println(map.getValue());
	        if("RUANKO_TOKEN".equals(map.getKey())){
	            String url="http://login.ruanko.com/writeCookie.jsp?domain=ruanko.com&key=RUANKO_TOKEN&value="+map.getValue();
	          String name=  sendGetRequest(url);
	          System.out.println("url:"+url+"-------String:"+name);
	        }
	    }
	    
	   try {
        TimeUnit.SECONDS.sleep(3000);
    }
    catch (InterruptedException e) {
        e.printStackTrace();
    }
	    sendGetRequest("http://open.kocla.com:8080/kocla/home/login!login.action?login_type=login");
	    
	    
	    
//	    
	    
//        System.out.println("respron:"+respron);
    }*/
	
	
	
	
	
	/**
	 * 发送HTTP_GET请求
	 * 
	 * @see 1)该方法会自动关闭连接,释放资源
	 * @see 2)方法内设置了连接和读取超时时间,单位为毫秒,超时或发生其它异常时方法会自动返回"通信失败"字符串
	 * @see 3)请求参数含中文时,经测试可直接传入中文,HttpClient会自动编码发给Server,应用时应根据实际效果决定传入前是否转码
	 * @see 4)该方法会自动获取到响应消息头中[Content-Type:text/html;
	 *      charset=GBK]的charset值作为响应报文的解码字符集
	 * @see 5)若响应消息头中无Content-Type属性,则会使用HttpClient内部默认的ISO-8859-1作为响应报文的解码字符集
	 *            请求地址(含参数)
	 * @return 远程主机响应正文
	 */
	public static String sendGetRequestCourseTools(String reqURL) {
		String respContent = "通信失败"; // 响应内容
		ClientConnectionManager connManager = new PoolingClientConnectionManager();
		HttpClient httpClient = new DefaultHttpClient(connManager); // 创建默认的httpClient实例
		// 设置代理服务器
//		 httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//		 new HttpHost("127.0.0.1", 8080));
//		 httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY,
//				 new HttpHost("121.40.236.238", 80));
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 10000); // 连接超时10s
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
				20000); // 读取超时20s
		HttpGet httpGet = new HttpGet(reqURL); // 创建org.apache.http.client.methods.HttpGet
		try {
			HttpResponse response = httpClient.execute(httpGet); // 执行GET请求
			HttpEntity entity = response.getEntity(); // 获取响应实体
			if (null != entity) {
				// respCharset=EntityUtils.getContentCharSet(entity)也可以获取响应编码,但从4.1.3开始不建议使用这种方式
				Charset respCharset = ContentType.getOrDefault(entity)
						.getCharset();
				respContent = EntityUtils.toString(entity, respCharset);
				// Consume response content
				EntityUtils.consume(entity);
			}
			logger.info("-------------------------------------------------------------------------------------------");
			StringBuilder respHeaderDatas = new StringBuilder();
			for (Header header : response.getAllHeaders()) {
				respHeaderDatas.append(header.toString()).append("\r\n");
			}
			String respStatusLine = response.getStatusLine().toString(); // HTTP应答状态行信息
			String respHeaderMsg = respHeaderDatas.toString().trim(); // HTTP应答报文头信息
			String respBodyMsg = respContent; // HTTP应答报文体信息
			logger.info("HTTP应答完整报文=[" + respStatusLine + "\r\n"
					+ respHeaderMsg + "\r\n\r\n" + respBodyMsg + "]");
			logger.info("-------------------------------------------------------------------------------------------");
		} catch (ConnectTimeoutException cte) {
			// Should catch ConnectTimeoutException, and don`t catch
			// org.apache.http.conn.HttpHostConnectException
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (ClientProtocolException cpe) {
			// 该异常通常是协议错误导致:比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合HTTP协议要求等
			logger.error("请求通信[" + reqURL + "]时协议异常,堆栈轨迹如下", cpe);
		} catch (ParseException pe) {
			logger.error("请求通信[" + reqURL + "]时解析异常,堆栈轨迹如下", pe);
		} catch (IOException ioe) {
			// 该异常通常是网络原因引起的,如HTTP服务器未启动等
			logger.error("请求通信[" + reqURL + "]时网络异常,堆栈轨迹如下", ioe);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			// 关闭连接,释放资源
			httpClient.getConnectionManager().shutdown();
		}
		return respContent;
	}
	
	
	/**
	 * 发送HTTP_Post 请求  返回文本
	 * @Title: sendPostRequestString 
	 * @Author: liujianjun
	 * @Description: 
	 * @param reqURL
	 * @param
	 * @param encodeCharset
	 * @return 
	 * @date: 2015年8月18日 上午10:01:00 
	 * @return String
	 */
	public static String sendPostRequestString(String reqURL, String  postJson, String encodeCharset) {
		String reseContent = "通信失败";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
		HttpPost httpPost = new HttpPost(reqURL);
		// 由于下面使用的是new
		// StringEntity(....),所以默认发出去的请求报文头中CONTENT_TYPE值为text/plain;
		// charset=ISO-8859-1
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
		if (StringUtils.isNotBlank(encodeCharset)) {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=" + encodeCharset);
		}else {
			httpPost.setHeader(HTTP.CONTENT_TYPE, "application/x-www-form-urlencoded; charset=UTF-8");
		}
		try {
			// 构建POST请求的表单参数
			if (StringUtils.isNotBlank(postJson)) {
				  StringEntity entity = new StringEntity(postJson.toString(),"utf-8");//解决中文乱码问题    
				  entity.setContentEncoding("UTF-8");    
				  entity.setContentType("application/json");    
				  httpPost.setEntity(entity);    
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
          
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return reseContent;
	}

	/**
	 * 发送HTTP_Post 请求  返回文本
	 * @Title: sendPostRequestString
	 * @Author: liujianjun
	 * @Description:
	 * @param reqURL
	 * @param
	 * @param encodeCharset
	 * @return
	 * @date: 2015年8月18日 上午10:01:00
	 * @return String
	 */
	public static String sendPostRequestString2(String reqURL, String  postJson,String encodeCharset) {
		String reseContent = "通信失败";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);
		HttpPost httpPost = new HttpPost(reqURL);
		// 由于下面使用的是new
		// StringEntity(....),所以默认发出去的请求报文头中CONTENT_TYPE值为text/plain;
		// charset=ISO-8859-1
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
		if (StringUtils.isNotBlank(encodeCharset)) {
			httpPost.setHeader(HTTP.CONTENT_TYPE,
					"application/json; charset=" + encodeCharset);
		}else {
			httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json; charset=UTF-8");
		}
		try {
			// 构建POST请求的表单参数
			if (StringUtils.isNotBlank(postJson)) {
				StringEntity entity = new StringEntity(postJson.toString(),"utf-8");//解决中文乱码问题
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return reseContent;
	}
	/**
	 * 发送HTTP_Post 请求  返回文本
	 * @Title: sendPostRequestString
	 * @Author: liujianjun
	 * @Description:
	 * @param reqURL
	 * @param
	 * @param encodeCharset
	 * @return
	 * @date: 2015年8月18日 上午10:01:00
	 * @return String
	 */
	public static String sendPostRequestString3(String reqURL, String  postJson,String encodeCharset,Map<String,Object> headers) {
		String reseContent = "通信失败";
		HttpClient httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,60000);
		HttpPost httpPost = new HttpPost(reqURL);
		// 由于下面使用的是new
		// StringEntity(....),所以默认发出去的请求报文头中CONTENT_TYPE值为text/plain;
		// charset=ISO-8859-1
		// 这就有可能会导致服务端接收不到POST过去的参数,比如运行在Tomcat6.0.36中的Servlet,所以我们手工指定CONTENT_TYPE头消息
		if (StringUtils.isNotBlank(encodeCharset)) {
			httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json; charset=" + encodeCharset);
		}else {
			httpPost.setHeader(HTTP.CONTENT_TYPE,"application/json; charset=UTF-8");
		}
		//附加header
		if (headers!= null&&headers.size()>0) {
			for(Map.Entry<String,Object> e:headers.entrySet()){
				httpPost.setHeader(e.getKey(),e.getValue().toString());
			}
		}
		try {
			// 构建POST请求的表单参数
			if (StringUtils.isNotBlank(postJson)) {
				StringEntity entity = new StringEntity(postJson.toString(),"utf-8");//解决中文乱码问题
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				reseContent = EntityUtils.toString(entity, ContentType.getOrDefault(entity).getCharset());
				EntityUtils.consume(entity);
			}
		} catch (ConnectTimeoutException cte) {
			logger.error("请求通信[" + reqURL + "]时连接超时,堆栈轨迹如下", cte);
		} catch (SocketTimeoutException ste) {
			logger.error("请求通信[" + reqURL + "]时读取超时,堆栈轨迹如下", ste);
		} catch (Exception e) {
			logger.error("请求通信[" + reqURL + "]时偶遇异常,堆栈轨迹如下", e);
		} finally {
			httpClient.getConnectionManager().shutdown();
		}
		return reseContent;
	}
}