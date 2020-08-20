package com.peak.collection.retry.configuration;

import com.alibaba.fastjson.JSONObject;
import com.peak.collection.retry.service.EmailService;
import com.peak.common.model.EmailModel;
import com.peak.common.util.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
@Slf4j
public class SaticScheduleTask {

    @Autowired
    private EmailService emailService;

    //3.添加定时任务
    @Scheduled(cron = "0 0/1 * * * ?")
    //或直接指定时间间隔，例如：1分钟
    private void configureTasks() {
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
        EmailModel emailModel = new EmailModel();
        emailModel.setStatus("NEW");
        doSend(emailModel);
        emailModel.setStatus("FAIL");
        doSend(emailModel);
    }

    private void doSend(EmailModel emailModel) {
        Response response = emailService.list(emailModel.getStatus());
        if(response.getCode() == 200) {
            List<Map> modelList = (List<Map>) response.getResult();
            modelList.forEach(map -> {
                try {
                    EmailModel item = convertMapToBean(map, EmailModel.class);
                    if(item != null) {
                       emailService.directSend(item);
                    }
                } catch (Exception e) {
                    log.error("发送失败， {}", e.getMessage());
                }
            });
        }
    }

    private static <T> T convertMapToBean(Map map, Class<T> clazz) {
        T t = JSONObject.parseObject(JSONObject.toJSONString(map), clazz);
        return t;
    }
}