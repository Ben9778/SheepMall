package com.blackfiresoft.sheepmall.message;

import com.blackfiresoft.sheepmall.util.GenerateCode;
import jakarta.annotation.Resource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * 发送邮箱验证码服务类
 */
@Service
public class MailService {

    @Resource
    private JavaMailSender javaMailSender;

    public String sendEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        String code = GenerateCode.generateCode();
        message.setFrom("hb9778@163.com");
        message.setTo(to);
        String subject = "懂尔科技";
        message.setSubject(subject);
        String text = "尊敬的用户: \n\n验证码:[],请在1分钟内进行验证。\n\n如有问题请联系客服。\n\n感谢您的使用！".replace("[]", code);
        message.setText(text);
        javaMailSender.send(message);
        return code;
    }
}
