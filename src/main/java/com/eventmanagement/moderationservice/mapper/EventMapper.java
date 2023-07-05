package com.eventmanagement.moderationservice.mapper;

import com.eventmanagement.moderationservice.model.Event;
import com.eventmanagement.shared.dto.response.EventResponseDTO;
import com.eventmanagement.shared.dto.response.PageEventsResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class EventMapper {
    public abstract EventResponseDTO toEventResponseDTO(Event event);

    @Mapping(target = "events", source = "content")
    @Mapping(target = "currentPage", source = "number")
    @Mapping(target = "totalItems", source = "totalElements")
    public abstract PageEventsResponseDTO toPageEventsResponseDTO(Page<Event> eventPage);
}
