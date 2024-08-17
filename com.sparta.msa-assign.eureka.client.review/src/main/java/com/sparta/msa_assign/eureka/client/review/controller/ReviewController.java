package com.sparta.msa_assign.eureka.client.review.controller;

import com.sparta.msa_assign.eureka.client.review.dto.ReviewRequestDto;
import com.sparta.msa_assign.eureka.client.review.dto.ReviewResponseDto;
import com.sparta.msa_assign.eureka.client.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @Value("${server.port}")
    private String port;

    // 리뷰 작성
    @PostMapping
    public ResponseEntity<String> createReview(ReviewRequestDto reviewRequestDto) {
        String message = reviewService.createReview(reviewRequestDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 특정 도서의 리뷰 목록 조회
    @GetMapping("/book/{book_id}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewListAboutBook(@PathVariable("book_id") Long bookId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewListAboutBook(bookId);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(reviewList, headers, HttpStatus.OK);
    }

    // 특정 사용자의 리뷰 조회
    @GetMapping("/member/{member_id}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewListAboutMember(@PathVariable("member_id") String memberId) {
        List<ReviewResponseDto> reviewList = reviewService.getReviewListAboutMember(memberId);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(reviewList, headers, HttpStatus.OK);
    }

    // 리뷰 수정
    @PutMapping("/{id}")
    public ResponseEntity<ReviewResponseDto> updateReview(@PathVariable("id") Long id,
                                                          ReviewRequestDto reviewRequestDto){
        ReviewResponseDto review = reviewService.updateReview(id, reviewRequestDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(review, headers, HttpStatus.OK);
    }

    // 리뷰 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReview(@PathVariable("id") Long id){
        String message = reviewService.deleteReview(id);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // Http 헤더에 Port 번호 표기
    private HttpHeaders addPortNumber() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("port", port);
        return httpHeaders;
    }
}
