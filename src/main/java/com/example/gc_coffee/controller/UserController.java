package com.example.gc_coffee.controller;

import com.example.gc_coffee.model.dto.UserDTO;
import com.example.gc_coffee.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor //@Autowired
@RequestMapping
public class UserController {
    private final UserService userService;
    //사용자의 이메일을 검색하여 주문 목록을 불러옴
    @GetMapping("/find")
    public List<UserDTO> findUserByEmail(@RequestParam String email) {
        return userService.getUserByEmail(email); //호출 및 반환

    }
}
