package com.itee.bingsheng.mybatis.dao;


import com.itee.bingsheng.entity.SysRole;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/8/11.
 */
public interface SysRoleMybatisDao {

    /**
     * 保存角色信息
     * @param sysRole
     * @return
     */
    public int insertSysRole(SysRole sysRole);

    /**
     * 查询所有的角色信息
     * @return
     */
    public List<SysRole> querySysRoleList();

    /**
     * 查询角色信息
     * @param id
     * @return
     */
    public SysRole querySysRole(int id);

    /**
     * 统计所有的角色信息
     * @return
     */
    public int querySysRoleCount();

    /**
     * 查询所有的角色信息
     * @return
     */
    public List<SysRole> queryPageAllSysRole(Map<String,Object> params);

    /**
     * 更新功能状态
     * @param params
     * @return
     */
    public int updateSysRole(Map<String,Object> params);



}
