package com.itee.bingsheng.mybatis.dao;


import com.itee.bingsheng.entity.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysAdminUserMybatisDao {

    /**
     * 用户登录
     * @param userName
     * @return
     */
    public SysUser querySysAdminUser(@Param("userName") String userName);

    /**
     * 查询所有的角色信息
     * @return
     */
    public List<SysUser> querySysUserList(Map<String,Object> params);


    /**
     * 统计所有的角色信息
     * @return
     */
    public int querySysUserCount();

    /**
     * 更新角色信息状态
     * @return
     */
    public int updateSysUserStatus(Map<String,Object> params);

    /**
     * 插入角色信息
     * @return
     */
    public int insertSysUser(SysUser SysUser);

    /**
     * 修改用户信息
     * @param SysUser
     * @return
     */
    public int editSysUser(SysUser SysUser);

    /**
     * 更新系统用户的登录信息
     * @param params
     */
    public void updateSysUserLoginInfo(Map<String, Object> params);

}
