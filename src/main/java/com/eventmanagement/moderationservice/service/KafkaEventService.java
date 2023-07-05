package com.eventmanagement.moderationservice.service;

import com.eventmanagement.moderationservice.mapper.KafkaEventMapper;
import com.eventmanagement.moderationservice.model.Event;
import com.eventmanagement.shared.kafkaEvents.KafkaEvent;
import com.eventmanagement.shared.kafkaEvents.event.EventReviewed;
import com.eventmanagement.shared.kafkaEvents.event.EventSubmitted;
import com.eventmanagement.shared.types.ReviewDecision;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventService {
    private final KafkaTemplate<String, KafkaEvent> kafkaTemplate;
    private final KafkaEventMapper kafkaEventMapper;
    private final EventModerationService eventModerationService;

    @Autowired
    public KafkaEventService(KafkaTemplate<String, KafkaEvent> kafkaTemplate,
                             KafkaEventMapper kafkaEventMapper,
                             @Lazy EventModerationService eventModerationService) {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaEventMapper = kafkaEventMapper;
        this.eventModerationService = eventModerationService;
    }

    public void processReviewEvent(Event event, ReviewDecision decision) {
        EventReviewed eventReviewed = new EventReviewed(event.getId(), decision);
        sendKafkaEvent(eventReviewed);
    }

    private void sendKafkaEvent(KafkaEvent kafkaEvent) {
        Message<KafkaEvent> message = MessageBuilder
                .withPayload(kafkaEvent)
                .setHeader(KafkaHeaders.TOPIC, kafkaEvent.getTopic())
                .build();
        kafkaTemplate.send(message);
    }

    @KafkaListener(topics = "event-submitted-kafka-events")
    private void consumeEventSubmitted(EventSubmitted eventSubmitted) {
        Event event = kafkaEventMapper.toEvent(eventSubmitted);
        eventModerationService.addEventForReviewal(event);
    }
}
