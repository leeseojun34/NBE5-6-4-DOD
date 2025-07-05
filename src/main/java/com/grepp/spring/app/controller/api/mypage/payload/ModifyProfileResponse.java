package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyProfileResponse {
  private Long memberId;
  private String profileImageNumber;
  private String name;
  private LocalDateTime updatedAt;
}
