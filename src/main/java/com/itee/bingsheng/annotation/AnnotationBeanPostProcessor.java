/**
 *
 */
package com.itee.bingsheng.annotation;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author gonglei
 */
public class AnnotationBeanPostProcessor extends PropertyPlaceholderConfigurer implements BeanPostProcessor,
        InitializingBean {

    private java.util.Properties pros;

    private Class[] enableClassList = {String.class, Integer.class, int.class, double.class};

    public void setEnableClassList(Class[] enableClassList) {
        this.enableClassList = enableClassList;
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.beans.factory.config.BeanPostProcessor#
     * postProcessAfterInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String arg1) throws BeansException {

        return bean;
    }

    private boolean filterType(String type) {
        if (type != null) {
            for (Class c : enableClassList) {
                if (c.toString().equals(type)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /*
     * (non-Javadoc)
     *
     * @seeorg.springframework.beans.factory.config.BeanPostProcessor#
     * postProcessBeforeInitialization(java.lang.Object, java.lang.String)
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String arg1) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {

            // if (logger.isDebugEnabled()) {
            // StringBuilder sb = new StringBuilder();
            // sb.append(" ========= ").append(field.getType()).append(" ============ ").append(
            // field.getName()).append(" ============ ").append(
            // field.isAnnotationPresent(Property.class));
            // logger.debug(sb.toString());
            // }
            if (field.isAnnotationPresent(Property.class)) {
                if (filterType(field.getType().toString())) {
                    Property p = field.getAnnotation(Property.class);
                    try {

                        ReflectionUtils.makeAccessible(field);
                        String property = pros.getProperty(p.name());
                        if (null != property) {
                            if (field.getType() == int.class) {
                                field.setInt(bean, Integer.parseInt(property));
                            } else if (field.getType() == long.class) {
                                field.setDouble(bean, Long.parseLong(property));
                            } else if (field.getType() == double.class) {
                                field.setDouble(bean, Double.parseDouble(property));
                            } else {
                                field.set(bean, property);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        return bean;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        pros = mergeProperties();

    }

}
