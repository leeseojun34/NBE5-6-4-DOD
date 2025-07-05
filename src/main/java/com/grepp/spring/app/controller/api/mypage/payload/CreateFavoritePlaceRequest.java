package com.grepp.spring.app.controller.api.mypage.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateFavoritePlaceRequest {
  private String placeName;
  private double latitude;
  private double longitude;

}
