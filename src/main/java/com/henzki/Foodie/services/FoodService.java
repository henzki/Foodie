package com.henzki.Foodie.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.henzki.Foodie.entities.Food;
import com.henzki.Foodie.repositories.FoodRepository;

@Service
public class FoodService {

	@Autowired
	private FoodRepository repository;
	
	public void saveFood(Food food) {
		repository.save(food);	
	}

	public List<Food> getAllFoods() {
		return repository.findAll();
	}

	public Optional<Food> getFoodById(Long id) {
		return repository.findById(id);
	}
	
	public void delete(long id) {
        repository.deleteById(id);
    }
}
