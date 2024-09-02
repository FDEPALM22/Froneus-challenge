package com.depalma.froneus.challenge.infrastructure.kafka;

import com.depalma.froneus.challenge.domain.event.UserStatusEvent;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaProducer {

    private final KafkaTemplate<String, UserStatusEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, UserStatusEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendUserStatus(UserStatusEvent event) {
        kafkaTemplate.send("user-status-topic", event);
    }
}
