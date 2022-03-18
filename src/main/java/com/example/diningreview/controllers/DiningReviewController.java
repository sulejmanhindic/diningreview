package com.example.diningreview.controllers;

import com.example.diningreview.model.Status;
import com.example.diningreview.model.DiningReview;
import com.example.diningreview.model.User;
import com.example.diningreview.repositories.DiningReviewRepository;
import com.example.diningreview.repositories.UserRepository;
import com.example.diningreview.repositories.RestaurantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class DiningReviewController {
    private final DiningReviewRepository diningReviewRepository;
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;

    public DiningReviewController(DiningReviewRepository diningReviewRepository, UserRepository userRepository, RestaurantRepository restaurantRepository) {
        this.diningReviewRepository = diningReviewRepository;
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @PostMapping("/diningreviews")
    public Object createNewDiningReview(@RequestBody DiningReview diningReview) {
        if (this.userRepository.findByName(diningReview.getName()).isPresent() &&
        this.restaurantRepository.findByNameAndZipcode(diningReview.getRestaurant(), diningReview.getZipcode()).isPresent()) {
            DiningReview newDiningReview = this.diningReviewRepository.save(diningReview);
            return newDiningReview;
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User or restaurant does not exist. Review not submitted.");
        }
    }

    @GetMapping("/diningreviews/{status}")
    public List<DiningReview> getDiningReviewsPerStatus(@PathVariable("status") String status) {
        return this.diningReviewRepository.findByStatus(status);
    }

    @GetMapping("/diningreviews/pending")
    public List<DiningReview> getPendingDiningReviews() {
        return this.diningReviewRepository.findByStatus("pending");
    }

    // Verbesserungsmöglichtkeit: statt id per name
    @PutMapping("/diningreviews/{id}/{status}")
    public Object approveOrRejectDiningReview(@PathVariable("id") Integer id,
                                                    @PathVariable("status") String status, @RequestBody DiningReview dr) {
        Optional<DiningReview> diningReviewToUpdateOptional = this.diningReviewRepository.findById(id);

        if (!diningReviewToUpdateOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dining review does not exist.");
        }

        DiningReview diningReviewToUpdate = diningReviewToUpdateOptional.get();

        if (!diningReviewToUpdate.getStatus().equals("pending")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dining review must be in the status pending.");
        }

        if (dr.getStatus().equals("approved") || dr.getStatus().equals("rejected"))
            diningReviewToUpdate.setStatus(dr.getStatus());
        else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dining review must either transform to status approved or rejected");

        DiningReview updatedDiningReview = this.diningReviewRepository.save(diningReviewToUpdate);

        if (updatedDiningReview.getStatus().equals("approved")) {
            this.restaurantRepository.updateScores(updatedDiningReview.getRestaurant(), updatedDiningReview.getZipcode());
            this.restaurantRepository.updateAverageRating(updatedDiningReview.getRestaurant(), updatedDiningReview.getZipcode());
        }

        return updatedDiningReview;
    }

    // Verbesserungsmöglichtkeit: statt id per name
    @GetMapping("/diningreviews/{id}/getapproved")
    public List<DiningReview> getApprovedDiningReviews(@PathVariable("id") Integer id) {
        return this.diningReviewRepository.findByIdAndStatus(id, "approved");
    }

    @GetMapping("/diningreview/approved/search")
    public List<DiningReview> getApprovedDiningReviewsRestaurant(@RequestParam(name = "restaurant", required = true) String restaurant) {
        return this.diningReviewRepository.findByRestaurantAndStatus(restaurant, "approved");
    }
}
