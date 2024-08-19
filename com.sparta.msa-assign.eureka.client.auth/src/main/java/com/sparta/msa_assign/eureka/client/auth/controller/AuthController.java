package com.sparta.msa_assign.eureka.client.auth.controller;

import com.sparta.msa_assign.eureka.client.auth.dto.request.LoginDto;
import com.sparta.msa_assign.eureka.client.auth.dto.response.TokenDto;
import com.sparta.msa_assign.eureka.client.auth.dto.request.SignupDto;
import com.sparta.msa_assign.eureka.client.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<String> login(LoginDto loginDto){
        TokenDto tokenDto = authService.login(loginDto);
        HttpHeaders headers = addPortNumber();
        headers.set("Authorization", "Bearer " + tokenDto.getAccessToken());

        return new ResponseEntity<>("로그인 성공", headers, HttpStatus.OK);
    }

    // 회원 목록 조회

    // 회원 정보 조회

    // 회원 정보 수정

    // 회원 탈퇴

    // Http 헤더에 Port 번호 표기
    private HttpHeaders addPortNumber() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("port", port);
        return httpHeaders;
    }
}
