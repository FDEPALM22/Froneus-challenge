package com.depalma.froneus.challenge.application.services;

import com.depalma.froneus.challenge.domain.event.UserStatusEvent;
import com.depalma.froneus.challenge.domain.model.User;
import com.depalma.froneus.challenge.infrastructure.kafka.KafkaProducer;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {
    private final KafkaProducer kafkaProducer;

    public UserService(KafkaProducer kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    public void updateUserStatus(User user) {
        String status = user.isActive() ? "ACTIVE" : "INACTIVE";
        UserStatusEvent event = new UserStatusEvent(user.getId(), status, Instant.now().toString());
        kafkaProducer.sendUserStatus(event);
    }
}
