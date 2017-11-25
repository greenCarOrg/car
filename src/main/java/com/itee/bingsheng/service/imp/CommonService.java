package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.mybatis.dao.CommonDao;
import com.itee.bingsheng.mybatis.dao.UserMyBatisDao;
import com.itee.bingsheng.service.ICommonService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommonService implements ICommonService{

	@Resource
	CommonDao dao;
	@Resource
	UserMyBatisDao userMyBatisDao;
	/**
	 * 保存短信验证
	 * @param phoneNumber
	 * @param randCode
	 * @param returnmsg
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@Override
	public int saveSmsLog (String phoneNumber,String randCode,String returnmsg,int type)throws Exception{
		Map<String,Object> map=new HashMap<>();
		map.put("phone_number",phoneNumber);
		map.put("create_time",new Date());
		map.put("verify_code",randCode);
		map.put("return_code",returnmsg);
		if(type==1){
			map.put("code","SMS_97860001");
		}else {
			map.put("code","SMS_97575016");
		}
		return dao.saveSmsLog(map);
	}

	/**
	 * 获取省市级联
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getRegion(Map<String,Object> map)throws Exception{
		return dao.getRegion(map);
	}


	/**
	 * 获取用户地址
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getUserAddress(int userId)throws Exception{
		return dao.getUserAddress(userId);
	}

	/**
	 * 设置用户常用收获地址
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateUserDefaultAddress(Map<String,Object> map)throws Exception{
		dao.updateUserDefaultAddressCancel(map);
		return dao.updateUserDefaultAddress(map);
	}

	/**
	 * 新增用户收货地址
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int addUserAddress(Map<String,Object> map)throws Exception{
		return dao.addUserAddress(map);
	}


	@Override
	public List<Map<String, Object>> getShipping(Map<String, Object> map) throws Exception {
		return dao.getShipping(map);
	}
	/**
	 * 保存接口调用信息
	 * @param params
	 * @return
	 */
	@Override
	public int saveIntefaceLog(Map<String, Object> params)throws Exception{
		return dao.saveIntefaceLog(params);
	}


	@Override
	public List<Map<String,Object>> getInterfaceLogList(Map<String, Object> params) throws Exception{
		return dao.getInterfaceLogList(params);

	}

	@Override
	public int getInterfaceLogCount(Map<String, Object> params)throws Exception{
		return dao.getInterfaceLogCount(params);
	}
}
