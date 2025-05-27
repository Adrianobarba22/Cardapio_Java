package com.adrianobarbosa.cardapio.repository;

import com.adrianobarbosa.cardapio.food.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
