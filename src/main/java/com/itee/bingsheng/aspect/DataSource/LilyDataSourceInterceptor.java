package com.itee.bingsheng.aspect.DataSource;

import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/9/28.
 */
@Component
@Aspect
public class LilyDataSourceInterceptor {

//    private final Logger log = Logger.getLogger(this.getClass());
//
//    @Before("execution(* com.itee.bingsheng.service.*.*(..)) ")
//    public void dynamicSetDataSoruce(JoinPoint joinPoint) throws Exception{
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        //获取请求ip
//        String serverName =  request.getServerName();
//        log.info("serverName : "+serverName);
//
//        String ip = request.getRemoteAddr();
//        log.info("request ip :" +ip);
//
//        Class<?> clazz = joinPoint.getTarget().getClass();
//        String className = clazz.getName();
//        if (ClassUtils.isAssignable(clazz, Proxy.class)) {
//            className = joinPoint.getSignature().getDeclaringTypeName();
//        }
//        String methodName = joinPoint.getSignature().getName();
//        Object[] arguments = joinPoint.getArgs();
//        StringBuffer print = new StringBuffer();
//        print.append("excuted:");
//        print.append(className);
//        print.append(".");
//        print.append(methodName);
//        print.append("(");
//        for(int i = 0;i<arguments.length;i++){
//            print.append(arguments[i]);
//            if(i!=arguments.length-1){
//                print.append(",");
//            }
//        }
//        print.append(")");
//        log.info(print.toString());
//
//        //String contentType = (request.getSession().getAttribute("contentType")).toString();
//        String contentType= "deve";
//        log.info("contentType :"+contentType);
//        if (StringUtils.equals("deve",contentType)) {
//            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_DEVE);
//        } else if (StringUtils.equals("test", contentType)) {
//            CustomerContextHolder.setCustomerType(CustomerContextHolder.DATA_SOURCE_TEST);
//        }
//        log.info(CustomerContextHolder.getCustomerType());
//
//    }

}
