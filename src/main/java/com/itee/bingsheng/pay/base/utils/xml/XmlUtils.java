package com.itee.bingsheng.pay.base.utils.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlUtils {

	public static byte[] toXmlBytes(Object object) throws Exception {
		JAXBContext jaxbContext = null;
		try {
			jaxbContext = JAXBContext.newInstance(object.getClass());
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(object, os);
			return os.toByteArray();
		} catch (JAXBException e) {
			throw  new Exception(e);
		}
	}

	public static String toXmlString(Object object) throws Exception {
		byte[] bytes = toXmlBytes(object);
		if(bytes.length > 0) {
			try {
				String content = new String(bytes, "UTF-8");
				return content;
			} catch (UnsupportedEncodingException e) {
				throw  new Exception(e);
			}
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	public static <T> T toObject(String content, Class<T> cls) throws Exception {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(cls);
			StringReader reader = new StringReader(content.trim());
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			T obj = (T)unmarshaller.unmarshal(reader);
			return obj;
		} catch (JAXBException e) {
			throw  new Exception(e);
		}
	}

	/**
	 * 将请求结果转换为bean
	 * @param request
	 * @param cls
	 * @param <T>
	 * @return
	 */
	public static <T> T toObject(HttpServletRequest request, Class<T> cls) throws Exception {
		JAXBContext jc;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			jc = JAXBContext.newInstance(cls);
			Unmarshaller unmarshaller = jc.createUnmarshaller();
			T obj = (T)unmarshaller.unmarshal(reader);
			return obj;
		} catch (Exception e) {
			throw  new Exception(e);
		}
	}


	/**
	 * 将请求结果转换为bean
	 * @param request
	 * @return
	 */
	public static String toXmlStr(HttpServletRequest request) throws Exception {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream(),"UTF-8"));
			String line = null;
			while((line = br.readLine())!=null){
				sb.append(line).append("\r\n");
			}
		}catch (Exception e){
			throw  new Exception(e);
		}
		return  sb.toString();
	}

	/**
	 * 将map转为xml String
	 * @param map
	 * @return
	 */
	public static String maptoXml(Map map) throws Exception {
		try {
			Document document = DocumentHelper.createDocument();
			Element nodeElement = document.addElement("xml");
			for (Object obj : map.keySet()) {
				Element keyElement = nodeElement.addElement(String.valueOf(obj));
				keyElement.setText(String.valueOf(map.get(obj)));
			}
			return doc2String(document);
		} catch (Exception ex) {
			throw  new Exception(ex);
		}
	}

	/**
	 *
	 * @param document
	 * @return
	 */
	public static String doc2String(Document document) throws Exception {
		try {
			// 使用输出流来进行转化
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			// 使用UTF-8编码
			OutputFormat format = new OutputFormat("   ", true, "UTF-8");
			XMLWriter writer = new XMLWriter(out, format);
			writer.write(document);
			return out.toString("UTF-8");
		} catch (Exception ex) {
			throw  new Exception(ex);
		}
	}

	/**
	 * 用于解析微信通知返回的PostData xml格式
	 * @return Map集合
	 * @throws IOException
	 * @throws DocumentException
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> parseWxXml(InputStream inputStream) throws Exception {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			SAXReader sax = new SAXReader();
			Document doc = sax.read(inputStream);
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			for(Element e: elements){
				map.put(e.getName(), e.getTextTrim());
			}
			inputStream.close();
			return map;
		} catch (Exception ex) {
			throw  new Exception(ex);
		}
	}
}
