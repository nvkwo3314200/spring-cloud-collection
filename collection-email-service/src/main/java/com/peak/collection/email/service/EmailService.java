package com.peak.collection.email.service;

import com.peak.common.model.EmailModel;

import java.util.List;

public interface EmailService {
    void add(EmailModel emailModel);

    void update(EmailModel emailModel);

    void delete(Long id);

    void addAndSend(EmailModel emailModel);

    int processing(Long id);

    List<EmailModel> list(EmailModel emailModel);

    void directSend(EmailModel emailModel);

}
