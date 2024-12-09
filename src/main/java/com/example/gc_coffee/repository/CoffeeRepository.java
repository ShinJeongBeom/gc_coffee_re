package com.example.gc_coffee.repository;

import com.example.gc_coffee.model.dto.CoffeeDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CoffeeRepository {
    //기능만 정의

    List<CoffeeDTO> selectAllCoffee();

    int getCoffeePriceById(long coffeeId);
}
