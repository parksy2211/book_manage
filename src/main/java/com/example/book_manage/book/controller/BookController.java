package com.example.book_manage.book.controller;

import com.example.book_manage.book.db.BookEntity;
import com.example.book_manage.book.model.BookDto;
import com.example.book_manage.book.service.BookService;
import com.example.book_manage.user.db.UserEntity;
import com.example.book_manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;  // UserService 주입

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody BookDto bookDto, @RequestParam Long userId) {
        UserEntity user = userService.findById(userId) // UserService에서 사용자 정보 가져오기
                .orElseThrow(() -> new RuntimeException("User not found"));

        bookService.addBook(bookDto.getTitle(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getGenre(), user);
        return ResponseEntity.ok("Book added successfully");
    }


    @GetMapping("/list")
    public ResponseEntity<List<BookEntity>> getBooks(@RequestParam Long userId) {
        UserEntity user = userService.findById(userId) // userService 인스턴스를 통해 호출
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<BookEntity> books = bookService.getBooksByUser(user);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto, @RequestParam Long userId) {
        UserEntity user = userService.findById(userId) // UserService에서 사용자 정보 가져오기
                .orElseThrow(() -> new RuntimeException("User not found"));

        bookService.updateBook(id, bookDto.getTitle(), bookDto.getAuthor(), bookDto.getYear(), bookDto.getGenre(), user);
        return ResponseEntity.ok("Book updated successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id, @RequestParam Long userId) {
        UserEntity user = userService.findById(userId) // UserService에서 사용자 정보 가져오기
                .orElseThrow(() -> new RuntimeException("User not found"));

        bookService.deleteBook(id, user);
        return ResponseEntity.ok("Book deleted successfully");
    }

}
