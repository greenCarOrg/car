package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.service.IMailSenderService;
import com.itee.bingsheng.utils.TKFile;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.stereotype.Service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Header;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

/**
 * Created by db.liu on 2015/10/29.
 */
@Service
public class MailSenderService implements IMailSenderService {
    /**
     * 以 html 方式发送 产品
     */
    public static final String SENT_TYPE_HTML = "HTML";
    /**
     * 以 text 方式发送 产品
     */
    public static final String SEND_TYPE_TEXT = "PLAIN";

    public static final String EMAIL_TITLE = "你已经成为了系统的用户";

    protected final Logger logger = Logger.getLogger(getClass());

    public JavaMailSenderImpl connect() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setDefaultEncoding("UTF-8");
        String smtpserver = TKFile.getPropValue("emailConfig.properties", "MAILSMTPSERVER");
        if (StringUtils.isBlank(smtpserver)) {
            smtpserver = "smtp.kocla.com";
        }

        String username = TKFile.getPropValue("emailConfig.properties", "MAILUSERNAME");
        if (StringUtils.isBlank(username)) {
            username = "erp@kocla.com";
        }

        String pwd = TKFile.getPropValue("emailConfig.properties", "MAILPWD");
        if (StringUtils.isBlank(pwd)) {
            pwd = "2015@kocla";
        }
        logger.info("Use Smtp Auth:" + username +
                "/" + pwd);
        Properties p = new Properties();
        p.put("mail.smtp.auth", "true");
        mailSender.setHost(smtpserver);
        mailSender.setJavaMailProperties(p);
        mailSender.setUsername(username);
        mailSender.setPassword(pwd);
        return mailSender;
    }

    public String getEmailContext(String userAccount, String psw) {
        if (userAccount.trim().equals("") || psw.trim().equals("")) {
            return "账号或密码为空，请联系管理员!";
        }
        StringBuffer sb = new StringBuffer("尊敬的客户，您已经成功成为了ERP系统的用户，");
        sb.append("账号是：" + userAccount + ",");
        sb.append("密码是：" + psw + ",");
        sb.append("请点击地址 " + TKFile.getPropValue("emailConfig.properties", "ProjectAddress") + " 下载ERP系统，登录使用！");
        return sb.toString();
    }

    public void sendMail(String to, String format, String subject, String content) throws Exception {
        String sSender = TKFile.getPropValue("emailConfig.properties", "FORMEMAIL");
        this.sendMail(sSender,
                to, null, format, subject, content,
                new ArrayList<String>(), new ArrayList<String>());
    }


//    public void sendMail(String to, String format, String subject, String content,
//                         List<String> translatedAttachmentNames, List<String> originallyFileNames) throws Exception {
//        String sSender = TKFile.getPropValue("emailConfig.properties", "FormEmail");
//        this.sendMail(sSender,
//                to, null, format, subject, content, translatedAttachmentNames, originallyFileNames);
//    }


