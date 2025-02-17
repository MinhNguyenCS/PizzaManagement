package com.pizza.DTO;

import java.util.Set;

public class PizzaRequestDTO {
    private String name;
    private Set<Long> toppingIds; // List of topping IDs

    // Constructors
    public PizzaRequestDTO() {}

    public PizzaRequestDTO(String name, Set<Long> toppingIds) {
        this.name = name;
        this.toppingIds = toppingIds;
    }

    // Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Long> getToppingIds() {
        return toppingIds;
    }

    public void setToppingIds(Set<Long> toppingIds) {
        this.toppingIds = toppingIds;
    }
}
