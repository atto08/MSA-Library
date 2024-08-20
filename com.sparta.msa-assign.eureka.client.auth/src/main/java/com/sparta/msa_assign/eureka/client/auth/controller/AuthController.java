package com.sparta.msa_assign.eureka.client.auth.controller;

import com.sparta.msa_assign.eureka.client.auth.dto.request.LoginDto;
import com.sparta.msa_assign.eureka.client.auth.dto.request.UpdateProfileDto;
import com.sparta.msa_assign.eureka.client.auth.dto.response.MemberResponseDto;
import com.sparta.msa_assign.eureka.client.auth.dto.response.TokenDto;
import com.sparta.msa_assign.eureka.client.auth.dto.request.SignupDto;
import com.sparta.msa_assign.eureka.client.auth.entity.Member;
import com.sparta.msa_assign.eureka.client.auth.entity.UserRoleEnum;
import com.sparta.msa_assign.eureka.client.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Value("${server.port}")
    private String port;

    // 회원 가입
    @PostMapping("/register")
    public ResponseEntity<String> signupMember(SignupDto signupDto) {

        String message = authService.signupMember(signupDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/verify")
    public ResponseEntity<Boolean> verifyMember(final @RequestParam(value = "member_id") String memberId) {
        Boolean response = authService.verifyMember(memberId);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(response, headers, HttpStatus.OK);
    }

    // 로그인 & JWT 발급
    @PostMapping("/login")
    public ResponseEntity<String> login(LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        Member member = authService.getMember(loginDto.getUsername());

        HttpHeaders headers = addPortNumber();
        headers.set("Authorization", "Bearer " + tokenDto.getAccessToken());
        headers.set("X-User-Role", member.getRole().toString()); // role 정보 추가
        headers.set("X-User-Id", member.getUsername()); // id 정보 추가

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

    // 회원 목록 조회 - 관리자만 가능
    @GetMapping
    public ResponseEntity<List<MemberResponseDto>> getMemberInfoList(@RequestHeader(value = "X-User-Role") String role) {

        if (validateRole(role)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        List<MemberResponseDto> getMemberInfoList = authService.getMemberInfoList(role);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(getMemberInfoList, headers, HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDto> getMemberInfo(@RequestHeader("X-User-Id") String memberId,
                                                           @PathVariable("id") String id) {
        if (!memberId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        MemberResponseDto memberResponseDto = authService.getMemberInfo(id);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(memberResponseDto, headers, HttpStatus.OK);
    }

    // 회원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDto> updateMemberInfo(@RequestHeader("X-User-Id") String memberId,
                                                              @PathVariable("id") String id,
                                                              UpdateProfileDto updateProfileDto) {
        if (!memberId.equals(id)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        MemberResponseDto memberResponseDto = authService.updateMemberInfo(id, updateProfileDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(memberResponseDto, headers, HttpStatus.OK);
    }

    // 회원 탈퇴
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMember(@RequestHeader("X-User-Role") String role,
                                               @PathVariable("id") String id) {
        if (validateRole(role)) { // 회원
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String message = authService.deleteMember(id);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // Http 헤더에 Port 번호 표기
    private HttpHeaders addPortNumber() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("port", port);
        return httpHeaders;
    }

    // 관리자 권한 체크를 위한 메소드
    private Boolean validateRole(String role) {
        return !UserRoleEnum.valueOf(role).equals(UserRoleEnum.MASTER);
    }
}
