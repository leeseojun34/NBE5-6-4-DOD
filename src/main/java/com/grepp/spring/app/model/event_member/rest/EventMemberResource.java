package com.grepp.spring.app.model.event_member.rest;

import com.grepp.spring.app.model.event.domain.Event;
import com.grepp.spring.app.model.event.repos.EventRepository;
import com.grepp.spring.app.model.event_member.model.EventMemberDTO;
import com.grepp.spring.app.model.event_member.service.EventMemberService;
import com.grepp.spring.app.model.member.domain.Member;
import com.grepp.spring.app.model.member.repos.MemberRepository;
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
@RequestMapping(value = "/api/eventMembers", produces = MediaType.APPLICATION_JSON_VALUE)
public class EventMemberResource {

    private final EventMemberService eventMemberService;
    private final MemberRepository memberRepository;
    private final EventRepository eventRepository;

    public EventMemberResource(final EventMemberService eventMemberService,
            final MemberRepository memberRepository, final EventRepository eventRepository) {
        this.eventMemberService = eventMemberService;
        this.memberRepository = memberRepository;
        this.eventRepository = eventRepository;
    }

    @GetMapping
    public ResponseEntity<List<EventMemberDTO>> getAllEventMembers() {
        return ResponseEntity.ok(eventMemberService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventMemberDTO> getEventMember(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(eventMemberService.get(id));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<Long> createEventMember(
            @RequestBody @Valid final EventMemberDTO eventMemberDTO) {
        final Long createdId = eventMemberService.create(eventMemberDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEventMember(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EventMemberDTO eventMemberDTO) {
        eventMemberService.update(id, eventMemberDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteEventMember(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = eventMemberService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        eventMemberService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/memberValues")
    public ResponseEntity<Map<String, String>> getMemberValues() {
        return ResponseEntity.ok(memberRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Member::getId, Member::getPassword)));
    }

    @GetMapping("/eventValues")
    public ResponseEntity<Map<Long, String>> getEventValues() {
        return ResponseEntity.ok(eventRepository.findAll(Sort.by("id"))
                .stream()
                .collect(CustomCollectors.toSortedMap(Event::getId, Event::getTitle)));
    }

}
