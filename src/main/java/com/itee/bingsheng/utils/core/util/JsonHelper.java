package com.itee.bingsheng.utils.core.util;

import com.itee.bingsheng.utils.core.metatype.Dto;
import com.itee.bingsheng.utils.core.metatype.impl.BaseDto;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;

/**
 * Json处理器<br>
 */
public class JsonHelper {

	private static Log log = LogFactory.getLog(JsonHelper.class);

	/**
	 * 将不含日期时间格式的Java对象系列化为Json资料格式
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject) {
		String jsonString = "[]";
		if (BL3Utils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将含有日期时间格式的Java对象系列化为Json资料格式<br>
	 * Json-Lib在处理日期时间格式是需要实现其JsonValueProcessor接口,所以在这里提供一个重载的方法对含有<br>
	 * 日期时间格式的Java对象进行序列化
	 * 
	 * @param pObject
	 *            传入的Java对象
	 * @return
	 */
	public static final String encodeObject2Json(Object pObject,
												 String pFormatString) {
		String jsonString = "[]";
		if (BL3Utils.isEmpty(pObject)) {
			// log.warn("传入的Java对象为空,不能将其序列化为Json资料格式.请检查!");
		} else {
			JsonConfig cfg = new JsonConfig();
			cfg.registerJsonValueProcessor(java.sql.Timestamp.class,
					new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(Date.class,
					new JsonValueProcessorImpl(pFormatString));
			cfg.registerJsonValueProcessor(java.sql.Date.class,
					new JsonValueProcessorImpl(pFormatString));
			if (pObject instanceof ArrayList) {
				JSONArray jsonArray = JSONArray.fromObject(pObject, cfg);
				jsonString = jsonArray.toString();
			} else {
				JSONObject jsonObject = JSONObject.fromObject(pObject, cfg);
				jsonString = jsonObject.toString();
			}
		}
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将分页信息压入JSON字符串 此类内部使用,不对外暴露
	 * 
	 * @param totalCount
	 * @return 返回合并后的字符串
	 */
	private static String encodeJson2PageJson(String jsonString, Integer totalCount) {
		jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:" + jsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("合并后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 直接将List转为分页所需要的Json资料格式
	 * 
	 * @param list
	 *            需要编码的List对象
	 * @param totalCount
	 *            记录总数
	 *            时间日期格式化,传null则表明List不包含日期时间属性
	 */
	public static final String encodeList2PageJson(List list, Integer totalCount, String dataFormat) {
		String subJsonString = "";
		if (BL3Utils.isEmpty(dataFormat)) {
			subJsonString = encodeObject2Json(list);
		} else {
			subJsonString = encodeObject2Json(list, dataFormat);
		}
		String jsonString = "{TOTALCOUNT:" + totalCount + ", ROOT:"
				+ subJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将数据系列化为表单数据填充所需的Json格式
	 * 
	 *            待系列化的对象
	 * @param pFormatString
	 *            日期时间格式化,如果为null则认为没有日期时间型字段
	 * @return
	 */
	public static String encodeDto2FormLoadJson(Dto pDto, String pFormatString) {
		String jsonString = "";
		String sunJsonString = "";
		if (BL3Utils.isEmpty(pFormatString)) {
			sunJsonString = encodeObject2Json(pDto);
		} else {
			sunJsonString = encodeObject2Json(pDto, pFormatString);
		}
		jsonString = "{success:"
				+ (BL3Utils.isEmpty(pDto.getAsString("success")) ? "true"
						: pDto.getAsString("success")) + ",data:"
				+ sunJsonString + "}";
		if (log.isInfoEnabled()) {
			log.info("序列化后的JSON资料输出:\n" + jsonString);
		}
		return jsonString;
	}

	/**
	 * 将单一Json对象解析为DTOJava对象
	 * 
	 * @param jsonString
	 *            简单的Json对象
	 * @return dto
	 */
	public static Dto parseSingleJson2Dto(String jsonString) {
		Dto dto = new BaseDto();
		if (BL3Utils.isEmpty(jsonString)) {
			return dto;
		}
		JSONObject jb = JSONObject.fromObject(jsonString);
		dto = (BaseDto) JSONObject.toBean(jb, BaseDto.class);
		return dto;
	}

	/**
	 * 将复杂Json资料格式转换为List对象
	 * 
	 * @param jsonString
	 *            复杂Json对象,格式必须符合如下契约<br>
	 *            {"1":{"name":"托尼.贾","age":"27"},
	 *            "2":{"name":"甄子丹","age":"72"}}
	 * @return List
	 */
	public static List parseJson2List(String jsonString) {
		List list = new ArrayList();
		JSONObject jbJsonObject = JSONObject.fromObject(jsonString);
		Iterator iterator = jbJsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			Dto dto = parseSingleJson2Dto(jbJsonObject.getString(iterator
					.next().toString()));
			list.add(dto);
		}
		return list;
	}

	/**
	 * 将上传的records转换为List对象
	 * 
	 * @param jsonString
	 * @return List
	 */
	public static List<Dto> parseRecordsJson2List(String jsonString) {
		return parseRecordsJson2List(jsonString, null);
	}

	/**
	 * 将上传的records转换为List对象，如果里面有嵌套records，则指定嵌套record是的名字
	 * 
	 * @param jsonString
	 * @return List
	 */
	public static List<Dto> parseRecordsJson2List(String jsonString,
												  String detailName) {
		JSONArray array = JSONArray.fromObject(jsonString);
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(BaseDto.class);
		if (StringUtils.isNotEmpty(detailName)) {
			Map<String, Class<BaseDto>> classMap = new HashMap<String, Class<BaseDto>>();
			classMap.put(detailName, BaseDto.class);
			jsonConfig.setClassMap(classMap);
		}
		List<Dto> details = (List<Dto>) JSONArray.toCollection(array,
				jsonConfig);

		return details;
	}
	   /**
     * 将JsonArrayString 转换成List
     * @author Drone
     * @param jsonString
     *            复杂Json对象,格式必须符合如下契约<br>
     *            [{"name":"托尼.贾","age":"27"},
     *            {"name":"甄子丹","age":"72"}]
     * @return List
     */
    public static List parseJsonArray2List(String jsonString) {
        List list = new ArrayList();
        JSONArray jsonArray = JSONArray.fromObject(jsonString);
        Iterator iterator = jsonArray.iterator();
        while (iterator.hasNext()) {
            Dto dto = parseSingleJson2Dto(iterator.next().toString());
            list.add(dto);
        }
        return list;
    }
    
    /**
     * 得到json串里面里面的key值
     * @param json
     * @param key
     * @return
     * @author 阳庆文
     * 2015年12月9日 上午9:36:31
     */
    public static String getJsonKey(String json, String key) {
		String result = "";

		JSONObject jsonObj = JSONObject.fromObject(json);
		if (jsonObj.containsKey(key)) {
			result = jsonObj.get(key).toString();
		} 
		return result;
	}
	/**
	 * 将json格式的字符串解析成Map对象 <li>
	 * json格式：{"name":"admin","retries":"3fff","testname"
	 * :"ddd","testretries":"fffffffff"}
	 */
	public static HashMap<String, Object> toHashMap(Object object)
	{
		HashMap<String, Object> data = new HashMap<String, Object>();
		// 将json字符串转换成jsonObject
		JSONObject jsonObject = JSONObject.fromObject(object);
		Iterator it = jsonObject.keys();
		// 遍历jsonObject数据，添加到Map对象
		while (it.hasNext())
		{
			String key = String.valueOf(it.next());
			Object value = jsonObject.get(key);
			data.put(key, value);
		}
		return data;
	}
}
