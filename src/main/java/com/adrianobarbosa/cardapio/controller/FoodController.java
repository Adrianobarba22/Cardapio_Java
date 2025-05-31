package com.adrianobarbosa.cardapio.controller;

import com.adrianobarbosa.cardapio.dto.FoodRequestDTO;
import com.adrianobarbosa.cardapio.dto.FoodResponseDTO;
import com.adrianobarbosa.cardapio.food.Food;
import com.adrianobarbosa.cardapio.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("food")
public class FoodController {

    @Autowired
    private FoodRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveFood(@RequestBody FoodRequestDTO data){
        Food foodData = new Food(data);
        repository.save(foodData);
        return;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<FoodResponseDTO> getAll(){

        List<FoodResponseDTO> foodList = repository.findAll().stream().map(FoodResponseDTO::new).toList();
        return foodList;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateFood(@PathVariable Long id, @RequestBody FoodRequestDTO data){
        Food existingFood = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));

        existingFood.updateFromDTO(data);

        repository.save(existingFood);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteFood(@PathVariable Long id){
        if(!repository.existsById(id)){
            throw new RuntimeException("Food not found");
        }
        repository.deleteById(id);
    }
}
