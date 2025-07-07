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
public class ModifyFavoritePlaceResponse {
  private Long favoritePlaceId;
  private String placeName;
  private double latitude;
  private double longitude;
  private LocalDateTime updatedAt;


}
