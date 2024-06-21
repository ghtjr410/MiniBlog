package com.housing.back.controller;




import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/aaa")
public class UserController {

    @GetMapping("/aaa")
    public String testEndpoint() {
        return "Access granted to /api/v1/aaa";
    }

    @GetMapping("/aab") //
    public void logout(HttpServletResponse response) throws IOException {
        // 클라이언트를 특정 URL로 리디렉션
        response.sendRedirect("http://localhost:3000/");
    }
}
