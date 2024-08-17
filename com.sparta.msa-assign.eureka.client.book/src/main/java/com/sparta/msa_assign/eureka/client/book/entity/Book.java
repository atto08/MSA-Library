package com.sparta.msa_assign.eureka.client.book.entity;

import com.sparta.msa_assign.eureka.client.book.dto.BookRequestDto;
import com.sparta.msa_assign.eureka.client.book.util.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String publishedAt;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String category;

    public Book(BookRequestDto bookRequestDto) {
        this.title = bookRequestDto.getTitle();
        this.author = bookRequestDto.getAuthor();
        this.publishedAt = bookRequestDto.getPublishedAt();
        this.description = bookRequestDto.getDescription();
        this.category = bookRequestDto.getCategory();
    }

    public void updateBookInfo(BookRequestDto bookRequestDto){
        this.title = bookRequestDto.getTitle();
        this.author = bookRequestDto.getAuthor();
        this.publishedAt = bookRequestDto.getPublishedAt();
        this.description = bookRequestDto.getDescription();
        this.category = bookRequestDto.getCategory();
    }
}
