package com.myapp.notification;

import com.myapp.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final SnsClient snsClient;
    private final UserService userService;

    public void sendSms(Long userId) {
        var user = userService.findById(userId);
        String msg = "Hello " + user.getName() + ", this is a notification from our SMS service.";

        var request = PublishRequest.builder()
                .message(msg)
                .phoneNumber(user.getPhone())
                .build();

        PublishResponse result = snsClient.publish(request);
        log.info("Sending SMS notification to client: {}, with phone number: {} - Message id: {}", user.getName(), user.getPhone(), result.messageId());
    }

}