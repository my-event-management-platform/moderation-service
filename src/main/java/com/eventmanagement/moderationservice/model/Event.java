package com.eventmanagement.moderationservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Document
public class Event {
    @Id
    private String id;
    private String title;
    private String text;
    private String location;
    private Instant datetime;
    private Integer capacity;
    private String userId;
}
