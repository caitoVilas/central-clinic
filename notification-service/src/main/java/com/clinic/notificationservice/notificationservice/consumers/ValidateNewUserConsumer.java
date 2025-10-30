package com.clinic.notificationservice.notificationservice.consumers;

import com.clinic.commonservice.models.RegisterUser;
import com.clinic.notificationservice.notificationservice.services.contracts.MailSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Consumer for user registration messages.
 * This class listens to the "userTopic" Kafka topic and processes incoming RegisterUser messages.
 * It is part of the notification service.
 *
 * @author caito
 *
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ValidateNewUserConsumer {
    private final MailSender mailSender;

    /**
     * Listens to the "userTopic" Kafka topic and processes RegisterUser messages.
     *
     * @param msg the RegisterUser message received from the Kafka topic
     */
    @KafkaListener(topics = "userTopic", groupId = "user-service-group")
    public void handleRegister(RegisterUser msg){
        System.out.println("Recived message : " + msg.getEmail() + " " + msg.getUsername() + " "
                + msg.getValidationToken());
        Map<String, String> data = new HashMap<>();
        data.put("name", msg.getUsername());
        data.put("token", msg.getValidationToken());
        mailSender.sendEmailWithTemplate(new String[]{msg.getEmail()},
                "Account Activation - No Reply",
                "templates/account-activation.html",
                data);
    }
}
