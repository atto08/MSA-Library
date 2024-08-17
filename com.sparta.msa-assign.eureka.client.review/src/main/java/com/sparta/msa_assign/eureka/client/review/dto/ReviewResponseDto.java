package com.sparta.msa_assign.eureka.client.review.dto;

import com.sparta.msa_assign.eureka.client.review.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewResponseDto {
    private Long id;
    private String memberId;
    private Long bookId;
    private String content;
    private Integer rating;

    public ReviewResponseDto(Review review) {
        this.id = review.getId();
        this.memberId = review.getMemberId();
        this.bookId = review.getBookId();
        this.content = review.getContent();
        this.rating = review.getRating();
    }
}
