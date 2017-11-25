package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.SysAdminUserMybatisDao;
import com.itee.bingsheng.utils.MD5Util;
import com.itee.bingsheng.service.ISysUserService;
import com.itee.bingsheng.web.WebConstants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysUserService implements ISysUserService {


    @Resource
    private SysAdminUserMybatisDao sysUserDao;


    @Override
    public SysUser querySysUser(String userName) {
		SysUser SysUser = null;
        try{
            SysUser = sysUserDao.querySysAdminUser(userName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return SysUser;
    }



    @Override
    public void updateSysUserLoginInfo(String ip,int id) {
        try{
            Map<String,Object> params = new HashMap<String,Object>();
            params.put("lastLoginIp",ip);
            params.put("lastLoginTime",new Date());
            params.put("updateTime", new Date());
            params.put("id", id);
            sysUserDao.updateSysUserLoginInfo(params);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取所有用户的列表
     * @return
     */
    @Override
    public List<SysUser> queryAllSysAdminUser(int pageNo,int pageSize){
        List<SysUser> list = null;
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("pageSize", pageSize);
            params.put("page", (pageNo-1)*pageSize);
            list = sysUserDao.querySysUserList(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 统计所有用户数量
     * @return
     */
    @Override
    public int queryAllSysAdminUserCount(){
        int count = 0;
        try{
            count = sysUserDao.querySysUserCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public int insertSysUser(SysUser SysUser, HttpServletRequest request){
        SysUser.setCreateTime(new Date());
        SysUser.setUpdateTime(new Date());
        SysUser.setLastLoginTime(new Date());
        SysUser.setPassword(MD5Util.ecodeByMD5AndSalt(SysUser.getPassword()));
        SysUser.setLastLoginIp(request.getRemoteAddr());
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser)session.getAttribute(WebConstants.LOGIN_WEB_SESSION);

        SysUser.setLastOperator(sysUser.getUserName());
        return sysUserDao.insertSysUser(SysUser);
    }

    @Override
    public int editSysUser(SysUser SysUser, HttpServletRequest request) {
        SysUser.setUpdateTime(new Date());
        SysUser.setLastLoginTime(new Date());
        SysUser.setLastLoginIp(request.getRemoteAddr());
        HttpSession session = request.getSession();
        SysUser sysUser = (SysUser)session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        SysUser.setLastOperator(sysUser.getUserName());
        return sysUserDao.editSysUser(SysUser);
    }

    @Override
    public void updateSysUserStatus(int id,boolean enabled,HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", enabled ? 1 : 0);
        params.put("updateTime",new Date());

        SysUser SysUser = (SysUser)request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
        params.put("lastOperator",SysUser.getUserName());

        sysUserDao.updateSysUserStatus(params);
    }
}
