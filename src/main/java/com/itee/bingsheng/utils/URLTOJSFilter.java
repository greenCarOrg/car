package com.itee.bingsheng.utils;

import java.io.IOException;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 2017年3月21日
 * 临时解决url地址的js注入攻击，该方案不是最终解决方案，只是临时处理可以使用
 * 更好的解决方案，需要考虑更多的问题，待定
 *
 */
public class URLTOJSFilter implements Filter {

   public void destroy() {

   }

   public void doFilter(ServletRequest req, ServletResponse resp,
           FilterChain chain) throws IOException, ServletException {

       HttpServletRequest request = (HttpServletRequest) req;

       HttpServletResponse response = (HttpServletResponse) resp;

       Map<String, String[]> parameterMap = request.getParameterMap();
       //验证是否请求的URL里是否有参数
       for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
           String[] value = entry.getValue();
           if (value != null) {
               int strLength = value.length;
               for (int i = 0; i < strLength; i++) {
                   boolean result = stripXSS(value[i]);
                   if (result){
                	   String url = "http://" + request.getServerName() + ":" + request.getServerPort();   
                       response.sendRedirect(url);//跳转首页
                   }
               }
           }
       }
       chain.doFilter(req, resp);

   }

   public void init(FilterConfig filterConfig) throws ServletException {
   }

   /**
    * 如果找到非法字符则返回true,如果没找到则返回false
    * 
    * @param value
    * @return
    */
   public static boolean stripXSS(String value) {
       boolean result = false;
       if (value != null) {

           //过滤所有带 script
           Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>",
                   Pattern.CASE_INSENSITIVE);
           result = scriptPattern.matcher(value).find();
           //如果找到则为true
           if (result)
               return result;

//           //过滤所有 src带链接的单引号
//           scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'",
//                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
//                           | Pattern.DOTALL);
//           result = scriptPattern.matcher(value).find();
//           if (result)
//               return result;
//
//           //过滤所有 src带链接的双引号
//           scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"",
//                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
//                           | Pattern.DOTALL);
//           result = scriptPattern.matcher(value).find();
//           if (result)
//               return result;

           //过滤所有 script 结束标签的 可能出现注入js 不写起始标签
           scriptPattern = Pattern.compile("</script>",
                   Pattern.CASE_INSENSITIVE);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           //过滤所有 script
           scriptPattern = Pattern.compile("<script(.*?)>",
                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                           | Pattern.DOTALL);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           //过滤所有eval
           scriptPattern = Pattern.compile("eval\\((.*?)\\)",
                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                           | Pattern.DOTALL);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           //过滤所有 expression
           scriptPattern = Pattern.compile("expression\\((.*?)\\)",
                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                           | Pattern.DOTALL);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           
           scriptPattern = Pattern.compile("vbscript:",
                   Pattern.CASE_INSENSITIVE);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           //过滤所有 onload 初始加载方法
           scriptPattern = Pattern.compile("onload(.*?)=",
                   Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
                           | Pattern.DOTALL);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;

           //过滤所有 alert 弹框
           scriptPattern = Pattern.compile("alert", Pattern.CASE_INSENSITIVE);
           result = scriptPattern.matcher(value).find();
           if (result)
               return result;
       }
       return result;
   }

}