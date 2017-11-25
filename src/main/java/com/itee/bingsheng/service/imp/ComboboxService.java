package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.mybatis.dao.ComboboxDao;
import com.itee.bingsheng.service.IComboboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Transactional
@Service
public class ComboboxService implements IComboboxService {

	@Resource
	private ComboboxDao dao;


	/**
	 * 获取登录日志列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getLoginLogList(Map<String,Object> map) throws Exception{
		return dao.getLoginLogList(map);
	}

	/**
	 * 获取登录日志数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getLoginLogCount(Map<String,Object> map) throws Exception{
		return dao.getLoginLogCount(map);
	}


	/**
	 * 获取短信列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public  List<Map<String,Object>> getSmsLogList(Map<String,Object> map) throws Exception{
		return dao.getSmsLogList(map);
	}


	/**
	 * 获取短信日志数量
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public  int getSmsLogCount(Map<String,Object> map) throws Exception{
		return dao.getSmsLogCount(map);
	}



	/**
	 * 获取所有combobox
	 * @param params
	 * @return
	 */
	public List<Map<String,Object>> getComboxData(Map<String, Object> params){
		return dao.getComboxData(params);
	}

	@Override
	public List<Map<String, Object>> boxRecommend(Map<String, Object> params)throws Exception{
		return dao.boxRecommend(params);
	}

	/**
	 * 根据名称获取常亮信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@Override
	public double getCodeValueByCode(String code)throws Exception{
		return dao.getCodeValueByCode(code);
	}

}
