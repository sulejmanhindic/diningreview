package com.example.diningreview.controllers;

import com.example.diningreview.model.Restaurant;
import com.example.diningreview.model.User;
import com.example.diningreview.repositories.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class RestaurantController {
    private final RestaurantRepository restaurantRepository;

    public RestaurantController(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/restaurants")
    public Object createNewRestaurant(@RequestBody Restaurant restaurant) {
        if (!this.restaurantRepository.findByNameAndZipcode(restaurant.getName(), restaurant.getZipcode()).isPresent()) {
            Restaurant newRestaurant = this.restaurantRepository.save(restaurant);
            return newRestaurant;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Restaurant is already available");
        }

    }

    @GetMapping("/restaurants/{id}")
    public Optional<Restaurant> getRestaurantById(@PathVariable("id") Integer id) {
        return this.restaurantRepository.findById(id);
    }

    @GetMapping("/restaurant/dairy/{zipcode}")
    public List<Restaurant> getRestaurantsByZipcodeWithDairy(@PathVariable("zipcode") String zipcode) {
        return this.restaurantRepository.findByZipcodeAndHasInterestedInDairyAllergiesOrderByScoreDesc(zipcode);
    }

    @GetMapping("/restaurant/egg/{zipcode}")
    public List<Restaurant> getRestaurantsByZipcodeWithEgg(@PathVariable("zipcode") String zipcode) {
        return this.restaurantRepository.findByZipcodeAndHasInterestedInEggAllergiesOrderByScoreDesc(zipcode);
    }

    @GetMapping("/restaurant/peanut/{zipcode}")
    public List<Restaurant> getRestaurantsByZipcodeWithPeanut(@PathVariable("zipcode") String zipcode) {
        return this.restaurantRepository.findAllByZipcodeAndHasInterestedInPeanutAllergiesOrderByScoreDesc(zipcode);
    }

}
