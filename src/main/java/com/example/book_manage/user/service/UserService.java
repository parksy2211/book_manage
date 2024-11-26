package com.example.book_manage.user.service;

import com.example.book_manage.user.db.UserEntity;
import com.example.book_manage.user.db.UserRepository;
import com.example.book_manage.user.model.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    public void registerUser(String username, String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        UserEntity user = new UserEntity(username,email, encodedPassword);
        userRepository.save(user);
    }

    // 로그인
    public void signup(UserDto userDto) {
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // 비밀번호 암호화
        userRepository.save(user);

        // 디버깅용 로그 추가
        System.out.println("Saving user: " + user);
        userRepository.save(user);
    }

    // 이메일로 사용자 찾기
    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    public UserEntity loginUser(String email, String password) {
        Optional<UserEntity> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            UserEntity user = optionalUser.get();
            if (passwordEncoder.matches(password, user.getPassword())) {
                return user;
            }
        }
        throw new RuntimeException("Invalid email or password");
    }
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }
}
