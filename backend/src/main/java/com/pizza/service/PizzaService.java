package com.pizza.service;

import com.pizza.DTO.PizzaRequestDTO;
import com.pizza.entity.PizzaEntity;
import com.pizza.entity.ToppingEntity;
import com.pizza.exception.PizzaAlreadyExistsException;
import com.pizza.repository.PizzaRepository;
import com.pizza.repository.ToppingRepository;
import com.pizza.service.imp.PizzaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PizzaService implements PizzaServiceImp {

    @Autowired
    PizzaRepository pizzaRepository;

    @Autowired
    ToppingRepository toppingRepository;


    @Override
    public List<PizzaEntity> getAllPizza() {
        return pizzaRepository.findAll();
    }

    @Override
    public PizzaEntity getPizza(long id) {
        return pizzaRepository.findById(id);
    }

    @Override
    public void addPizza(PizzaRequestDTO pizzaRequestDTO) {
        if (pizzaRepository.findByName(pizzaRequestDTO.getName()).isPresent()) {
            throw new PizzaAlreadyExistsException("The pizza already exists.");
        }
        Set<ToppingEntity> toppings = pizzaRequestDTO.getToppingIds().stream()
                .map(id -> toppingRepository.findById(id))
                .collect(Collectors.toSet());
        PizzaEntity pizza = new PizzaEntity();
        pizza.setName(pizzaRequestDTO.getName());
        pizza.setToppings(toppings);
        pizzaRepository.save(pizza);
    }

    @Override
    public void updatePizza(long id, String newName, Set<Long> toppingIds) {
        PizzaEntity pizza = pizzaRepository.findById(id);
        if (!pizza.getName().equals(newName) && pizzaRepository.findByName(newName).isPresent()) {
            throw new PizzaAlreadyExistsException("A pizza with this name already exists.");
        }
        pizza.setName(newName);
        Set<ToppingEntity> toppings = toppingRepository.findAllByIdIn(toppingIds).stream().collect(java.util.stream.Collectors.toSet());
        pizza.setToppings(toppings);
        pizzaRepository.save(pizza);
    }

    @Override
    public void deletePizza(long id) {
        PizzaEntity pizza = pizzaRepository.findById(id);
        try {
            pizzaRepository.delete(pizza);
        } catch(Exception e) {
            throw new RuntimeException("Delete Pizza Error");
        }
    }
}
