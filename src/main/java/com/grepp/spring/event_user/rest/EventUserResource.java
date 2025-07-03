package com.grepp.spring.event_user.rest;

import com.grepp.spring.event.domain.Event;
import com.grepp.spring.event.repos.EventRepository;
import com.grepp.spring.event_user.model.EventUserDTO;
import com.grepp.spring.event_user.service.EventUserService;
import com.grepp.spring.member.domain.Member;
import com.grepp.spring.member.repos.MemberRepository;
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
@RequestMapping(value = "/api/eventUsers", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventUserResource {

    private final EventUserService eventUserService;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    public EventUserResource(final EventUserService eventUserService,
            final MemberRepository memberRepository, final EventRepository eventRepository) {
        this.eventUserService = eventUserService;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventUserDTO>> getAllEventUsers() {
        return ResponseEntity.ok(eventUserService.findAll());
    }

    @GetMapping("/{eventUserId}")
    public ResponseEntity<EventUserDTO> getEventUser(
            @PathVariable(name = "eventUserId") final Long eventUserId) {
        return ResponseEntity.ok(eventUserService.get(eventUserId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEventUser(
            @RequestBody @Valid final EventUserDTO eventUserDTO) {
        final Long createdEventUserId = eventUserService.create(eventUserDTO);
        return new ResponseEntity<>(createdEventUserId, HttpStatus.CREATED);
    }

    @PutMapping("/{eventUserId}")
    public ResponseEntity<Long> updateEventUser(
            @PathVariable(name = "eventUserId") final Long eventUserId,
            @RequestBody @Valid final EventUserDTO eventUserDTO) {
        eventUserService.update(eventUserId, eventUserDTO);
        return ResponseEntity.ok(eventUserId);
    }

    @DeleteMapping("/{eventUserId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventUser(
            @PathVariable(name = "eventUserId") final Long eventUserId) {
        final ReferencedWarning referencedWarning = eventUserService.getReferencedWarning(eventUserId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventUserService.delete(eventUserId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/userValues")
    public ResponseEntity<Map<String, String>> getUserValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("userId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getUserId, Member::getPassword)));
    }

    @GetMapping("/eventValues")
    public ResponseEntity<Map<Long, String>> getEventValues() {
        return ResponseEntity.ok(eventRepository.findAll(Sort.by("eventId"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Event::getEventId, Event::getTitle)));
    }

}
