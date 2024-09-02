package com.depalma.froneus.challenge.domain.event;

import lombok.Data;

@Data
public class UserStatusEvent {

    private String userId;
    private String status; // "ACTIVE" o "INACTIVE"
    private String timestamp;

    public UserStatusEvent(String userId, String status, String timestamp) {
        this.userId = userId;
        this.status = status;
        this.timestamp = timestamp;
    }
}
