package com.peak.collection.aisdeploy.controller.service;

import com.peak.common.model.EmailModel;
import com.peak.common.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "collection-email-service")
public interface EmailService {
    @RequestMapping(value = "email-service/addAndSend",method = RequestMethod.POST)
    Response addAndSend(@RequestBody EmailModel emailModel);
}
