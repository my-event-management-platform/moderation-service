package com.eventmanagement.moderationservice.service;

import com.eventmanagement.moderationservice.model.Event;
import com.eventmanagement.moderationservice.repository.EventRepository;
import com.eventmanagement.shared.exceptions.EventNotFoundException;
import com.eventmanagement.shared.types.ReviewDecision;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventModerationService {
    private final EventRepository eventRepository;
    private final KafkaEventService kafkaEventService;

    @Transactional
    public void makeDecision(String eventId, ReviewDecision reviewDecision) {
        Event event = getEventById(eventId);
        kafkaEventService.processReviewEvent(event, reviewDecision);
        deleteEventById(eventId, false);
    }

    @Transactional(readOnly = true)
    public Event getEventById(String eventId) {
        Event event = eventRepository
                .findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Event with id " + eventId + " is not found"));
        return event;
    }

    @Transactional
    public void addEventForReviewal(Event event) {
        eventRepository.insert(event);
    }

    public Page<Event> getEvents(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Event> pageEvents = eventRepository.findAll(paging);
        return pageEvents;
    }

    @Transactional
    protected void deleteEventById(String eventId, boolean checkExistence) {
        if (checkExistence) {
            getEventById(eventId);
        }
        eventRepository.deleteById(eventId);
    }

}
