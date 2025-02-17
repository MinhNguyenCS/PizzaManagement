package com.pizza.service.imp;

import com.pizza.DTO.PizzaRequestDTO;
import com.pizza.entity.PizzaEntity;

import java.util.List;
import java.util.Set;

public interface PizzaServiceImp {
    List<PizzaEntity> getAllPizza();
    PizzaEntity getPizza(long id);
    void addPizza(PizzaRequestDTO pizzaRequestDTO);
    void updatePizza(long id, String name, Set<Long> toppingIds);
    void deletePizza(long id);
}
