package com.sparta.msa_assign.eureka.client.review.repository;

import com.sparta.msa_assign.eureka.client.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByMemberId(String memberId);

    List<Review> findByBookId(Long bookId);
}
