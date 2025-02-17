package com.pizza.repository;

import com.pizza.DTO.PizzaRequestDTO;
import com.pizza.entity.PizzaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PizzaRepository extends JpaRepository<PizzaEntity, Integer> {
    PizzaEntity findById(long id);
    Optional<PizzaEntity> findByName(String name);
}
