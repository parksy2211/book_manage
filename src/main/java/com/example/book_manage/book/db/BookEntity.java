package com.example.book_manage.book.db;

import com.example.book_manage.user.db.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private int publicationYear;
    private String genre;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public BookEntity(String title, String author, int year, String genre, UserEntity user) {
    }
}
