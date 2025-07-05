package com.grepp.spring.app.controller.api.mypage;


import com.grepp.spring.app.controller.api.event.payload.EventCreateRequest;
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
import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.Map;
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
  public ResponseEntity<ApiResponse<CreateFavoritePlaceResponse>> createFavoriteLocation(
      @RequestBody @Valid CreateFavoritePlaceRequest request) {

    try {
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
  public ResponseEntity<ApiResponse<CreateFavoriteTimeResponse>> createFavoriteTime(
      @RequestBody @Valid CreateFavoriteTimeRequest request) {

    try {
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
  @PatchMapping("/favorite-location/{memberId}")
  public ResponseEntity<ApiResponse<ModifyFavoritePlaceResponse>> modifyFavoritePlace(
      @RequestBody @Valid ModifyFavoritePlaceRequest request) {

    try {
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
  @PatchMapping("/favorite-timetable/{memberId}")
  public ResponseEntity<ApiResponse<ModifyFavoriteTimeResponse>> modifyFavoriteTime(
      @RequestBody @Valid ModifyFavoriteTimeRequest request) {

    try {
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


  // 프로필 수정 (사진만)
  @PatchMapping("/member-profile/{memberId}")
  public ResponseEntity<ApiResponse<ModifyProfileResponse>> modifyProfile(
      @PathVariable Long memberId,
      @RequestBody @Valid ModifyFavoriteTimeRequest request) {

    try {
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
  @PatchMapping("/calendar/{memberId}")
  public ResponseEntity<ApiResponse<SetCalendarSyncResponse>> modifyCalendarSync(
      @PathVariable Long memberId,
      @RequestBody @Valid SetCalendarSyncRequest request) {

    try {
      SetCalendarSyncResponse response = new SetCalendarSyncResponse(
          request.isEnabled(),
          LocalDateTime.now(),
          LocalDateTime.now()
      );
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