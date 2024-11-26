package com.example.book_manage.book.service;

import com.example.book_manage.book.db.BookEntity;
import com.example.book_manage.book.db.BookRepository;
import com.example.book_manage.user.db.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public void addBook(String title, String author, int year, String genre, UserEntity user) {
        BookEntity book = new BookEntity(title, author, year, genre, user);
        bookRepository.save(book);
    }

    public List<BookEntity> getBooksByUser(UserEntity user) {
        return bookRepository.findByUser(user);
    }

    public void updateBook(Long bookId, String title, String author, int year, String genre, UserEntity user) {
        List<BookEntity> books = bookRepository.findByUser(user);

        // 책을 찾기
        BookEntity book = books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 책 정보 업데이트
        book.setTitle(title);
        book.setAuthor(author);
        book.setPublicationYear(year);
        book.setGenre(genre);
        bookRepository.save(book);
    }

    public void deleteBook(Long bookId, UserEntity user) {
        List<BookEntity> books = bookRepository.findByUser(user);

        // 책을 찾기
        BookEntity book = books.stream()
                .filter(b -> b.getId().equals(bookId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // 책 삭제
        bookRepository.delete(book);
    }


}
