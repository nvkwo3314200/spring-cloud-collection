package com.peak.collection.email.controller;

import com.peak.collection.email.service.EmailService;
import com.peak.common.model.EmailModel;
import com.peak.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("email-service")
public class EmailServiceController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/add")
    public Response add(EmailModel emailModel) {
        emailService.add(emailModel);
        return Response.success();
    }

    @PostMapping("/update")
    public Response update(EmailModel emailModel) {
        emailService.update(emailModel);
        return Response.success();
    }

    @PostMapping("/delete")
    public Response delete(Long id) {
        emailService.delete(id);
        return Response.success();
    }

    @PostMapping("/addAndSend")
    public Response addAndSend(@RequestBody EmailModel emailModel) {
        emailService.addAndSend(emailModel);
        return Response.success();
    }

    @PostMapping("/directSend")
    public Response directSend(@RequestBody EmailModel emailModel) {
        emailService.directSend(emailModel);
        return Response.success();
    }

    @PostMapping("/list")
    public Response list(EmailModel emailModel) {
        return Response.success(emailService.list(emailModel));
    }
}
