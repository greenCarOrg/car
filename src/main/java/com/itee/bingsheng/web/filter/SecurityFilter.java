package com.itee.bingsheng.web.filter;

import com.itee.bingsheng.entity.SysUser;
import com.itee.bingsheng.web.WebConstants;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String path = request.getServletPath();
        String redirectURL = request.getRequestURL().toString().replaceAll(path,"");
        HttpSession session = request.getSession();
        session.setAttribute("redirectURL",redirectURL);
        SysUser sysAdminUser= (SysUser) session.getAttribute(WebConstants.LOGIN_WEB_SESSION);
        System.out.println(path);
        if(path.startsWith("/weChat/")){
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            if (sysAdminUser != null ||
                    path.equals("/login.do") ||
                    path.equals("/syslogin.do") ||
                    path.equals("/index.do") ||
                    path.equals("/test.do") ||
                    path.startsWith("/rest") ||
                    path.startsWith("/out") ||
                    path.startsWith("/binding") ||
                    path.startsWith("/attendance") ||
                    path.contains("/pay/") ||
                    path.contains("/chart/")||
                    path.contains("/cs/") ||
                    path.contains("/onWebsocket")
                    ) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else if (sysAdminUser != null || path.equals("/index.do") || path.startsWith("/rest") || path.startsWith("/out")) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                response.sendRedirect(redirectURL);
            }
        }

    }

    @Override
    public void destroy() {

    }
}
