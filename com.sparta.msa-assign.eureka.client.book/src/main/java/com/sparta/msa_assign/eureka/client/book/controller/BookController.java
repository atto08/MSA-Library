package com.sparta.msa_assign.eureka.client.book.controller;

import com.sparta.msa_assign.eureka.client.book.dto.BookRequestDto;
import com.sparta.msa_assign.eureka.client.book.dto.BookResponseDto;
import com.sparta.msa_assign.eureka.client.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Value("${server.port}")
    private String port;

    // 도서 등록 API
    @PostMapping
    public ResponseEntity<String> createBook(BookRequestDto bookRequestDto) {
        String message = bookService.createBook(bookRequestDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    // 도서 목록 조회 API
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getBookInfoList() {
        List<BookResponseDto> bookInfoList = bookService.getBookInfoList();
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(bookInfoList, headers, HttpStatus.OK);
    }

    // 특정 도서 정보 조회
    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookInfo(@PathVariable("id") Long id) {
        BookResponseDto bookInfo = bookService.getBookInfo(id);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(bookInfo, headers, HttpStatus.OK);
    }

    // 도서 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDto> updateBookInfo(@PathVariable("id") Long id,
                                                          BookRequestDto bookRequestDto) {
        BookResponseDto updateBookInfo = bookService.updateBookInfo(id, bookRequestDto);
        HttpHeaders headers = addPortNumber();

        return new ResponseEntity<>(updateBookInfo, headers, HttpStatus.OK);
    }

    // 도서 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBookInfo(@PathVariable("id") Long id) {
        String message = bookService.deleteBookInfo(id);
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
