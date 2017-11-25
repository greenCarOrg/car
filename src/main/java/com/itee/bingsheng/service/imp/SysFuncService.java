package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.entity.SysFunction;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.SysFunctionMybatisDao;
import com.itee.bingsheng.service.ISysFuncService;
import com.itee.bingsheng.web.WebConstants;
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
public class SysFuncService implements ISysFuncService {

    @Resource
    private SysFunctionMybatisDao sysFunctionMybatisDao;

    @Override
    public List<SysFunction> querySysFunctionParentList() {
        return sysFunctionMybatisDao.querySysFunctionParentList();

    }

    @Override
    public int saveSysFunction(SysFunction sysFunction, HttpServletRequest request) {
        int id = 0;
        try {
            SysUser sysAdminUser = (SysUser) request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
            sysFunctionMybatisDao.insertSysFunction(sysFunction);
            id = sysFunction.getId();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<SysFunction> querySysFunctionList(int parentId, String funcName, int pageSize, int page) {
        List<SysFunction> list = null;
        try {
            Map<String, Object> params = new HashMap();
            params.put("parentId", parentId);
            params.put("funcName", funcName);
            params.put("pageSize", pageSize);
            params.put("page", (page - 1) * pageSize);
            list = sysFunctionMybatisDao.querySysFunctionList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<SysFunction> querySysFunctionList(int parentId, String funcName) {
        return querySysFunctionList(parentId, funcName, 100, 1);
    }

    @Override
    public int querySysFunctionCount(int parentId, String funcName) {
        int count = 0;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parentId", parentId);
            params.put("funcName", funcName);
            count = sysFunctionMybatisDao.querySysFunctionCount(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void updateSysFuncStatus(int id, boolean enabled, HttpServletRequest request) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", id);
        params.put("status", enabled ? 1 : 0);
        params.put("updateTime", new Date());

        SysUser sysAdminUser = (SysUser) request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
        params.put("lastOperator", sysAdminUser.getUserName());

        sysFunctionMybatisDao.updateSysFunction(params);
    }

//    @Override
//    public List<SysFunction> querySysFunctionByRoleId(Integer roleId){
//        return querySysFunctionByRoleId(roleId,null);
//    }

    @Override
    public List<SysFunction> querySysFunctionByRoleId(Integer roleId, Integer parentId) {
        List<SysFunction> sysFunctionList = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("roleId", roleId);
            params.put("parentId", parentId);
            sysFunctionList = sysFunctionMybatisDao.querySysFunctionByRoleId(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysFunctionList;
    }

    @Override
    public List<SysFunction> querySysFunctionSonList(Integer parentId) {
        List<SysFunction> sysFunctionSonList = null;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("parentId", parentId);
            sysFunctionSonList = sysFunctionMybatisDao.querySysFunctionSonList(params);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysFunctionSonList;
    }

    @Override
    public String querySysFunctionForLogin(int  roleId){
        String sysFunctionPathList = null;
        try {
            sysFunctionPathList = sysFunctionMybatisDao.querySysFunctionForLogin(roleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sysFunctionPathList;
    }

}
