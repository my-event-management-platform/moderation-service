package com.eventmanagement.moderationservice.controller;

import com.eventmanagement.shared.dto.response.PageEventsResponseDTO;
import com.eventmanagement.shared.types.ReviewDecision;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/moderation/events")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class EventModerationController {
    @PostMapping("/{event_id}/{decision}")
    public ResponseEntity<Void> makeDecision(@PathVariable("event_id") String eventId,
                                             @PathVariable("decision") ReviewDecision reviewDecision) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping()
    public ResponseEntity<PageEventsResponseDTO> getEventsToBeReviewed(@RequestParam(required = false, defaultValue = "0") @Min(0) int page,
                                                                       @RequestParam(required = false, defaultValue = "10") @Min(0) int size) {
        return new ResponseEntity<>(new PageEventsResponseDTO(), HttpStatus.OK);
    }
}
