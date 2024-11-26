package com.example.book_manage.user.service;

import com.example.book_manage.user.db.UserEntity;
import com.example.book_manage.user.db.UserRepository;
import com.example.book_manage.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }



    // 회원가입 메서드 (UserDto를 인자로 받음)
    public void signup(UserDto userDto) {
        // username, email, password가 null인지 확인
        if (userDto.getUsername() == null || userDto.getEmail() == null || userDto.getPassword() == null) {
            throw new IllegalArgumentException("Username, email, and password must not be null");
        }

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());
        UserEntity user = new UserEntity(userDto.getUsername(), userDto.getEmail(), encodedPassword);

        // 디버깅 로그 추가
        System.out.println("Attempting to save user: " + user);

        userRepository.save(user);
    }

    // 이메일로 사용자 찾기
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // 로그인 메서드
    public UserEntity loginUser(String email, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                System.out.println("로그인 성공: " + user.getEmail());
                return user;
            } else {
                System.out.println("비밀번호 불일치: " + email);
            }
        } else {
            System.out.println("이메일을 찾을 수 없음: " + email);
        }
        throw new RuntimeException("Invalid email or password");
    }


    // ID로 사용자 찾기
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}
