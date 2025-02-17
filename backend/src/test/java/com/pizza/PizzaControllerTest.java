package com.pizza;

import com.pizza.DTO.PizzaRequestDTO;
import com.pizza.controller.PizzaController;
import com.pizza.entity.PizzaEntity;
import com.pizza.exception.GlobalExceptionHandler;
import com.pizza.exception.PizzaAlreadyExistsException;
import com.pizza.service.imp.PizzaServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


//Unit Test for PizzaController
/*
It should allow me to see a list of existing pizzas and their toppings
It should allow me to create a new pizza and add toppings to it
It should allow me to delete an existing pizza
It should allow me to update an existing pizza
It should allow me to update toppings on an existing pizza
It should not allow me to enter duplicate pizzas*/
@ExtendWith(MockitoExtension.class)
public class PizzaControllerTest {
    private MockMvc mockMvc;

    @Mock
    private PizzaServiceImp pizzaServiceImp;

    @InjectMocks
    private PizzaController pizzaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(pizzaController)
                .setControllerAdvice(new GlobalExceptionHandler()) // Attach GlobalExceptionHandler
                .build();
    }

    // Test getting all pizzas
    @Test
    void testGetAllPizza() throws Exception {
        List<PizzaEntity> pizzas = Arrays.asList(
                new PizzaEntity(1L, "Margherita"),
                new PizzaEntity(2L, "Pepperoni")
        );

        when(pizzaServiceImp.getAllPizza()).thenReturn(pizzas);

        mockMvc.perform(get("/chef"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Getting Pizza Successfully"))
                .andExpect(jsonPath("$.data[0].id").value(1))
                .andExpect(jsonPath("$.data[0].name").value("Margherita"))
                .andExpect(jsonPath("$.data[1].id").value(2))
                .andExpect(jsonPath("$.data[1].name").value("Pepperoni"));

        verify(pizzaServiceImp, times(1)).getAllPizza();
    }

    // Test getting a pizza by ID
    @Test
    void testGetPizza() throws Exception {
        long pizzaId = 1L;
        PizzaEntity pizza = new PizzaEntity(pizzaId, "Margherita");

        when(pizzaServiceImp.getPizza(pizzaId)).thenReturn(pizza);

        mockMvc.perform(get("/chef/{id}", pizzaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Getting Pizza Successfully"))
                .andExpect(jsonPath("$.data.id").value(1))
                .andExpect(jsonPath("$.data.name").value("Margherita"));

        verify(pizzaServiceImp, times(1)).getPizza(pizzaId);
    }

    // Test getting a pizza by invalid ID
    @Test
    void testGetPizza_NotFound() throws Exception {
        long pizzaId = 99L;
        when(pizzaServiceImp.getPizza(pizzaId)).thenReturn(null);

        mockMvc.perform(get("/chef/{id}", pizzaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.data").doesNotExist());

        verify(pizzaServiceImp, times(1)).getPizza(pizzaId);
    }

    // Test adding a new pizza
    @Test
    void testAddPizza() throws Exception {
        PizzaRequestDTO request = new PizzaRequestDTO("Hawaiian", new HashSet<>(Arrays.asList(1L, 2L)));

        doNothing().when(pizzaServiceImp).addPizza(any(PizzaRequestDTO.class));

        mockMvc.perform(post("/chef/adding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Hawaiian\", \"toppingIds\": [1,2]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Adding Pizza Successfully"));

        verify(pizzaServiceImp, times(1)).addPizza(any(PizzaRequestDTO.class));
    }

    // Test adding a pizza with duplicate name (invalid case)
    @Test
    void testAddDuplicatePizza() throws Exception {
        PizzaRequestDTO request = new PizzaRequestDTO("Margherita", new HashSet<>(Arrays.asList(1L, 2L)));

        doThrow(new PizzaAlreadyExistsException("Pizza already exists")).when(pizzaServiceImp).addPizza(any(PizzaRequestDTO.class));

        mockMvc.perform(post("/chef/adding")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Margherita\", \"toppingIds\": [1,2]}"))
                .andExpect(status().isBadRequest())  // Expecting BAD_REQUEST now
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("Pizza already exists"));

        verify(pizzaServiceImp, times(1)).addPizza(any(PizzaRequestDTO.class));
    }

    // Test updating an existing pizza
    @Test
    void testUpdatePizza() throws Exception {
        long pizzaId = 1L;
        Set<Long> toppingIds = new HashSet<>(Arrays.asList(1L, 3L));

        doNothing().when(pizzaServiceImp).updatePizza(eq(pizzaId), eq("BBQ Chicken"), eq(toppingIds));

        mockMvc.perform(post("/chef/update/{id}", pizzaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"BBQ Chicken\", \"toppingIds\": [1,3]}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Updating Pizza Successfully"));

        verify(pizzaServiceImp, times(1)).updatePizza(eq(pizzaId), eq("BBQ Chicken"), eq(toppingIds));
    }

    //  Test updating an existing pizza with duplicate name
    @Test
    void testUpdateDuplicatePizza() throws Exception {
        long pizzaId = 1L;
        PizzaRequestDTO updateRequest = new PizzaRequestDTO("Margherita", new HashSet<>(Arrays.asList(1L, 2L)));

        doThrow(new PizzaAlreadyExistsException("A pizza with this name already exists."))
                .when(pizzaServiceImp).updatePizza(eq(pizzaId), anyString(), anySet());

        mockMvc.perform(post("/chef/update/{id}", pizzaId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Margherita\", \"toppingIds\": [1,2]}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false))
                .andExpect(jsonPath("$.message").value("A pizza with this name already exists."));

        verify(pizzaServiceImp, times(1)).updatePizza(eq(pizzaId), anyString(), anySet());
    }


    // Test deleting a pizza
    @Test
    void testDeletePizza() throws Exception {
        long pizzaId = 1L;
        doNothing().when(pizzaServiceImp).deletePizza(pizzaId);

        mockMvc.perform(post("/chef/delete/{id}", pizzaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Deleting Pizza Successfully"));

        verify(pizzaServiceImp, times(1)).deletePizza(pizzaId);
    }

}
