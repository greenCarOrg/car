package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.entity.SysRole;
import com.itee.bingsheng.entity.SysRoleFunc;
import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.mybatis.dao.SysFunctionMybatisDao;
import com.itee.bingsheng.mybatis.dao.SysRoleMybatisDao;
import com.itee.bingsheng.service.ISysRoleService;
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

/**
 * Created by abc on 2016/1/8.
 */
@Service
@Transactional
public class SysRoleService implements ISysRoleService {

    @Resource
    private SysRoleMybatisDao sysRoleDao;

    @Resource
    private SysFunctionMybatisDao sysFunctionMybatisDao;

    @Override
    public List<SysRole> queryAllSysRole(){
        List<SysRole> sysRoles = null;
        try{
            sysRoles = sysRoleDao.querySysRoleList();
       }catch (Exception e){
           e.printStackTrace();
       }
        return sysRoles;
    }

    @Override
    public int saveSysRole(SysRole sysRole,HttpServletRequest request){
        int id = 0;
        try{
            HttpSession session = request.getSession();
            SysUser SysUser = (SysUser)session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
            sysRole.setCreateTime(new Date());
            sysRole.setStatus(1);
            sysRole.setUpdateTime(new Date());
            sysRole.setLastOperator(SysUser.getUserName());
            id = sysRoleDao.insertSysRole(sysRole);

            if(id > 0){
                int roleId = sysRole.getId();
                String[] funcId = request.getParameterValues("funcId");
                if(funcId != null && funcId.length > 0){
                    for(int a = 0; a < funcId.length; a ++) {
                        SysRoleFunc sysRoleFunc = new SysRoleFunc();
                        sysRoleFunc.setRoleId(roleId);
                        sysRoleFunc.setFuncId(Integer.valueOf(funcId[a]));
                        sysRoleFunc.setCreateTime(new Date());
                        sysFunctionMybatisDao.insertSysRoleFunc(sysRoleFunc);
                    }
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return id;
    }

    /**
     * 获取所有角色列表(带分页)
     * @return
     */
    public List<SysRole> queryPageAllSysRole(int pageNo,int pageSize){
        List<SysRole> list = null;
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("pageSize", pageSize);
            params.put("page", (pageNo - 1) * pageSize);
            list = sysRoleDao.queryPageAllSysRole(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    /**
     *获取所有角色的数量
     * @return
     */
    public int queryPageAllSysRoleCount(){
        int count = 0;
        try{
            count = sysRoleDao.querySysRoleCount();
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public void updateSysRoleStatus(int id,boolean enabled,HttpServletRequest request){
        Map<String,Object> params = new HashMap<>();
        params.put("id", id);
        params.put("status", enabled ? 1 : 0);
        params.put("updateTime",new Date());

        SysUser SysUser = (SysUser)request.getSession().getAttribute(WebConstants.LOGIN_WEB_SESSION);
        params.put("lastOperator",SysUser.getUserName());

        sysRoleDao.updateSysRole(params);
    }

    @Override
    public SysRole querySysRoleById(int id){
        return sysRoleDao.querySysRole(id);
    }

    @Override
    public List<SysRoleFunc> querySysRoleFuncByRoleId(int roleId){
        List<SysRoleFunc> sysRoleFunc = null;
        try{
            Map<String,Object> params = new HashMap<>();
            params.put("roleId",roleId);
            sysRoleFunc = sysFunctionMybatisDao.querySysRoleFuncByRoleId(params);
        }catch (Exception e){
            e.printStackTrace();
        }
        return sysRoleFunc;
    }

    @Override
    public void updateSysRoleFunc(HttpServletRequest request){
        try{
            String id = request.getParameter("roleId");
            String[] funcId = request.getParameterValues("funcId");

            if( null != id && !id.equals("")) {
                int roleId = Integer.valueOf(id);

                Map<String,Object> params = new HashMap<>();
                params.put("roleId",roleId);
                sysFunctionMybatisDao.deleteSysRoleFunc(params);

                if (funcId != null && funcId.length > 0) {
                    for (int a = 0; a < funcId.length; a++) {
                        SysRoleFunc sysRoleFunc = new SysRoleFunc();
                        sysRoleFunc.setRoleId(roleId);
                        sysRoleFunc.setFuncId(Integer.valueOf(funcId[a]));
                        sysRoleFunc.setCreateTime(new Date());
                        sysFunctionMybatisDao.insertSysRoleFunc(sysRoleFunc);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
