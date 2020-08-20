package com.peak.collection.email.component;

import com.alibaba.fastjson.JSONObject;
import com.peak.collection.email.exception.EmailException;
import com.peak.collection.email.model.SysParamModel;
import com.peak.collection.email.service.SysParamService;
import com.peak.common.model.EmailModel;
import com.peak.common.util.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@Component
public class MyJavaMailSender {

    @Autowired
    private JavaMailSenderImpl javaMailSender;
    private List<SysParamModel> list;
    private int count = -1;
    @Autowired
    private SysParamService sysParamService;

    private void applyProperties(JavaMailSenderImpl sender, SysParamModel param) {
        sender.setHost(param.getHost());
        if (param.getPort() != null) {
            sender.setPort(param.getPort());
        }
        sender.setUsername(param.getUsername());
        String pwd = AesUtil.decrypt(param.getPassword(), param.getUsername());
        sender.setPassword(pwd);
        sender.setProtocol(param.getProtocol());
        if (param.getEncoding() != null) {
            sender.setDefaultEncoding(param.getEncoding());
        }
        if (param.getOther() != null) {
            sender.setJavaMailProperties(asProperties(param.getOther()));
        }
    }

    private Properties asProperties(String otherInfo) {
        Map<String, String> map = (Map<String, String>) JSONObject.parse(otherInfo);
        Properties properties = new Properties();
        properties.putAll(map);
        return properties;
    }

    public void send(EmailModel emailModel) throws UnsupportedEncodingException, MessagingException {
        if(list == null || list.size() == 0) {
            list = getParamList();
            if(list.size() == 0) {
                throw new EmailException("发件配置没找到");
            }
        }
        SysParamModel param = findSendParam(emailModel);
        if (param == null) {
            throw new EmailException("发件配置没找到");
        }
        applyProperties(javaMailSender, param);
        doSend(emailModel, param);
    }

    private void doSend(EmailModel emailModel, SysParamModel param) throws UnsupportedEncodingException, MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(param.getUsername(),param.getPersonal());
        helper.setSubject(emailModel.getSubject());
        String[] tos = emailModel.getEmailTo().split(",");
        helper.setTo(tos);
        helper.setText(emailModel.getContent(), true);
        javaMailSender.send(message);
    }

    private SysParamModel findSendParam(EmailModel emailModel) {
        if(emailModel.getAllowFrom() != null) {
            return sysParamService.getByUsername(emailModel.getAllowFrom());
        }
        count++;
        return list.get(count%list.size());
    }

    private List<SysParamModel> getParamList() {
        List<SysParamModel> paramList = sysParamService.getParamList();
        return paramList;
    }
}
