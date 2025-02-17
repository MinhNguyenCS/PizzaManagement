package com.pizza.service.imp;

import com.pizza.entity.ToppingEntity;

import java.util.List;

public interface ToppingServiceImp {
    List<ToppingEntity> getAllTopping();
    ToppingEntity getTopping(long id);
    void addingTopping(String name);
    void updateTopping(long id, String name);
    void deleteTopping(long id);
}
