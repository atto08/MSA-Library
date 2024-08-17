package com.sparta.msa_assign.eureka.client.book.repository;

import com.sparta.msa_assign.eureka.client.book.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {

    Boolean existsByTitleAndAuthorAndPublishedAt(String title, String author, String publishedAt);
}
