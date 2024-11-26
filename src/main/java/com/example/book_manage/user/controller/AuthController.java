package com.example.book_manage.user.controller;

import com.example.book_manage.user.db.UserEntity;
import com.example.book_manage.user.model.UserDto;
import com.example.book_manage.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;
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



    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDto userDto, HttpSession session) {
        try {
            UserEntity user = userService.loginUser(userDto.getEmail(), userDto.getPassword());
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 세션에 사용자 저장
            session.setAttribute("user", user);

            // JSON 응답
            return ResponseEntity.ok(Map.of(
                    "id", user.getId(),
                    "email", user.getEmail(),
                    "username", user.getUsername(),
                    "message", "Login successful"
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of(
                    "message", "Invalid email or password"
            ));
        }
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
        System.out.println("User successfully registered: " + userDto.getUsername());
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

}
