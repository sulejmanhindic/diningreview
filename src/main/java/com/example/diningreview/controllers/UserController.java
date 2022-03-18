package com.example.diningreview.controllers;

import com.example.diningreview.model.User;
import com.example.diningreview.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/users")
    public Object createNewUser(@RequestBody User user) {
        if (!this.userRepository.findByName(user.getName()).isPresent()) {
            User newUser = this.userRepository.save(user);
            return newUser;
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does exist.");
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable("id") Integer id) {
        return this.userRepository.findById(id);
    }

    @PutMapping("/users/{id}")
    public Object updateUser(@PathVariable("id") Integer id, @RequestBody User u) {
        Optional<User> userToUpdateOptional = this.userRepository.findById(id);

        if (!userToUpdateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist.");
        }

        User userToUpdate = userToUpdateOptional.get();

        if (u.getCity() != null) {
            userToUpdate.setCity(u.getCity());
        }

        if (u.getState() != null) {
            userToUpdate.setState(u.getState());
        }

        if (u.getZipcode() != null) {
            userToUpdate.setZipcode(u.getZipcode());
        }

        if (u.getInterestedInDairyAllergies() != null) {
            userToUpdate.setInterestedInDairyAllergies(u.getInterestedInDairyAllergies());
        }

        if (u.getInterestedInEggAllergies() != null) {
            userToUpdate.setInterestedInEggAllergies(u.getInterestedInEggAllergies());
        }

        if (u.getInterestedInPeanutAllergies() != null) {
            userToUpdate.setInterestedInPeanutAllergies(u.getInterestedInPeanutAllergies());
        }

        User updatedUser = this.userRepository.save(userToUpdate);
        return updatedUser;
    }

    @GetMapping("/users/search")
    public Object findUser(@RequestParam(name = "name", required = true) String name) {
        Optional<User> userToGet = this.userRepository.findByName(name);

        if(userToGet.isPresent()) {
            return userToGet;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User does not exist.");
        }
    }
}
