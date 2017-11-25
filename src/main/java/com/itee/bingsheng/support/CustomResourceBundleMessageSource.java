package com.itee.bingsheng.support;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.ReflectionUtils;

import javax.servlet.ServletContext;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by gonglei on 2015/3/19.
 */
public class CustomResourceBundleMessageSource extends ResourceBundleMessageSource {

    public void printMessage(ServletContext servletContext) throws NoSuchFieldException, IllegalAccessException, IOException {
        Field field = getClass().getSuperclass().getDeclaredField("basenames");
        ReflectionUtils.makeAccessible(field);
        String[] basenames = (String[]) field.get(this);
//        writeFile(servletContext,Locale.PRC,basenames);
//        writeFile(servletContext,Locale.US,basenames);
    }

    public void writeFile(ServletContext servletContext, Locale locale, String[] basenames) throws IOException {
        String filePath = servletContext.getRealPath("js/messages_" + locale.toString() + ".js");
        OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(filePath), "UTF-8");
        for (String basename : basenames) {
            out.write("var " + basename + "={");
            ResourceBundle resourceBundle = getResourceBundle(basename, locale);
            Enumeration<String> dd = resourceBundle.getKeys();
            boolean first = true;
            while (dd.hasMoreElements()) {
                String key = dd.nextElement();
                String message = resourceBundle.getString(key);
                if (!first) {
                    out.write(",");
                }
                out.write("\"" + key + "\":\"" + message + "\"");
//                System.out.println(key+ "   "+message);
                first = false;
            }
            out.write("};");
        }
        out.flush();
        out.close();
    }
}
