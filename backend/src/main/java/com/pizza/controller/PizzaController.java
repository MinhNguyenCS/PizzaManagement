package com.pizza.controller;

import com.pizza.DTO.PizzaRequestDTO;
import com.pizza.response.BaseResponse;
import com.pizza.service.imp.PizzaServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/chef")
public class PizzaController {
    @Autowired
    PizzaServiceImp pizzaServiceImp;

    @GetMapping("/{id}")
    public ResponseEntity<?> getPizza(@PathVariable long id) {
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(pizzaServiceImp.getPizza(id));
        response.setMessage("Getting Pizza Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<?> getAllPizza() {
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setData(pizzaServiceImp.getAllPizza());
        response.setMessage("Getting Pizza Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/adding")
    public ResponseEntity<?> addPizza(@RequestBody PizzaRequestDTO pizzaRequestDTO) {
        BaseResponse response = new BaseResponse();
        pizzaServiceImp.addPizza(pizzaRequestDTO);
        response.setSuccess(true);
        response.setMessage("Adding Pizza Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<?> updatePizza(@PathVariable long id, @RequestBody PizzaRequestDTO pizzaUpdate) {
        BaseResponse response = new BaseResponse();
        pizzaServiceImp.updatePizza(id, pizzaUpdate.getName(), pizzaUpdate.getToppingIds());
        response.setSuccess(true);
        response.setMessage("Updating Pizza Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deletePizza(@PathVariable long id) {
        pizzaServiceImp.deletePizza(id);
        BaseResponse response = new BaseResponse();
        response.setSuccess(true);
        response.setMessage("Deleting Pizza Successfully");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
