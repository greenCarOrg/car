package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.User;
import com.itee.bingsheng.mybatis.dao.UserMyBatisDao;
import com.itee.bingsheng.utils.MD5Util;
import com.itee.bingsheng.service.IUserService;
import com.itee.bingsheng.utils.StringTools;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class UserService implements IUserService {


    @Resource
    UserMyBatisDao dao;

    /**
     * 获取用户的优惠券
     * userId
     * state 0 未使用 1 已使用 2 已过期
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String,Object>> getUserCoupon(Map<String,Object> map)throws Exception{
        return dao.getUserCoupon(map);
    }


    @Resource
    public List<Map<String, Object>> queryAllUser(Map<String,Object> map)throws Exception{
        return dao.queryAllUser(map);
    }


    @Resource
    public int getAllUserCount(Map<String,Object> map)throws Exception{
        return dao.getAllUserCount(map);
    }


    @Override
    public  User updateUser(User user)throws Exception{
        return user;
    }

    /**
     * 用户获取积分,通用
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int giftUserPoint(Map<String,Object> map)throws Exception{
        return dao.giftUserPoint(map);
    }

    /**
     * 用户注册
     * @param map
     * @return
     * @throws Exception
     * #{password},
     * #{paypwd},
     * #{sex},
     * #{birthday},
     * #{lastLogin},
     * #{lastIp},
     * #{qq},
     * #{phone},
     * #{oauth},
     * #{openid},
     * #{unionid},
     * #{head},
     * #{nickname},
     * #{level},
     * #{discount},
     * #{firstLeader},
     * #{secondLeader},
     * #{token},
     * #{refer},
     * #{focus},
     * #{headId}
     */
    @Override
    public User registerUser(Map<String,Object> map, HttpServletRequest request)throws Exception{
        String phone=map.get("phone").toString();
        User user=new User();
        user.setPhone(phone);
        String pwd=map.get("password").toString();
        user.setRegTime(new Date());
        user.setPassword(MD5Util.ecodeByMD5AndSalt(pwd));
        dao.registerUser(user);
        //保存登录日志
        Map mp=new HashMap<>();
        mp.put("userId",user.getUserId());
        mp.put("lastIp", StringTools.getLocalIpAddr(request));
        dao.saveLoginLog(mp);

        //返回加密后的用户信息
        return user;
    }

    /**
     * 用户登录验证
     * @param phone
     * @param password
     * @return
     * @throws Exception
     */
    @Override
    public User userLogin(String phone,String password)throws Exception{
        Map<String ,Object>map=new HashMap<>();
        map.put("userName",phone);
        map.put("password",password);
        return dao.userLogin(map);
    }



    /**
     * 修改用户的支付密码或是登录密码
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int updatePassword(Map<String,Object> map)throws Exception{
        return dao.updateUser(map);
    }

    /**
     * 通过用户编号找到对应的用户信息
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public User getUserById(int userId)throws Exception{
        return dao.getUserById(userId);
    }

    /**
     * 更新用户信息
     * @param map
     * @return
     * @throws Exception
     */
    @Override
    public int updateUser(Map<String,Object> map)throws Exception{
        return dao.updateUser(map);
    }


    @Override
    public User getUserByPhone(String phone)throws Exception{
        return dao.getUserByPhone(phone);
    }
}
