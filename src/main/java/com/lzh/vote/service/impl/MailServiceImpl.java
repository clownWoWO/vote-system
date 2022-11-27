package com.lzh.vote.service.impl;

import com.lzh.vote.service.IMailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.List;

@Service
@Slf4j
public class MailServiceImpl implements IMailService {
    @Value("${spring.mail.username}")
    private String from;
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(from);
        mailSender.send(message);
    }

    @Override
    public void batchSendSimpleMail(List<String> tos, String subject, String content) {
        log.info("邮件发送内容为：{}",content);
        for (String to : tos) {
            this.sendSimpleMail(to,subject,content);
            log.info("邮件发送成功，收件人为：{}",to);
        }
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) throws Exception {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);

        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendAttachmentMail(String to, String subject, String content, String filepath) throws Exception {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message,true);
        helper.setFrom(from);
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(content,true);

        //文件流:获取本地文件
        FileSystemResource file = new FileSystemResource(new File(filepath));
        String filename = file.getFilename();
        //可以发送多个
        helper.addAttachment(filename,file);

        //进行发送
        mailSender.send(message);
    }
}
