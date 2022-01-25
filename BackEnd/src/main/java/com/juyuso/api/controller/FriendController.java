package com.juyuso.api.controller;

import com.juyuso.api.dto.request.FriendReqDto;
import com.juyuso.api.dto.response.*;
import com.juyuso.api.service.FriendService;
import com.juyuso.db.entity.Friend;
import com.juyuso.db.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(value = "친구 관리 api")
@RestController
@RequestMapping("/api/friend")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @GetMapping()
    @ApiOperation(value = "친구 리스트 조회", notes = "<strong>친구리스트를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendListResDto> findFriendList(@ApiIgnore Authentication authentication) {
        User userDetails = (User) authentication.getDetails();
        List<User> friendList = friendService.friendList(userDetails);
        List<User> RequestList = friendService.RequestList(userDetails);



        return ResponseEntity.ok(FriendListResDto.of(200, "Success", friendList , null,RequestList));
    }


    @PostMapping("/request")
    @ApiOperation(value = "친구 추가 신청", notes = "<strong>친구를 추가신청을 한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendRequestResDto> friendRequest(@ApiIgnore Authentication authentication,@RequestBody FriendReqDto friendReqDto) {
        System.out.println("=================================== friendRequest");
        User userDetails = (User) authentication.getDetails();

        friendService.addRequest(userDetails, friendReqDto);
        return ResponseEntity.ok(FriendRequestResDto.of(200, "Success"));
    }

    @PostMapping("/agree")
    @ApiOperation(value = "친구 신청 동의", notes = "<strong>친구를 추가신청을 한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendRequestResDto> friendAgree(@RequestBody FriendReqDto friendReqDto) {

        friendService.agreeRequest(friendReqDto);

        return ResponseEntity.ok(FriendRequestResDto.of(200, "Success"));
    }

    @GetMapping("/info/{freindId}")
    @ApiOperation(value = "친구 정보 조회", notes = "<strong>친구정보를 조회한다.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "정보조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendResDto> getFriendInfo(@PathVariable Long freindId) {
        User user = friendService.getFriendInfo(freindId);
        return ResponseEntity.ok(FriendResDto.of(200, "Success", user));
    }

    @GetMapping("/{keyword}")
    @ApiOperation(value = "유저 검색", notes = "<strong>친구추가를 위한 전체 유저 검색.</strong>")
    @ApiResponses({
            @ApiResponse(code = 200, message = "리스트조회 성공 "),
            @ApiResponse(code = 400, message = "오류"),
            @ApiResponse(code = 401, message = "권한 없음"),
            @ApiResponse(code = 500, message = " 서버에러")
    })
    public ResponseEntity<FriendResDto> userSearch(@PathVariable String keyword) {
        List<User> userList = friendService.userSearch(keyword);
        for (User u : userList) {
            System.out.println(u.getNickname());
        }
        return ResponseEntity.ok(FriendResDto.of(200, "Success", userList));
    }
}
