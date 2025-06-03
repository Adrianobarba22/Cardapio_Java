package com.adrianobarbosa.cardapio.controller;

import com.adrianobarbosa.cardapio.dto.FoodRequestDTO;
import com.adrianobarbosa.cardapio.dto.FoodResponseDTO;
import com.adrianobarbosa.cardapio.food.Food;
import com.adrianobarbosa.cardapio.repository.FoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class FoodControllerTest {

    private MockMvc mockMvc;

    @Mock
    private FoodRepository foodRepository;

    @InjectMocks
    private FoodController foodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();
    }

    @Test
    void shouldGetAllFoods() throws Exception {
        Food food = new Food(1L, "Pizza", "pizza.jpg", 25);
        List<Food> foodList = Arrays.asList(food);
        when(foodRepository.findAll()).thenReturn(foodList);

        mockMvc.perform(get("/food"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Pizza"));
    }

    @Test
    void shouldSaveFood() throws Exception {
        String json = "{\"title\":\"Pizza\",\"image\":\"pizza.jpg\",\"price\":25}";

        mockMvc.perform(post("/food")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void shouldUpdateFood() throws Exception {
        Food existingFood = new Food(1L, "Pizza", "pizza.jpg", 25);
        when(foodRepository.findById(1L)).thenReturn(Optional.of(existingFood));

        String json = "{\"title\":\"Pizza Calabresa\",\"image\":\"pizza2.jpg\",\"price\":30}";

        mockMvc.perform(put("/food/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());

        verify(foodRepository, times(1)).save(any(Food.class));
    }

    @Test
    void shouldDeleteFood() throws Exception {
        when(foodRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/food/1"))
                .andExpect(status().isOk());

        verify(foodRepository, times(1)).deleteById(1L);
    }
}
