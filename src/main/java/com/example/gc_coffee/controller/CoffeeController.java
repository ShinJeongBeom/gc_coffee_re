package com.example.gc_coffee.controller;

import com.example.gc_coffee.model.dto.CoffeeDTO;
import com.example.gc_coffee.service.CoffeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController //jason 형태로 데이터 객체를 반환
@RequiredArgsConstructor //@Autowired
@RequestMapping("/coffee")
public class CoffeeController {
    //커피 목록을 보여줌
    private final CoffeeService coffeeService; //생성자 주입으로 받는다.

    //목록에 저장되어 있는 커피목록 불러오기
    @GetMapping("/list")
    public List<CoffeeDTO> listCoffee() throws SQLException {
        List<CoffeeDTO> list = coffeeService.getAllCoffeeList();
        System.out.println("listCoffee: " + list);
        return list;
    }
}
