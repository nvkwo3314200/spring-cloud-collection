package com.peak.common.util;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface EmailStreamClient {
    String INPUT = "emailMessageInput";
    String OUTPUT = "emailMessageOutput";

    @Input(EmailStreamClient.INPUT)
    SubscribableChannel input();

    @Output(EmailStreamClient.OUTPUT)
    MessageChannel output();
}
