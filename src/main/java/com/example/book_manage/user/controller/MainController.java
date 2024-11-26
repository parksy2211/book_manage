package com.example.book_manage.user.controller;


import com.example.book_manage.user.db.UserEntity;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Controller
public class MainController {

    @GetMapping("/register")
    public String register() {
        return "signup";
    }
    @GetMapping("/")
    public String home() {
        return "login";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/mybooks")
    public String myBooks(HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
            System.out.println("인증되지 않은 사용자입니다.");
            return "redirect:/login";
        }

        System.out.println("인증된 사용자: " + authentication.getPrincipal());
        System.out.println("세션 ID: " + session.getId());
        return "mybooks";
    }

}
