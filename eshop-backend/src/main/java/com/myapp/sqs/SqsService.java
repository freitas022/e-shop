package com.myapp.sqs;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class SqsService {

    private final SqsTemplate sqsTemplate;

    public void sendMessage(String queueName, String message) {
        sqsTemplate.sendAsync(queueName, message);
        log.info("Message sent to queue {}", message);
    }
}
