package com.sparta.msa_assign.eureka.client.book.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRequestDto {

    private String title;
    private String author;
    private String publishedAt;
    private String description;
    private String category;
}
