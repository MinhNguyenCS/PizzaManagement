package com.pizza;
import com.pizza.controller.ToppingController;
import com.pizza.entity.ToppingEntity;
import com.pizza.exception.GlobalExceptionHandler;
import com.pizza.exception.ToppingAlreadyExistsException;
import com.pizza.service.imp.ToppingServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.List;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//Unit Test for PizzaController
/*
It should allow me to see a list of available toppings
It should allow me to add a new topping
It should allow me to delete an existing topping
It should allow me to update an existing topping
It should not allow me to enter duplicate toppings*/
@ExtendWith(MockitoExtension.class)
public class ToppingControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ToppingServiceImp toppingServiceImp;

    @InjectMocks
    private ToppingController toppingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(toppingController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    // Test getting all toppings
    @Test
    void testGetAllToppings() throws Exception {
        List<ToppingEntity> toppings = Arrays.asList(
                new ToppingEntity(1L, "Cheese"),
                new ToppingEntity(2L, "Pepperoni")
        );

        when(toppingServiceImp.getAllTopping()).thenReturn(toppings);

        mockMvc.perform(get("/topping"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Getting All Toppings Successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Cheese"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Pepperoni"));

        verify(toppingServiceImp, times(1)).getAllTopping();
    }

    // Test adding a new topping
    @Test
    void testAddTopping() throws Exception {
        doNothing().when(toppingServiceImp).addingTopping(anyString());

        mockMvc.perform(post("/topping/adding")
                        .param("name", "Mushrooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Adding Topping Successfully"));

        verify(toppingServiceImp, times(1)).addingTopping(anyString());
    }

    // Test adding a duplicate topping (invalid case)
    @Test
    void testAddDuplicateTopping() throws Exception {
        doThrow(new ToppingAlreadyExistsException("The topping already exists.")).when(toppingServiceImp).addingTopping(anyString());

        mockMvc.perform(post("/topping/adding")
                        .param("name", "Cheese"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("The topping already exists."));

        verify(toppingServiceImp, times(1)).addingTopping(anyString());
    }

    // Test updating a topping
    @Test
    void testUpdateTopping() throws Exception {
        long toppingId = 1L;

        doNothing().when(toppingServiceImp).updateTopping(eq(toppingId), anyString());

        mockMvc.perform(post("/topping/update")
                        .param("id", String.valueOf(toppingId))
                        .param("name", "Extra Cheese"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Updating Topping Successfully"));

        verify(toppingServiceImp, times(1)).updateTopping(eq(toppingId), anyString());
    }

    // Test updating a topping with a duplicate name (invalid case)
    @Test
    void testUpdateDuplicateTopping() throws Exception {
        long toppingId = 1L;
        doThrow(new ToppingAlreadyExistsException("The topping already exists.")).when(toppingServiceImp).updateTopping(eq(toppingId), anyString());

        mockMvc.perform(post("/topping/update")
                        .param("id", String.valueOf(toppingId))
                        .param("name", "Cheese"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("The topping already exists."));

        verify(toppingServiceImp, times(1)).updateTopping(eq(toppingId), anyString());
    }

    // Test deleting a topping
    @Test
    void testDeleteTopping() throws Exception {
        long toppingId = 1L;
        doNothing().when(toppingServiceImp).deleteTopping(toppingId);

        mockMvc.perform(post("/topping/delete/{id}", toppingId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Delete Topping Successfully"));

        verify(toppingServiceImp, times(1)).deleteTopping(toppingId);
    }

}
