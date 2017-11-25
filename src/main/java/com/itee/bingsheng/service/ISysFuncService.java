package com.itee.bingsheng.service;

import com.itee.bingsheng.entity.SysFunction;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


public interface ISysFuncService {

    /**
     * ��ѯ���и��ڵ�
     *
     * @return
     */
    public List<SysFunction> querySysFunctionParentList();

    /**
     * ���湦����Ϣ
     *
     * @param sysFunction
     * @return
     */
    public int saveSysFunction(SysFunction sysFunction, HttpServletRequest request);

    /**
     * ��ѯ���и��ڵ�
     *
     * @param parentId
     * @param funcName
     * @param pageSize
     * @param page
     * @return
     */
    public List<SysFunction> querySysFunctionList(int parentId, String funcName, int pageSize, int page);

    /**
     * ��ѯ���е�ͳ������
     *
     * @param parentId
     * @param funcName
     * @return
     */
    public int querySysFunctionCount(int parentId, String funcName);

    /**
     * ���ط���
     *
     * @param parentId
     * @param funcName
     * @return
     */
    public List<SysFunction> querySysFunctionList(int parentId, String funcName);

    /**
     * ͣ�����ù���
     *
     * @param id
     * @param enabled
     * @return
     */
    public void updateSysFuncStatus(int id, boolean enabled, HttpServletRequest request);

    /**
     * ���ݽ�ɫ����ѯ��Ӧ��Ȩ��
     *
     * @param roleId
     * @return
     */
    public List<SysFunction> querySysFunctionByRoleId(Integer roleId, Integer parentId);

//    /**
//     * ���ݽ�ɫ����ѯ��Ӧ��Ȩ��
//     * @param roleId
//     * @return
//     */
//    public List<SysFunction> querySysFunctionByRoleId(Integer roleId);

    /**
     * ��ѯ���еĹ�����Ϣ
     *
     * @return
     */
    public List<SysFunction> querySysFunctionSonList(Integer parentId);




    public String querySysFunctionForLogin(int  roleId);

}
