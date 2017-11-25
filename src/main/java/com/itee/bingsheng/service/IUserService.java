package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


public interface IUserService {

    /**
     * 获取用户的优惠券
     * userId
     * state 0 未使用 1 已使用 2 已过期
     * @return
     * @throws Exception
     */
    List<Map<String,Object>> getUserCoupon(Map<String,Object> map)throws Exception;

    List<Map<String, Object>> queryAllUser(Map<String,Object> map)throws Exception;

    int getAllUserCount(Map<String,Object> map)throws Exception;


    User updateUser(User user)throws Exception;

    /**
     * 用户获取积分,通用
     * @param map
     * @return
     * @throws Exception
     */
    int giftUserPoint(Map<String,Object> map)throws Exception;

    /**
     * 用户注册
     * @param map
     * @return
     * @throws Exception
     */
    User registerUser(Map<String,Object> map, HttpServletRequest request)throws Exception;

    /**
     * 用户登录验证
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    User userLogin(String phone,String password)throws Exception;

    /**
     * 修改用户的支付密码或是登录密码
     * @param map
     * @return
     * @throws Exception
     */
    int updatePassword(Map<String,Object> map)throws Exception;

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
     * 根据手机号码查找用户
     * @param phone
     * @return
     * @throws Exception
     */
    User getUserByPhone(String phone)throws Exception;
}
