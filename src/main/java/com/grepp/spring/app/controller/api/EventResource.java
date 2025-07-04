package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.event.model.EventDTO;
import com.grepp.spring.app.model.event.service.EventService;
import com.grepp.spring.app.model.group.domain.Group;
import com.grepp.spring.app.model.group.repos.GroupRepository;
import com.grepp.spring.util.CustomCollectors;
import com.grepp.spring.util.ReferencedException;
import com.grepp.spring.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/events", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventResource {

    private final EventService eventService;
    private final GroupRepository groupRepository;

    public EventResource(final EventService eventService, final GroupRepository groupRepository) {
        this.eventService = eventService;
        this.groupRepository = groupRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventDTO>> getAllEvents() {
        return ResponseEntity.ok(eventService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventDTO> getEvent(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(eventService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEvent(@RequestBody @Valid final EventDTO eventDTO) {
        final Long createdId = eventService.create(eventDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEvent(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EventDTO eventDTO) {
        eventService.update(id, eventDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEvent(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = eventService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/groupValues")
    public ResponseEntity<Map<Long, String>> getGroupValues() {
        return ResponseEntity.ok(groupRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Group::getId, Group::getName)));
    }

}
