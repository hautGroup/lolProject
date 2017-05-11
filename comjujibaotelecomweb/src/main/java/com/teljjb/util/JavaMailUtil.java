package com.teljjb.util;

import com.teljjb.result.UserResult;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

/**
 * Created by dezhonger on 2017/5/8.
 */
public final class JavaMailUtil {

    public static String myEmailAccount = "a373440955@163.com";
    public static String myEmailPassword = "nishizhu";
    public static String myEmailSMTPHost = "smtp.163.com";


    /**
     * 测试
     * @param email
     * @throws Exception
     */
    public static void sendMail(String email) throws Exception {

        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        MimeMessage message = createMimeMessage(session, myEmailAccount, email);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 测试
     * @param session
     * @param sendMail
     * @param receiveMail
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail) throws Exception {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "五黑小分队项目", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        message.setSubject("五黑小分队测试邮件", "UTF-8");
        message.setContent("sb用户你好, 这是一封登录发送邮件测试", "text/html;charset=UTF-8");

        message.setSentDate(new Date());
        return message;
    }


    /**
     * 发送激活邮件
     * @param email
     * @param user
     * @throws Exception
     */
    public static void sendForActive(String email, UserResult user) throws Exception {
        Properties props = new Properties();                    // 参数配置
        props.setProperty("mail.transport.protocol", "smtp");   // 使用的协议（JavaMail规范要求）
        props.setProperty("mail.smtp.host", myEmailSMTPHost);   // 发件人的邮箱的 SMTP 服务器地址
        props.setProperty("mail.smtp.auth", "true");            // 需要请求认证
        Session session = Session.getDefaultInstance(props);
        session.setDebug(false);
        MimeMessage message = createMimeMessage(session, myEmailAccount, email, user);
        Transport transport = session.getTransport();
        transport.connect(myEmailAccount, myEmailPassword);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    /**
     * 激活邮件内容设置
     * @param session
     * @param sendMail
     * @param receiveMail
     * @param user
     * @return
     * @throws Exception
     */
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, UserResult user)
            throws Exception {
        String code = MD5Util.getMD5Str("lol" + user.getNickname() + user.getId() + "lol");
        String url = PropertiesHelp.getProperty("projectDomain")+"/active/account?nickname="+user.getNickname()+"&code="+code;

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(sendMail, "五黑小分队项目", "UTF-8"));
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail, "XX用户", "UTF-8"));
        message.setSubject("五黑小分队测试邮件", "UTF-8");
        message.setContent(user.getNickname() + "用户你好, 这是一封激活邮件，请点击后激活您的账号，如果你没有在我们网站注册，请无视即可." + "     " + url,
                "text/html;" + "charset=UTF-8");

        message.setSentDate(new Date());
        return message;
    }

}
