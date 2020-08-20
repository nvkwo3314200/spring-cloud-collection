package com.peak.collection.email.mq;

import com.alibaba.fastjson.JSONObject;
import com.peak.collection.email.component.MyJavaMailSender;
import com.peak.collection.email.service.EmailService;
import com.peak.collection.email.util.EmailStatus;
import com.peak.common.model.EmailModel;
import com.peak.common.util.EmailStreamClient;
import com.peak.common.util.RabbitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@EnableBinding(value = {EmailStreamClient.class})
@Slf4j
public class EmailDirectReceiver {

    @Autowired
    EmailService emailService;

    @Autowired
    MyJavaMailSender myJavaMailSender;

    @StreamListener(EmailStreamClient.INPUT)
    public void process(String emailMessage) {
        log.info("DirectReceiver消费者收到消息  : {} ",  emailMessage);
        EmailModel emailModel = JSONObject.parseObject(emailMessage, EmailModel.class);
        try {
            if(emailModel.getId() != null) { // 幂等处理
                int i = emailService.processing(emailModel.getId());
                if(i == 0) return;
            }
            myJavaMailSender.send(emailModel);
            emailModel.setStatus(EmailStatus.SUCCESS.name());
            emailModel.setSendTime(new Date());
        } catch (Exception e) {
            if(EmailStatus.FAIL.name().equals(emailModel.getStatus())) {
                int retry = emailModel.getRetry();
                emailModel.setRetry(++retry);
            }
            emailModel.setStatus(EmailStatus.FAIL.name());
            log.error("发送失败, email id: {}, reason: {}", emailModel.getId(), e.getMessage());
        }
        if(emailModel.getId() != null) {
            emailService.update(emailModel);
        }
    }
 
}