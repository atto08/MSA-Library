package com.sparta.msa_assign.eureka.client.auth.service;

import com.sparta.msa_assign.eureka.client.auth.dto.request.LoginDto;
import com.sparta.msa_assign.eureka.client.auth.dto.request.SignupDto;
import com.sparta.msa_assign.eureka.client.auth.dto.response.TokenDto;
import com.sparta.msa_assign.eureka.client.auth.entity.Member;
import com.sparta.msa_assign.eureka.client.auth.entity.UserRoleEnum;
import com.sparta.msa_assign.eureka.client.auth.repository.MemberRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final SecretKey secretKey;

    @Value("${spring.application.name}")
    private String issuer;

    @Value("${jwt.admin-token}")
    private String ADMIN_TOKEN;

    @Value(("${jwt.access-expiration}"))
    private Long accessExpiration;

    public AuthService(MemberRepository memberRepository, PasswordEncoder passwordEncoder, @Value("${jwt.secret-key}") String secretKey) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
    }

    // 회원 가입
    @Transactional
    public String signupMember(SignupDto signupDto) {
        // 회원 중복 확인,
        if (memberRepository.existsByUsername(signupDto.getUsername())) {
            throw new IllegalArgumentException("사용 중인 유저명 입니다.");
        }
        // 이메일 중복 확인
        if (memberRepository.existsByEmail(signupDto.getEmail())) {
            throw new IllegalArgumentException("사용 중인 이메일 입니다.");
        }
        // 전화번호 중복 확인
        if (memberRepository.existsByPhoneNumber(signupDto.getPhoneNumber())) {
            throw new IllegalArgumentException("사용 중인 전화번호 입니다.");
        }
        // 회원 Role 확인
        UserRoleEnum role = UserRoleEnum.MEMBER;
        if (signupDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signupDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀렸습니다.");
            }
            role = UserRoleEnum.MASTER;
        }
        // 회원 등록
        String encodedPassword = passwordEncoder.encode(signupDto.getPassword());
        Member member = new Member(signupDto, encodedPassword, role);
        memberRepository.save(member);

        return "[" + member.getUsername() + "]님 회원 가입 완료.";
    }

    // 로그인 - 토큰 생성
    public TokenDto createAccessToken(String username) {
        Member memberId = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("등록되지 않은 유저명입니다."));

        return memberRepository.findByUsername(memberId.getUsername())
                // user.getUserId() 로 JWT 토큰을 생성합니다. - Spring 심화 강의 응용
                .map(member -> TokenDto.of(Jwts.builder()
                                .claim("member_id", member.getUsername())
                                .claim("role", member.getRole())
                                .issuer(issuer)
                                .issuedAt(new Date(System.currentTimeMillis()))
                                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                                .signWith(secretKey)
                                .compact())
                        //유저가 존재하지 않는다면 Exception 을 발생 시킵니다.
                ).orElseThrow(() -> new IllegalArgumentException("Reject createAccessToken: 존재하지 않는 유저입니다."));
    }

    // 로그인
    @Transactional
    public TokenDto login(LoginDto loginDto) {
        // 유저 존재 확인
        Member member = memberRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        // 비밀번호 일치 확인
        if (!passwordEncoder.matches(loginDto.getPassword(), member.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        // 토큰 생성
        return createAccessToken(loginDto.getUsername());
    }

    // 회원 존재 여부 검증 비즈니스 로직
    public Boolean verifyMember(final String memberId) {
        // userId 로 User 를 조회 후 isPresent() 로 존재유무를 리턴함
        return memberRepository.findByUsername(memberId).isPresent();
    }
}
