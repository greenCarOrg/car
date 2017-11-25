package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.entity.SysFunction;
import com.itee.bingsheng.entity.SysRoleFunc;

import java.util.List;
import java.util.Map;

/**
 * Created by abc on 2016/8/11.
 */
public interface SysFunctionMybatisDao{

    /**
     * 保存功能信息
     * @param sysFunction
     * @return
     */
    public int insertSysFunction(SysFunction sysFunction);

    /**
     * 查询所有的功能信息
     * @param params
     * @return
     */
    public List<SysFunction> querySysFunctionList(Map<String,Object> params);

    /**
     * 查询功能信息
     * @param id
     * @return
     */
    public SysFunction querySysFunction(int id);

    /**
     * 统计所有的功能信息数量
     * @param params
     * @return
     */
    public int querySysFunctionCount(Map<String,Object> params);

    /**
     * 插入角色功能信息
     * @param sysRoleFunc
     * @return
     */
    public int insertSysRoleFunc(SysRoleFunc sysRoleFunc);

    /**
     * 删除角色对应的权限
     * @return
     */
    public int deleteSysRoleFunc(Map<String,Object> params);

    /**
     * 查询角色对应的功能信息
     * @param params
     * @return
     */
    public List<SysFunction> querySysFunctionByRoleId(Map<String,Object> params);


    /**
     * 查询所有的功能信息
     * @return
     */
    public List<SysFunction> querySysFunctionParentList();

    /**
     * 更新功能状态
     * @param params
     * @return
     */
    public int updateSysFunction(Map<String,Object> params);


    /**
     * 查询
     * @param params
     * @return
     */
    public List<SysRoleFunc>  querySysRoleFuncByRoleId(Map<String,Object> params);

    /**
     * 查询所有的子节点
     * @return
     */
    public List<SysFunction> querySysFunctionSonList(Map<String,Object> params);


    String querySysFunctionForLogin(int roleId);

    public int updateSysFunctionName(Map<String,Object> params);
}
