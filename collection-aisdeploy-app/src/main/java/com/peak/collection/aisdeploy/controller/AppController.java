package com.peak.collection.aisdeploy.controller;

import com.peak.collection.aisdeploy.controller.service.EmailService;
import com.peak.common.model.EmailModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("deploy")
@Slf4j
public class AppController {

    @Autowired
    EmailService emailService;

    @Value("${email.to}")
    private String emailTo;
    @Value("${allow.from}")
    private String allowFrom;
    @Value("${svnlog.path}")
    private String svnLog;

    @GetMapping("notify")
    public String notify(String projectName) {
        try {
            String logPath = String.format(svnLog, projectName);
            String subject = projectName + "部署完成";
            String content = getContent(projectName, getSvnLogInfo(projectName, logPath));
            EmailModel emailModel = new EmailModel();
            emailModel.setSubject(subject);
            emailModel.setContent(content);
            emailModel.setEmailTo(emailTo);
            emailModel.setAllowFrom(allowFrom);
            emailService.addAndSend(emailModel);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("发送失败 {}", e.getMessage());
            return "fail";
        }
        return "success";
    }

    private String getSvnLogInfo(String projectName, String logPath) {
        if(projectName == null || projectName == "") {
            System.out.println("工程名不能为空");
        }
        if(logPath == null || logPath == "") {
            System.out.println("svn log 路径不能为空");
        }
        File file = new File(logPath);
        if(!file.exists()) {
            System.out.println("svn 文件不存在");
        }
        StringBuffer sb = new StringBuffer();
        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            List<String> collect = reader.lines().collect(Collectors.toList());
            for (String s : collect) {
                sb.append("<p style=\"marge-left:20px\">").append(s).append("</p>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String logContent = sb.toString().replaceAll("\\n", "");
        return logContent;
    }

    public String getContent(String projectName, String logContent) {
        StringBuffer sb = new StringBuffer();
        try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("temp/mail_temp");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"))){
            List<String> collect = reader.lines().collect(Collectors.toList());
            for (String s : collect) {
                sb.append(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = sb.toString().replaceAll("\\n", "").replaceAll("PROJECT_NAME", projectName).replaceAll("SVN_LOG", logContent);
        return result;
    }
}