//    public void sendMailBig(String to, String format, String subject, String content,
//                            List<String> translatedAttachmentNames, List<String> originallyFileNames) throws Exception {
//        String sSender = TKFile.getPropValue("emailConfig.properties", "FormEmail");
//        this.sendMailBig(sSender,
//                to, null, format, subject, content, translatedAttachmentNames, originallyFileNames);
//    }

    /**
     * @param from
     * @param to
     * @param bcc
     * @param format
     * @param subject
     * @param content
     * @param translatedAttachmentNames
     * @param originallyFileNames
     * @throws Exception
     */
    public void sendMail(
            String from, String to, String[] bcc, String format,
            String subject, String content,
            List<String> translatedAttachmentNames,
            List<String> originallyFileNames) throws Exception {
        JavaMailSenderImpl mailSender = connect();
        MimeMessage message = mailSender.createMimeMessage();

        //用来 对message进行装配的 装饰类...
        MimeMailMessage mm = new MimeMailMessage(message);

        //处理from
        if (from == null) {
            String _from = TKFile.getPropValue("emailConfig.properties", "FORMEMAIL");
            if (StringUtils.isNotBlank(_from)) {
                //from 携宁科技 < lei.yang@sinitek.com > 方式在前面加入了中文名字,所以需要解码后重新编码.
                mm.setFrom(new String(_from.getBytes("UTF-8"), "ISO-8859-1"));
//               mm.setFrom( MimeUtility.encodeText(_from) );
            }
        } else {
            //from 携宁科技 < lei.yang@sinitek.com > 方式在前面加入了中文名字,所以需要解码后重新编码.
            mm.setFrom(new String(from.getBytes("UTF-8"), "ISO-8859-1"));

        }
        //end

        if (StringUtils.isNotBlank(to)) {
            if (to.indexOf(",") != -1 || to.indexOf(";") != -1) {
                to = to.trim().replace(",", ";");
                mm.setTo(to.split(";"));
            } else {
                mm.setTo(to.trim());
            }
        }
        if (bcc != null && bcc.length > 0) {
            mm.setBcc(bcc);
        }

        mm.setSubject(subject);
//        mm.setSubject( new String( subject.getBytes("GBK"),"ISO-8859-1"));

        //****MultiPart*****
        Multipart mp = new MimeMultipart();
        MimeBodyPart mbp = new MimeBodyPart();


        //add by yanglei 2009-4-1 支持发送方式的配置化...
        if (SENT_TYPE_HTML.equalsIgnoreCase(format))//html方式发送...
        {

            mbp.setText(content, "UTF-8");
            // 放在setText()后面,因为setText()默认将Content-Type设置为 text/plain
            mbp.setHeader("Content-Type", "text/html;charset=\"UTF-8\"");
        } else {
            //text文本方式发送...
            content = content + "\r\n";
            mbp.setText(content, "UTF-8");
            mbp.setHeader("Content-Type", "text/plain;charset=\"UTF-8\"");
        }

        mp.addBodyPart(mbp);

        if (translatedAttachmentNames.size() != originallyFileNames.size()) {
            logger.info("处理后的附件 全路径名 数量 和 原始文件名 对应数量不匹配,不发送...");
            return;
        } else {
            for (int i = 0; i < translatedAttachmentNames.size(); i++) {
                String name = translatedAttachmentNames.get(i);
                File file = new File(name);
                if (!file.exists()) {
                    throw new IOException("File Not Exists:" + name);
                }

                MimeBodyPart mbp2 = new MimeBodyPart();
                DataSource source = new FileDataSource(file);
                mbp2.setDataHandler(new DataHandler(source));
                if (originallyFileNames.get(i) != null) {
                    String h = MimeUtility.encodeText(originallyFileNames.get(i));
                    h = h.replaceAll("\r", "").replaceAll("\n", "");
                    mbp2.setFileName(h);
                } else {
                    logger.info("无法匹配原文件名");
                }
                logger.info(mbp2.getFileName());
                Enumeration e = mbp2.getAllHeaders();
                while (e.hasMoreElements()) {
                    Header header = (Header) e.nextElement();
                    logger.info(header.getName() + "->" + header.getValue());
                }

                mp.addBodyPart(mbp2);
            }
        }
        message.setContent(mp);
        //****Multipart****
        long t1 = System.currentTimeMillis();
        logger.info("before send mail  , currentTime:" + to + "\t" + t1);

        mailSender.send(message);
        long t2 = System.currentTimeMillis();
        logger.info("after send mail  , currentTime:" + to + "\t" + t2 + "\n 耗时:" + (t2 - t1));
    }

    public void sendMailBig(
            String from, String to, String[] bcc, String format,
            String subject, String content,
            List<String> translatedAttachmentNames,
            List<String> originallyFileNames) throws Exception {
        int attachmentNo = 0;
        List<String> translatedAttachmentNames_ = translatedAttachmentNames;
        do {
            attachmentNo = 0;
            JavaMailSenderImpl mailSender = connect();
            MimeMessage message = mailSender.createMimeMessage();

            //用来 对message进行装配的 装饰类...
            MimeMailMessage mm = new MimeMailMessage(message);

            //处理from
            if (from == null) {
                String _from = TKFile.getPropValue("emailConfig.properties", "FORMEMAIL");
                if (StringUtils.isNotBlank(_from)) {
                    //from 携宁科技 < lei.yang@sinitek.com > 方式在前面加入了中文名字,所以需要解码后重新编码.
                    mm.setFrom(new String(_from.getBytes("UTF-8"), "ISO-8859-1"));
//               mm.setFrom( MimeUtility.encodeText(_from) );
                }
            } else {
                //from 携宁科技 < lei.yang@sinitek.com > 方式在前面加入了中文名字,所以需要解码后重新编码.
                mm.setFrom(new String(from.getBytes("UTF-8"), "ISO-8859-1"));

            }
            //end

            if (StringUtils.isNotBlank(to)) {
                if (to.indexOf(",") != -1 || to.indexOf(";") != -1) {
                    to = to.trim().replace(",", ";");
                    mm.setTo(to.split(";"));
                } else {
                    mm.setTo(to.trim());
                }
            }
            if (bcc != null && bcc.length > 0) {
                mm.setBcc(bcc);
            }

            mm.setSubject(subject);
//        mm.setSubject( new String( subject.getBytes("GBK"),"ISO-8859-1"));

            //****MultiPart*****
            Multipart mp = new MimeMultipart();
            MimeBodyPart mbp = new MimeBodyPart();


            //add by yanglei 2009-4-1 支持发送方式的配置化...
            if (SENT_TYPE_HTML.equalsIgnoreCase(format))//html方式发送...
            {

                mbp.setText(content, "UTF-8");
                // 放在setText()后面,因为setText()默认将Content-Type设置为 text/plain
                mbp.setHeader("Content-Type", "text/html;charset=\"UTF-8\"");
            } else {
                //text文本方式发送...
                content = content + "\r\n";
                mbp.setText(content, "UTF-8");
                mbp.setHeader("Content-Type", "text/plain;charset=\"UTF-8\"");
            }

            mp.addBodyPart(mbp);

            long fileLength = 0;//文件大小
//            long maxLength = 8388608;
            long maxLength = 409600;

            if (translatedAttachmentNames_.size() != originallyFileNames.size()) {
                logger.info("处理后的附件 全路径名 数量 和 原始文件名 对应数量不匹配,不发送...");
                return;
            } else {
                for (int i = 0; i < translatedAttachmentNames_.size(); i++) {
                    String name = translatedAttachmentNames_.get(i);
//                logger.info("translatedAttachmentName["+i+"] :"+name);
                    File file = new File(name);
                    fileLength += file.length();
                    if (fileLength > maxLength) {
                        attachmentNo = 1;//分批次发送
                        int k = i;
                        for (int j = 0; j < translatedAttachmentNames_.size(); j++) {
                            if (j < k) {
                                translatedAttachmentNames_.remove(j);
                                originallyFileNames.remove(j);
                                --j;//删除了元素，迭代的下标也跟着改变
                                --k;
                            }
                        }
                        break;
                    }
                    if (!file.exists()) {
                        throw new IOException("File Not Exists:" + name);
                    }

                    MimeBodyPart mbp2 = new MimeBodyPart();
                    DataSource source = new FileDataSource(file);
                    mbp2.setDataHandler(new DataHandler(source));
                    if (originallyFileNames.get(i) != null) {
                        String h = MimeUtility.encodeText(originallyFileNames.get(i));
                        h = h.replaceAll("\r", "").replaceAll("\n", "");
                        mbp2.setFileName(h);
                        // mbp2.setFileName( MimeUtility.encodeText(originallyFileNames.get(i)));
                        // mbp2.setFileName(MimeUtility.encodeText(originallyFileNames.get(i),"GBK",null));
                        // mbp2.setFileName(("=?GB2312?B?"+MimeUtility.encode(fileName.getBytes())+"?="););//附件名称 设置成原文件名...
                    } else {
                        logger.info("无法匹配原文件名");
                    }
                    logger.info(mbp2.getFileName());
                    Enumeration e = mbp2.getAllHeaders();
                    while (e.hasMoreElements()) {
                        Header header = (Header) e.nextElement();
                        logger.info(header.getName() + "->" + header.getValue());
                    }

                    mp.addBodyPart(mbp2);
                }
            }

            message.setContent(mp);
            //****Multipart****

            long t1 = System.currentTimeMillis();
            logger.info("before send mail  , currentTime:" + to + "\t" + t1);

            mailSender.send(message);
            long t2 = System.currentTimeMillis();
            logger.info("after send mail  , currentTime:" + to + "\t" + t2 + "\n 耗时:" + (t2 - t1));
        } while (attachmentNo > 0);
    }


