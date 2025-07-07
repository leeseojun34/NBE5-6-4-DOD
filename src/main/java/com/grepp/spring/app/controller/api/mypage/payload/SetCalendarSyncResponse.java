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
public class SetCalendarSyncResponse {
  private boolean isEnabled; // 동기화 on/off 여부
  private LocalDateTime lastSyncAt; // 마지막 동기화 시점

}
