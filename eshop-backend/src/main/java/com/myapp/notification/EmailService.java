package com.myapp.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService implements NotificationService {

    private final SesClient sesClient;

    @Override
    public void send(MessageSender messageSender) {
        log.info("Sending email to: {}", messageSender.to());
        SendEmailRequest request = SendEmailRequest.builder()
                .destination(Destination.builder().toAddresses(messageSender.to()).build())
                .message(Message.builder()
                        .subject(Content.builder().data(messageSender.subject()).build())
                        .body(Body.builder().text(Content.builder().data(messageSender.body()).build()).build())
                        .build())
                .source("test@mail.com")
                .build();
        sesClient.sendEmail(request);
    }
}