//    public void sentSampleMail(String from,
//                               String to,
//                               String subject,
//                               String content)
//            throws Exception {
//        JavaMailSenderImpl mailSender = this.connect();
//
//        MimeMessage message = mailSender.createMimeMessage();
//
//        //用来 对message进行装配的 装饰类...
//        MimeMailMessage mm = new MimeMailMessage(message);
//
//
//        if (from == null) {
//            String _from = TKFile.getPropValue("emailConfig.properties", "FormEmail");;
//            if (StringUtils.isNotBlank(_from)) {
//                mm.setFrom(new String(_from.getBytes("UTF-8"), "ISO-8859-1"));
//            }
//        } else {
//            mm.setFrom(new String(from.getBytes("UTF-8"), "ISO-8859-1"));
//
//        }
//
//        if (StringUtils.isNotBlank(to)) {
//            mm.setTo(to.trim());
//        }
//        mm.setSubject(subject);
////        mm.setSubject( new String( subject.getBytes("GBK"),"ISO-8859-1"));
//
//        //****MultiPart*****
//        Multipart mp = new MimeMultipart();
//        MimeBodyPart mbp = new MimeBodyPart();
//        //add by yanglei 2009-4-1 支持发送方式的配置化...
//        mbp.setText(content, "UTF-8");
//        // 放在setText()后面,因为setText()默认将Content-Type设置为 text/plain
//        mbp.setHeader("Content-Type", "text/html;charset=\"UTF-8\"");
//        mp.addBodyPart(mbp);
//        message.setContent(mp);
//        mailSender.send(message);
//    }

}
