package com.itee.bingsheng.service;

import java.util.List;

/**
 * Created by db.liu on 2015/10/29.
 */
public interface IMailSenderService {

    void sendMail(String to, String format, String subject, String content) throws Exception;

//    void sendMail(String to, String format, String subject,
//                  String content, List<String> translatedAttachmentNames, List<String> originallyFileNames) throws Exception;

    void sendMail(
            String from, String to, String[] bcc, String format,
            String subject, String content,
            List<String> translatedAttachmentNames,
            List<String> originallyFileNames) throws Exception;

//    void sendMailBig(String to, String format, String subject,
//                     String content, List<String> translatedAttachmentNames, List<String> originallyFileNames) throws Exception;

    default void sendMailBig(
            String from, String to, String[] bcc, String format,
            String subject, String content,
            List<String> translatedAttachmentNames,
            List<String> originallyFileNames) throws Exception {

    }

}
