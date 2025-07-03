package com.grepp.spring.app.controller.api;

import com.grepp.spring.app.model.member.model.MemberDTO;
import com.grepp.spring.app.model.member.service.MemberService;
import com.grepp.spring.util.ReferencedException;
import com.grepp.spring.util.ReferencedWarning;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import java.util.List;
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
@RequestMapping(value = "/api/members", produces = MediaType.APPLICATION_JSON_VALUE)
public class MemberResource {

    private final MemberService memberService;

    public MemberResource(final MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<MemberDTO> getMember(@PathVariable(name = "userId") final String userId) {
        return ResponseEntity.ok(memberService.get(userId));
    }

    @PostMapping
    @ApiResponse(responseCode = "201")
    public ResponseEntity<String> createMember(@RequestBody @Valid final MemberDTO memberDTO) {
        final String createdUserId = memberService.create(memberDTO);
        return new ResponseEntity<>('"' + createdUserId + '"', HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<String> updateMember(@PathVariable(name = "userId") final String userId,
            @RequestBody @Valid final MemberDTO memberDTO) {
        memberService.update(userId, memberDTO);
        return ResponseEntity.ok('"' + userId + '"');
    }

    @DeleteMapping("/{userId}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteMember(@PathVariable(name = "userId") final String userId) {
        final ReferencedWarning referencedWarning = memberService.getReferencedWarning(userId);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        memberService.delete(userId);
        return ResponseEntity.noContent().build();
    }

}
