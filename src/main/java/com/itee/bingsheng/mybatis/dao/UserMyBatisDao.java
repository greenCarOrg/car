package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.entity.User;

import java.util.List;
import java.util.Map;


public interface UserMyBatisDao {

	/**
	 * 获取用户的优惠券
	 * userId
	 * state 0 未使用 1 已使用 2 已过期
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getUserCoupon(Map<String,Object> map)throws Exception;

	/**
	 * 用户获取积分,通用
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int giftUserPoint(Map<String,Object> map)throws Exception;

	/**
	 * 获取用户列表
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryAllUser(Map<String,Object> map)throws Exception;

	/**
	 * 获取用户总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int getAllUserCount(Map<String,Object> map)throws Exception;

	/**
	 * 用户注册
	 * @return
	 * @throws Exception
	 */
	int registerUser(User user)throws Exception;


	/**
	 * 保存登录日志
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int saveLoginLog(Map<String,Object> map)throws Exception;

	/**
	 * 用户登录验证
	 * @return
	 * @throws Exception
	 */
	User userLogin(Map<String,Object> map)throws Exception;


	/**
	 * 通过用户编号找到对应的用户信息
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	User getUserById(int userId)throws Exception;

	/**
	 * 更新用户信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateUser(Map<String,Object> map)throws Exception;


	/**
	 * 通过推荐人手机号码获取推荐人信息
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	User getUserByPhone(String phone)throws Exception;

}
