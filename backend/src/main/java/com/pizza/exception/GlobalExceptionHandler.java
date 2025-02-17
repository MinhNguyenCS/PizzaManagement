package com.pizza.exception;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.pizza.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PizzaAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handlePizzaAlreadyExistsException(PizzaAlreadyExistsException ex) {
        BaseResponse response = new BaseResponse();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ToppingAlreadyExistsException.class)
    public ResponseEntity<BaseResponse> handleToppingAlreadyExistsException(ToppingAlreadyExistsException ex) {
        BaseResponse response = new BaseResponse();
        response.setMessage(ex.getMessage());
        response.setSuccess(false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
