package com.itee.bingsheng.aspect.DataSource;

import org.apache.log4j.Logger;

import java.util.Date;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ApplicationLogging {

//    private static final Logger LOG = Logger.getLogger(ApplicationLogging.class);
//
//    public static void trace(String message,String ... strs){
//        String [] messages = message.split("？");
//        StringBuffer print = new StringBuffer();
//        for(int i = 0 ; i<(messages.length-1);i++){
//            print.append(i);
//            print.append(strs[i]);
//        }
//
//    }


    public void test1(){

        String className = "Test";
        String methodName = "update";
        Object [] arguments = new Object[]{"jdj","jdj","ieiei"};
        StringBuffer print = new StringBuffer();
        print.append("excuted:");
        print.append(className);
        print.append(".");
        print.append(methodName);
        print.append("(");
        for(int i = 0;i<arguments.length;i++){
            System.out.println(i+":"+arguments[i]+arguments.length);
            print.append(arguments[i]);
            if(i!=(arguments.length-1)){
                print.append(",");
            }
        }
        print.append(")");
        System.out.println(print.toString());
    }

    public final static void main(String [] args){
        System.out.println(new Date());

//        String message = "excute:?.?(?)";
//        String [] strs = new String []{"Test","update","20"};
//        String [] messages = message.split("？");
//        System.out.println(messages.length);
//        StringBuffer print = new StringBuffer();
//        for(int i = 0 ; i<(messages.length-1);i++){
//            System.out.println(messages[i]);
//        }
    }
}
