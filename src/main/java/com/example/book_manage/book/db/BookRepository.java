package com.example.book_manage.book.db;

import com.example.book_manage.user.db.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
    List<BookEntity> findByUser(UserEntity user);

}
