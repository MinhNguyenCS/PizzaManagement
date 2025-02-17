package com.pizza.repository;

import com.pizza.entity.ToppingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ToppingRepository  extends JpaRepository<ToppingEntity, Integer> {
    ToppingEntity findById(long id);
    Optional<ToppingEntity> findByName(String name);
    Set<ToppingEntity> findAllByIdIn(Set<Long> ids);
}
