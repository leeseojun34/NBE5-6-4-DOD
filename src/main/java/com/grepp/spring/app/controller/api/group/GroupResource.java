package com.grepp.spring.app.controller.api.group;


import com.grepp.spring.app.controller.api.group.payload.ShowGroupMemberResponse;
import com.grepp.spring.infra.response.ApiResponse;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/groups",  produces = MediaType.APPLICATION_JSON_VALUE)
public class GroupResource {

    @GetMapping
    public ResponseEntity<ApiResponse<ShowGroupMemberResponse>> getGroupMembers(@RequestParam Long groupId){
        ShowGroupMemberResponse response = ShowGroupMemberResponse.builder()
            .userIds(new ArrayList<>(List.of(12341234L, 23423424L)))
                .build();


        return ResponseEntity.ok(ApiResponse.success(response));
    }
}