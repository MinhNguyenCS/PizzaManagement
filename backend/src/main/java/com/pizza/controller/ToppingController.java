package com.pizza.controller;
import com.pizza.response.BaseResponse;
import com.pizza.service.imp.ToppingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@CrossOrigin
@RestController
@RequestMapping("/topping")
public class ToppingController {
    @Autowired
    private ToppingServiceImp toppingServiceImp;

    @GetMapping("")
    public ResponseEntity<?> getAllTopping() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Getting All Toppings Successfully");
        baseResponse.setData(toppingServiceImp.getAllTopping());
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/adding")
    public ResponseEntity<?> addTopping(@RequestParam String name) {
        toppingServiceImp.addingTopping(name);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Adding Topping Successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTopping(@RequestParam long id,
                                           @RequestParam String name) {
        toppingServiceImp.updateTopping(id, name);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Updating Topping Successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<?> deleteTopping(@PathVariable long id) {
        toppingServiceImp.deleteTopping(id);
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        baseResponse.setMessage("Delete Topping Successfully");
        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

}
