package com.pizza.service;

import com.pizza.entity.ToppingEntity;
import com.pizza.exception.ToppingAlreadyExistsException;
import com.pizza.repository.ToppingRepository;
import com.pizza.service.imp.ToppingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ToppingService implements ToppingServiceImp {

    @Autowired
    ToppingRepository toppingRepository;

    @Override
    public List<ToppingEntity> getAllTopping() {
        List<ToppingEntity> toppingList = toppingRepository.findAll();
        return toppingList;
    }

    @Override
    public ToppingEntity getTopping(long id) {
        ToppingEntity topping = toppingRepository.findById(id);
        return topping;
    }

    @Override
    public void addingTopping(String name) {
        Optional<ToppingEntity> existingTopping = toppingRepository.findByName(name);
        if (existingTopping.isPresent()) {
            throw new ToppingAlreadyExistsException("The topping already exists.");
        }
         ToppingEntity topping = new ToppingEntity();
         topping.setName(name);
         toppingRepository.save(topping);
    }

    @Override
    public void updateTopping(long id, String newName) {
         ToppingEntity topping = toppingRepository.findById(id);
        Optional<ToppingEntity> existingTopping = toppingRepository.findByName(newName);
        if (!topping.getName().equals(newName) && existingTopping.isPresent()) {
            throw new ToppingAlreadyExistsException("The topping already exists.");
        }
         topping.setName(newName);
        toppingRepository.save(topping);
    }

    @Override
    public void deleteTopping(long id) {
        ToppingEntity topping = toppingRepository.findById(id);
        try {
            toppingRepository.delete(topping);
        } catch(Exception e) {
            throw new RuntimeException("Delete Topping Error");
        }
    }
}
