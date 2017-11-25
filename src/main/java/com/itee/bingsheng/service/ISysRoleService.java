package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.SysRole;
import com.itee.bingsheng.entity.SysRoleFunc;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by abc on 2016/8/31.
 */
public interface ISysRoleService {

    /**
     * 获取所有角色列表
     *
     * @return
     */
    List<SysRole> queryAllSysRole();

    /**
     * 新增角色信息
     *
     * @param sysRole
     * @return
     */
    int saveSysRole(SysRole sysRole, HttpServletRequest request);

    /**
     * 获取所有角色列表(带分页)
     *
     * @return
     */
    List<SysRole> queryPageAllSysRole(int pageNo, int pageSize);

    /**
     * @return
     */
    int queryPageAllSysRoleCount();

    /**
     * 更新角色额状态
     *
     * @param id
     * @param enabled
     * @param request
     */
    void updateSysRoleStatus(int id, boolean enabled, HttpServletRequest request);

    /**
     * 根据角色ID，查询角色名
     *
     * @param id
     * @return
     */
    SysRole querySysRoleById(int id);

    /**
     * 查询角色对应的权限
     *
     * @param roleId
     * @return
     */
    List<SysRoleFunc> querySysRoleFuncByRoleId(int roleId);

    /**
     * 更新角色权限信息
     *
     * @return
     */
    void updateSysRoleFunc(HttpServletRequest request);
}
