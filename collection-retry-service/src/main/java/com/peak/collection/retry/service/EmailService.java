package com.peak.collection.retry.service;

import com.peak.collection.retry.configuration.FeignConfiguration;
import com.peak.common.model.EmailModel;
import com.peak.common.util.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(value = "collection-email-service", configuration = FeignConfiguration. class)
public interface EmailService {

    @RequestMapping(value = "email-service/directSend",method = RequestMethod.POST)
    Response directSend(@RequestBody EmailModel emailModel);

    @RequestMapping(value = "email-service/list",method = RequestMethod.POST)
    //Response list(EmailModel emailModel);
    Response list(@RequestParam(value = "status") String status);

}
