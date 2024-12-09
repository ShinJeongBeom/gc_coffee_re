package com.example.gc_coffee.service;

import com.example.gc_coffee.model.dto.OrderDTO;
import com.example.gc_coffee.repository.CoffeeRepository;
import com.example.gc_coffee.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CoffeeRepository coffeeRepository;

    @Transactional
    public void insertUserOrder(OrderDTO dto, int quantity, long coffeeId) {
        //order / orderDetail 테이블이나눠저 있음으로 나눴다./ insert Query 를 두번 때려서
        //커피아이디 가지고 커피 테이블에서 커피 가격을 가져온다
        int coffeePrice = coffeeRepository.getCoffeePriceById(coffeeId);
        //가져온값을 quintity * coffeeprice = totalprice
        int totalPrice = coffeePrice * quantity;

        orderRepository.insertOrder(dto,totalPrice);
        // 이 시점에서 dto.orderId 가 세팅됨
        orderRepository.insertOrderDetail(dto, quantity, coffeeId);
    }

    @Transactional
    public void deleteOldOrders(String email) {
        orderRepository.deleteOrdersByEmail(email);
    }

    public void updateOrder(long orderId) {
        orderRepository.updateOrderStatus(orderId);
    }
}
