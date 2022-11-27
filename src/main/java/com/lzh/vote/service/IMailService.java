package com.lzh.vote.service;

import java.util.List;

public interface IMailService {

    /**
     *  发送文本邮件
     *
     * @param to  接收人
     * @param subject 主题
     * @param content  邮件内容
     */
    void sendSimpleMail(String to,String subject,String content);

    /**
     * 批量发送文本邮件
     * @param tos 接收人List
     * @param subject 主题
     * @param content 邮件内容
     */
    void batchSendSimpleMail(List<String> tos, String subject, String content);

    void sendHtmlMail(String to,String subject,String content) throws Exception;

    void sendAttachmentMail(String to,String subject,String content,String filepath) throws Exception;

}
