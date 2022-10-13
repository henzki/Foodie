package com.henzki.Foodie.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.henzki.Foodie.entities.Food;

public interface FoodRepository extends JpaRepository<Food, Long> {
	
	Optional<Food> findByName(String name);

}
