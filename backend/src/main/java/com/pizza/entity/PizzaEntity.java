package com.pizza.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity(name = "Pizza")
public class PizzaEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "Pizza_Topping",
            joinColumns = @JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id")
    )

    private Set<ToppingEntity> toppings;


    public PizzaEntity(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public PizzaEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<ToppingEntity> getToppings() {
        return toppings;
    }

    public void setToppings(Set<ToppingEntity> toppings) {
        this.toppings = toppings;
    }
}
