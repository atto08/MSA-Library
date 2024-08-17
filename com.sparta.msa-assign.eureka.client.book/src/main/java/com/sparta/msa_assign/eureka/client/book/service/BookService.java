package com.sparta.msa_assign.eureka.client.book.service;

import com.sparta.msa_assign.eureka.client.book.dto.BookRequestDto;
import com.sparta.msa_assign.eureka.client.book.dto.BookResponseDto;
import com.sparta.msa_assign.eureka.client.book.entity.Book;
import com.sparta.msa_assign.eureka.client.book.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    // 도서 등록
    @Transactional
    public String createBook(BookRequestDto bookRequestDto) {

        // 관리자가 아닌 사람이 접근했을 경우

        Book book = new Book(bookRequestDto); // 입력받은 도서

        // 이미 등록된 도서일 경우
        if (bookRepository.existsByTitleAndAuthorAndPublishedAt(book.getTitle(), book.getAuthor(), book.getPublishedAt())) {
            // 이미 등록된 도서 입니다.
        }

        // 등록되지 않은 도서일 경우
        bookRepository.save(book);

        return book.getTitle() + "도서를 등록했습니다.";
    }

    // 도서 목록 조회
    public List<BookResponseDto> getBookInfoList() {
        List<Book> bookList = bookRepository.findAll();
        List<BookResponseDto> bookResponseDtoList = new ArrayList<>();
        for (Book book : bookList) {
            bookResponseDtoList.add(new BookResponseDto(book));
        }
        return bookResponseDtoList;
    }

    // 특정 도서 조회
    public BookResponseDto getBookInfo(Long id) {
        Book book = findBook(id);

        return new BookResponseDto(book);
    }

    // 도서 정보 수정
    @Transactional
    public BookResponseDto updateBookInfo(Long id, BookRequestDto bookRequestDto) {
        Book book = findBook(id);

        book.updateBookInfo(bookRequestDto);

        return new BookResponseDto(book);
    }


    // 도서 삭제
    @Transactional
    public String deleteBookInfo(Long id) {
        Book book = findBook(id);

        bookRepository.deleteById(book.getId());

        return book.getTitle() + " 도서 삭제 완료.";
    }

    private Book findBook(Long id) {

        return bookRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("등록되지 않은 도서입니다."));
    }
}
