package com.sparta.msa_assign.eureka.client.review.service;

import com.sparta.msa_assign.eureka.client.review.dto.ReviewRequestDto;
import com.sparta.msa_assign.eureka.client.review.dto.ReviewResponseDto;
import com.sparta.msa_assign.eureka.client.review.entity.Review;
import com.sparta.msa_assign.eureka.client.review.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // 리뷰 작성
    public String createReview(ReviewRequestDto reviewRequestDto) {

        Review review = new Review(reviewRequestDto);
        reviewRepository.save(review);

        return "리뷰 작성 완료";
    }

    // 특정 도서의 리뷰 목록 조회
    public List<ReviewResponseDto> getReviewListAboutBook(Long bookId) {
        return reviewRepository.findByBookId(bookId).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 특정 사용자의 리뷰 목록 조회
    public List<ReviewResponseDto> getReviewListAboutMember(String memberId) {
        return reviewRepository.findByMemberId(memberId).stream()
                .map(ReviewResponseDto::new)
                .collect(Collectors.toList());
    }

    // 리뷰 수정
    @Transactional
    public ReviewResponseDto updateReview(Long id, ReviewRequestDto reviewRequestDto) {
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 리뷰입니다.")
        );
        review.updateReview(reviewRequestDto.getContent(), reviewRequestDto.getRating());
        return new ReviewResponseDto(review);
    }

    // 리뷰 삭제
    @Transactional
    public String deleteReview(Long id){
        Review review = reviewRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 리뷰입니다.")
        );

        reviewRepository.delete(review);
        return "리뷰 삭제 완료.";
    }
}
