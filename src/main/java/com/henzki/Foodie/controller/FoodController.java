package com.henzki.Foodie.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.henzki.Foodie.entities.Food;
import com.henzki.Foodie.services.FoodService;

@Controller
public class FoodController {
	
	@Value("${uploadDir}")
	private String uploadFolder;
	
	@Autowired
	private FoodService service;
	
	//INDEX, LIST OF ALL
	@GetMapping("/")
	String show(Model map) {
		List<Food> foods = service.getAllFoods();
		map.addAttribute("foods", foods);
		return "foods";
	}
	
	//IMAGE DISPLAY IN INDEX
		@GetMapping("/food/display/{id}")
		@ResponseBody
		void showImage(@PathVariable("id") Long id, HttpServletResponse response, Optional<Food> food)
				throws ServletException, IOException {
			food = service.getFoodById(id);
			response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
			response.getOutputStream().write(food.get().getImage());
			response.getOutputStream().close();
		}
	
	//ADD
	@GetMapping("/add")
	public String addFoodPage() {
		return "add";
	}

	//SAVE
	@PostMapping("/saveFood")
	public @ResponseBody ResponseEntity<?> createFood(@RequestParam("name") String name,
			@RequestParam("category") String category, @RequestParam("time") String time, @RequestParam("link") String link, Model model, HttpServletRequest request
			,final @RequestParam("image") MultipartFile file, @RequestParam("cuisine") String cuisine) {
		try {
			
			String uploadDirectory = request.getServletContext().getRealPath(uploadFolder);
			String fileName = file.getOriginalFilename();
			String filePath = Paths.get(uploadDirectory, fileName).toString();
			
			if (fileName == null || fileName.contains("..")) {
				model.addAttribute("invalid", "Filename contains invalid path sequence \" + fileName");
				return new ResponseEntity<>("Filename contains invalid path sequence " + fileName, HttpStatus.BAD_REQUEST);
			}
			
			String[] names = name.split(",");
			String[] categories = category.split(",");
			String[] times = time.split(",");
			String[] links = link.split(",");
			String[] cuisines = cuisine.split(",");
			
			try {
				File dir = new File(uploadDirectory);
				if (!dir.exists()) {
					dir.mkdirs();
				}
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
				stream.write(file.getBytes());
				stream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			byte[] imageData = file.getBytes();
			
			Food food = new Food();
			
			food.setName(names[0]);
			food.setCategory(categories[0]);
			food.setTime(times[0]);
			food.setLink(links[0]);
			food.setImage(imageData);
			food.setCuisine(cuisines[0]);
			
			service.saveFood(food);
			
			return new ResponseEntity<>("Food Saved With File - " + fileName, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	//VIEW DETAILS
	@GetMapping("/view")
	String showFoodDetails(@RequestParam("id") Long id, Optional<Food> food, Model model) {
		try {
			if (id != 0) {
				food = service.getFoodById(id);
			
				if (food.isPresent()) {
					model.addAttribute("id", food.get().getId());
					model.addAttribute("name", food.get().getName());
					model.addAttribute("time", food.get().getTime());
					model.addAttribute("link", food.get().getLink());
					model.addAttribute("category", food.get().getCategory());
					model.addAttribute("cuisine", food.get().getCuisine());
					
					return "view";
				}
				return "redirect:/home";
			}
		return "redirect:/";
		} catch (Exception e) {
			e.printStackTrace();
			return "redirect:/";
		}	
	}
	
	//DELETE
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public String deleteFood(@PathVariable("id") Long foodId, Model model) {
	 service.delete(foodId);
	 return "redirect:../";
	}
}
