package com.example.diningreview.repositories;

import com.example.diningreview.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Integer> {
    /*

    As an unregistered user, I want to create my user profile using a display name that’s unique only to me. (done)
    As a registered user, I want to update my user profile. I cannot modify my unique display name. (done)
    As an application experience, I want to fetch the user profile belonging to a given display name. (done)
    As part of the backend process that validates a user-submitted dining review,
    I want to verify that the user exists, based on the user display name associated with the dining review. (done)

     */

    Optional<User> findByName(String name);
}
