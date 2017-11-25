package com.itee.bingsheng.service;



import com.itee.bingsheng.entity.SysUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/8/31.
 */
public interface ISysUserService {


    /**
     * 用户名登录
     * @param userName
     * @return
     */
    public SysUser querySysUser(String userName);


    /**
     * 更新登录信息
     * @param ip
     */
    public void updateSysUserLoginInfo(String ip,int id);


    /**
     * 获取所有用户的列表
     * @return
     */
    public List<SysUser> queryAllSysAdminUser(int pageNo,int pageSize);

    /**
     * 统计所有用户数量
     * @return
     */
    public int queryAllSysAdminUserCount();

    /**
     * 新增系统用户
     * @param sysAdminUser
     * @return
     */
    public int insertSysUser(SysUser sysAdminUser, HttpServletRequest request);

    public int editSysUser(SysUser sysAdminUser, HttpServletRequest request);

    /**
     * 更新用户的状态
     * @param id
     * @param enabled
     * @param request
     */
    public void updateSysUserStatus(int id,boolean enabled,HttpServletRequest request);

}
