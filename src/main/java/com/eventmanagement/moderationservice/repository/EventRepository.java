package com.eventmanagement.moderationservice.repository;

import com.eventmanagement.moderationservice.model.Event;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<Event, String> {
}
