package com.peak.collection.email.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.peak.collection.email.mapper.EmailMapper;
import com.peak.collection.email.service.EmailService;
import com.peak.collection.email.util.EmailStatus;
import com.peak.collection.email.util.EmailType;
import com.peak.common.model.EmailModel;
import com.peak.common.util.EmailStreamClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

    @Autowired
    private EmailMapper emailMapper;

    @Autowired
    private EmailStreamClient emailStreamClient;

    @Override
    public void add(EmailModel emailModel) {
        emailModel.setEmailType(EmailType.NORMAL.name());
        emailModel.setCreateDate(new Date());
        emailModel.setLastUpdateDate(new Date());
        emailModel.setStatus(EmailStatus.NEW.name());
        emailModel.setRetry(0);
        emailMapper.add(emailModel);
    }

    @Override
    public void update(EmailModel emailModel) {
        emailModel.setLastUpdateDate(new Date());
        emailMapper.update(emailModel);
    }

    @Override
    public void delete(Long id) {
        emailMapper.delete(id);
    }

    @Override
    public void addAndSend(EmailModel emailModel) {
        add(emailModel);
        try {
            new Thread(() -> {
                String emailModelStr = JSONObject.toJSONString(emailModel);
                emailStreamClient.output().send(MessageBuilder.withPayload(emailModelStr).build());
            }).start();
        }catch (Exception e) {
            log.error("发送失败， 异常忽略， {}", e.getMessage());
        }
    }

    @Override
    public void directSend(EmailModel emailModel) {
        String emailModelStr = JSONObject.toJSONString(emailModel);
        emailStreamClient.output().send(MessageBuilder.withPayload(emailModelStr).build());
    }

    @Override
    public int processing(Long id) {
        return emailMapper.processing(id);
    }

    @Override
    public List<EmailModel> list(EmailModel emailModel) {
        return emailMapper.list(emailModel);

    }
}
