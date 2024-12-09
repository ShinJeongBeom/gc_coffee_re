package com.example.gc_coffee.controller;

import com.example.gc_coffee.model.dto.OrderDTO;
import com.example.gc_coffee.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RequiredArgsConstructor//@Autowired
@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    //주문자 정보를 저장하는 로직
    @PostMapping("/add")
    public void addOrder(@RequestBody OrderDTO dto ,
                         @RequestParam(name = "quantity") int quantity,
                         @RequestParam(name = "coffeeId") int coffeeId) throws SQLException{
        orderService.insertUserOrder(dto,quantity,coffeeId);
    }
    //사용자 이메일을 검색하여 현재 상태에 따라 true=1면 삭제(전날 14시부터 다음날 14시까지 주문의 상태를 변화
    @DeleteMapping("/delete/{email}")
    public void deleteOldOrders(@PathVariable String email) {
        orderService.deleteOldOrders(email);
    }
    //전날14시 - 당일14시 이전의 주문은 배송 시작 처리
    @PutMapping("/update/{orderId}")
    public void updateStatus(@PathVariable long orderId) {
        orderService.updateOrder(orderId);
    }

}
