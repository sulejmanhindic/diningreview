package com.example.diningreview.repositories;

import com.example.diningreview.model.Status;
import com.example.diningreview.model.DiningReview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface DiningReviewRepository extends CrudRepository<DiningReview, Integer> {
    /*

    As a registered user, I want to submit a dining review.
    As an admin, I want to get the list of all dining reviews that are pending approval.
    As an admin, I want to approve or reject a given dining review.
    As part of the backend process that updates a restaurant’s set of scores, I want to fetch the set of all approved dining reviews belonging to this restaurant.

     */

    List<DiningReview> findByStatus(String status);
    List<DiningReview> findByIdAndStatus(Integer id, String status);
    List<DiningReview> findByRestaurantAndStatus(String restaurant, String status);
}
