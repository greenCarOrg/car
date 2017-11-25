package com.itee.bingsheng.persistence;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.web.WebConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 1、获得session id
 * 2、从session中获取当前用户
 * 3、存放新的用户信息到session
 * @author Jesse
 */
public class Sessions {
	
	public Sessions() {
	}
	
	/**
	 * 获得sessionid
	 * @return session id
	 */
	public static String getSessionId(HttpServletRequest request){
		return request.getSession().getId();
	}
	/**
	 * 从session中取到当前用户
	 */
	public static SysUser getSysUser(HttpServletRequest request){
		SysUser sysUser = null;
		HttpSession session =  request.getSession();
		if(session!=null){
			sysUser = (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
		}
		return sysUser;
	}
	/**
	 * 设置新的用户信息到session
	 * @param sysUser 新的用户
	 */
	public static SysUser setNewSysUser(HttpServletRequest request,SysUser sysUser){
		HttpSession session =  request.getSession();
		if(session!=null){
			session.setAttribute(WebConstants.LOGIN_WEB_SESSION, sysUser);
		}
		return sysUser;
	}
}
