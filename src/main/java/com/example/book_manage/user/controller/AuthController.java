package com.example.book_manage.user.controller;

import com.example.book_manage.user.db.UserEntity;
import com.example.book_manage.user.model.UserDto;
import com.example.book_manage.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {
    @Autowired
    private UserService userService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(UserService userService, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        userService.registerUser(userDto.getUsername(),userDto.getEmail(), userDto.getPassword());
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        UserEntity user = userService.loginUser(userDto.getEmail(), userDto.getPassword());
        // 세션 설정
        return ResponseEntity.ok("Login successful");
    }




    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody UserDto userDto) {
        // 중복 이메일 확인
        Optional<UserEntity> existingUser = userService.findByEmail(userDto.getEmail());
        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().body("이미 사용 중인 이메일입니다.");
        }

        // 회원가입 처리
        userService.signup(userDto);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}
