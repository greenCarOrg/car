/**
 *
 */
package com.itee.bingsheng;


import com.itee.bingsheng.support.CustomResourceBundleMessageSource;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

/**
 * @author 龚磊
 */
public class SystemContextLoaderListener extends ContextLoaderListener {

    /**
     * spring上下文环境
     */
    private static ApplicationContext applicationContext;

    /**
     * servlet上下文环境
     */
    private static ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        servletContext = event.getServletContext();
        super.contextInitialized(event);
        applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);

        // File configFile = new
        // File(servletContext.getRealPath("/WEB-INF/config.properties"));

        CustomResourceBundleMessageSource messageSource = (CustomResourceBundleMessageSource) applicationContext.getBean("messageSource");

//        IConsumeService consumeService= (IConsumeService) applicationContext.getBean("consumeService");
//        consumeService.prepareConsumeCourses();
//        consumeService.consumeCourses();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        if (applicationContext != null) {

        }

        super.contextDestroyed(event);
    }


    /**
     * @return the applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * @return the servletContext
     */
    public static ServletContext getServletContext() {
        return servletContext;
    }

    public static <T> T getBean(String beanName) {
        return (T) applicationContext.getBean(beanName);
    }

}
