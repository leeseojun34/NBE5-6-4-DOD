package com.grepp.spring.app.controller.api.mypage.payload;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyProfileResponse {
  private String memberId;
  private String profileImageNumber;
  private String name;
}
