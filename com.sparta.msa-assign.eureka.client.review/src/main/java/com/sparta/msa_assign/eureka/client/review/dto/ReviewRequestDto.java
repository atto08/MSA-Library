package com.sparta.msa_assign.eureka.client.review.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewRequestDto {
    private String memberId;
    private Long bookId;
    private String content;
    private Integer rating;
}
