package com.eventmanagement.moderationservice.mapper;

import com.eventmanagement.moderationservice.model.Event;
import com.eventmanagement.shared.kafkaEvents.event.EventSubmitted;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class KafkaEventMapper {
    public abstract Event toEvent(EventSubmitted eventSubmitted);
}
