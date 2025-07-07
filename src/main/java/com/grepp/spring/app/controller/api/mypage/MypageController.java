package com.grepp.spring.app.controller.api.mypage;


import com.grepp.spring.app.controller.api.mypage.payload.CreateFavoritePlaceRequest;
import com.grepp.spring.app.controller.api.mypage.payload.CreateFavoritePlaceResponse;
import com.grepp.spring.app.controller.api.mypage.payload.CreateFavoriteTimeRequest;
import com.grepp.spring.app.controller.api.mypage.payload.CreateFavoriteTimeResponse;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyFavoritePlaceRequest;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyFavoritePlaceResponse;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyFavoriteTimeRequest;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyFavoriteTimeResponse;
import com.grepp.spring.app.controller.api.mypage.payload.ModifyProfileResponse;
import com.grepp.spring.app.controller.api.mypage.payload.SetCalendarSyncRequest;
import com.grepp.spring.app.controller.api.mypage.payload.SetCalendarSyncResponse;
import com.grepp.spring.infra.response.ApiResponse;
import com.grepp.spring.infra.response.ResponseCode;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class MypageController {

  // 즐겨찾기 장소 등록
  @PostMapping("/favorite-locations/{memberId}")
  @Operation(summary = "즐겨찾기 장소 등록", description = "회원 즐겨찾기 장소 등록")
  public ResponseEntity<ApiResponse<CreateFavoritePlaceResponse>> createFavoriteLocation(
      @RequestBody @Valid CreateFavoritePlaceRequest request) {

    try {
      CreateFavoritePlaceResponse response = new CreateFavoritePlaceResponse();
      response.setFavoritePlaceId(100L);
      response.setPlaceName("스타벅스 홍대입구역점");
      response.setLatitude(37.5561);
      response.setLongitude(126.9229);
      response.setCreatedAt(LocalDateTime.now());

      return ResponseEntity.ok(ApiResponse.success("즐겨찾기 장소가 정상적으로 등록되었습니다."));

    } catch (Exception e) {
      if (e instanceof AuthenticationException) {
        return ResponseEntity.status(401)
            .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
      }
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었습니다."));
    }
  }

  // 즐겨찾기 시간대 등록
  @PostMapping("/favorite-timetable/{memberId}")
  @Operation(summary = "즐겨찾기 시간대 등록", description = "회원 즐겨찾기 시간대 등록")
  public ResponseEntity<ApiResponse<CreateFavoriteTimeResponse>> createFavoriteTime(
      @RequestBody @Valid CreateFavoriteTimeRequest request) {

    try {
      CreateFavoriteTimeResponse response = new CreateFavoriteTimeResponse();
      response.setFavoriteTimeId(100L);
      response.setStartTime(LocalTime.of(13, 0));
      response.setEndTime(LocalTime.of(15, 0));

      LocalDateTime dateTime = LocalDateTime.of(2025, 7, 7, 0, 0); // ✅ 변수로 저장
      response.setDateTime(dateTime);                              // 날짜 저장

      DayOfWeek weekday = dateTime.getDayOfWeek(); // 요일 추출
      response.setWeekday(weekday);                // 요일 저장

      response.setCreatedAt(LocalDateTime.now());
      return ResponseEntity.ok(ApiResponse.success("즐겨찾기 시간이 정상적으로 등록되었습니다."));
    } catch (Exception e) {
      if (e instanceof AuthenticationException) {
        return ResponseEntity.status(401)
            .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
      }
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었습니다."));
    }
  }


  // 즐겨찾기 장소 수정
  @Operation(summary = "즐겨찾기 장소 수정", description = "회원 즐겨찾기 장소 수정")
  @PatchMapping("/favorite-location/{memberId}/{favoritePlaceId}")
  public ResponseEntity<ApiResponse<ModifyFavoritePlaceResponse>> modifyFavoritePlace(
      @RequestBody @Valid ModifyFavoritePlaceRequest request) {

    try {
      ModifyFavoritePlaceResponse response = new ModifyFavoritePlaceResponse();
      response.setFavoritePlaceId(200L);
      response.setPlaceName("투썸플레이스 신촌점");
      response.setLatitude(37.5571);
      response.setLongitude(126.9368);
      response.setUpdatedAt(LocalDateTime.now());

      Long favoritePlaceId = request.getFavoritePlaceId();
      if(
          !favoritePlaceId.equals(100L)
      ){
        return ResponseEntity.status(404)
            .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 정보를 찾을 수 없습니다."));
      }

      return ResponseEntity.ok(ApiResponse.success("즐겨찾기 장소가 정상적으로 수정되었습니다."));
    } catch (Exception e) {
      if (e instanceof AuthenticationException) {
        return ResponseEntity.status(401)
            .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
      }
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었습니다."));
    }
  }


  // 즐겨찾기 시간대 수정
  @Operation(summary = "즐겨찾기 시간대 수정", description = "회원 즐겨찾기 시간대 수정")
  @PatchMapping("/favorite-timetable/{memberId}{favoriteTimeId}")
  public ResponseEntity<ApiResponse<ModifyFavoriteTimeResponse>> modifyFavoriteTime(
      @RequestBody @Valid ModifyFavoriteTimeRequest request) {

    try {
      ModifyFavoriteTimeResponse response = new ModifyFavoriteTimeResponse();
      response.setFavoriteTimeId(200L);
      response.setStartTime(LocalTime.of(14, 0));
      response.setEndTime(LocalTime.of(15, 0));

      LocalDateTime dateTime = LocalDateTime.of(2025, 7, 10, 0, 0); // 예: 목요일
      DayOfWeek weekday = dateTime.getDayOfWeek(); // THURSDAY
      response.setWeekday(weekday);  //

      response.setUpdatedAt(LocalDateTime.now());

      Long favoriteTimeId = request.getFavoriteTimeId();
      if(
          !favoriteTimeId.equals(200L)
      ){
        return ResponseEntity.status(404)
            .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 정보를 찾을 수 없습니다."));
      }
      return ResponseEntity.ok(ApiResponse.success("즐겨찾기 시간이 정상적으로 수정되었습니다."));
    } catch (Exception e) {
      if (e instanceof AuthenticationException) {
        return ResponseEntity.status(401)
            .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
      }
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었습니다."));
    }
  }


  // 프로필 수정 (사진 + 이름 수정)
  @Operation(summary = "프로필", description = "회원 프로필 내 이름 및 프로필 캐릭터 수정")
  @PatchMapping("/member-profile/{memberId}")
  public ResponseEntity<ApiResponse<ModifyProfileResponse>> modifyProfile(
      @PathVariable String memberId,
      @RequestBody @Valid ModifyFavoriteTimeRequest request) {

    try {
      ModifyProfileResponse response = new ModifyProfileResponse();
      response.setMemberId("KAKAO_1234");
      response.setProfileImageNumber("7");
      response.setName("ABC");

      if(
          !"KAKAO_1234".equals(memberId)
      ){
        return ResponseEntity.status(404)
            .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 정보를 찾을 수 없습니다."));
      }

      return ResponseEntity.ok(ApiResponse.success("프로필이 정상적으로 수정되었습니다."));
    } catch (Exception e) {
      if (e instanceof AuthenticationException) {
        return ResponseEntity.status(401)
            .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
      }
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었습니다."));
    }
  }

  // 캘린더 연동 변경
  @Operation(summary = "캘린더 연동 설정 변경", description = "회원 프로필 내 캘린더 연동 설정 변경 (ON/OFF)")
  @PatchMapping("/calendar/{memberId}")
  public ResponseEntity<ApiResponse<SetCalendarSyncResponse>> modifyCalendarSync(
      @PathVariable String memberId,
      @RequestBody @Valid SetCalendarSyncRequest request) {

    try {
      SetCalendarSyncResponse response = new SetCalendarSyncResponse();
      response.setEnabled(true);
      response.setLastSyncAt(LocalDateTime.of(2025, 7, 5, 14, 30, 0));

      if(
          !"KAKAO_1234".equals(memberId)
      ){
        return ResponseEntity.status(404)
            .body(ApiResponse.error(ResponseCode.NOT_FOUND, "해당 정보를 찾을 수 없습니다."));
      }

      return ResponseEntity.ok(ApiResponse.success("캘린더 연동이 정상적으로 수정되었습니다."));



    } catch (AuthenticationException e) {
      return ResponseEntity.status(401)
          .body(ApiResponse.error(ResponseCode.UNAUTHORIZED, "인증(로그인)이 되어있지 않습니다."));
    } catch (Exception e) {
      return ResponseEntity.status(400)
          .body(ApiResponse.error(ResponseCode.BAD_REQUEST, "필수값이 누락되었거나 잘못된 요청입니다."));
    }
  }
}