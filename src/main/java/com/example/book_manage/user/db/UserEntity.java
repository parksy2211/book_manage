package com.example.book_manage.user.db;

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
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email; // 이메일은 유니크해야 함

    @Column(nullable = false)
    private String password; // 암호화된 비밀번호

    @Column(nullable = false)
    private String username; // username 필드는 null 허용하지 않음

    // username, email, password를 인자로 받는 생성자 추가
    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
