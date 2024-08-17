package com.sparta.msa_assign.eureka.client.review.entity;

import com.sparta.msa_assign.eureka.client.review.dto.ReviewRequestDto;
import com.sparta.msa_assign.eureka.client.review.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reviews")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String memberId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Integer rating;

    public Review(ReviewRequestDto reviewRequestDto) {
        this.memberId = reviewRequestDto.getMemberId();
        this.bookId = reviewRequestDto.getBookId();
        this.content = reviewRequestDto.getContent();
        this.rating = reviewRequestDto.getRating();
    }

    public void updateReview(String content, Integer rating) {
        this.content = content;
        this.rating = rating;
    }
}
