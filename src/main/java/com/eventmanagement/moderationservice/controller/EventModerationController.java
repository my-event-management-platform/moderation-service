package com.eventmanagement.moderationservice.controller;

import com.eventmanagement.moderationservice.mapper.EventMapper;
import com.eventmanagement.moderationservice.model.Event;
import com.eventmanagement.moderationservice.service.EventModerationService;
import com.eventmanagement.shared.dto.response.EventResponseDTO;
import com.eventmanagement.shared.dto.response.PageEventsResponseDTO;
import com.eventmanagement.shared.types.ReviewDecision;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderation/events")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventModerationController {
    private final EventModerationService eventModerationService;
    private final EventMapper eventMapper;
    @PostMapping("/{event_id}/{decision}")
    public ResponseEntity<Void> makeDecision(@PathVariable("event_id") String eventId,
                                             @PathVariable("decision") ReviewDecision reviewDecision) {
        eventModerationService.makeDecision(eventId, reviewDecision);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{event_id}")
    public ResponseEntity<EventResponseDTO> getEvent(@PathVariable("event_id") String eventId) {
        Event event = eventModerationService.getEventById(eventId);
        EventResponseDTO eventResponseDTO = eventMapper.toEventResponseDTO(event);
        return new ResponseEntity<>(eventResponseDTO, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<PageEventsResponseDTO> getEventsToBeReviewed(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                                       @RequestParam(required = false, defaultValue = "10") @Min(0) int size) {
        Page<Event> pageEvents = eventModerationService.getEvents(page, size);
        PageEventsResponseDTO pageEventsResponseDTO = eventMapper.toPageEventsResponseDTO(pageEvents);
        return new ResponseEntity<>(pageEventsResponseDTO, HttpStatus.OK);
    }
}
