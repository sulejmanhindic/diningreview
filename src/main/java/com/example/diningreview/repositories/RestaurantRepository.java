package com.example.diningreview.repositories;

import com.example.diningreview.model.Restaurant;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RestaurantRepository extends CrudRepository<Restaurant, Integer> {
    /*

    As an application experience, I want to submit a new restaurant entry. Should a restaurant with the same name and with the same zip code already exist, I will see a failure.
    As an application experience, I want to fetch the details of a restaurant, given its unique Id.
    As an application experience, I want to fetch restaurants that match a given zip code and that also have at least one user-submitted score for a given allergy.
    I want to see them sorted in descending order.
     */

    Optional<Restaurant> findByNameAndZipcode(String name, String zipcode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE restaurant r " +
            "set r.score_peanut = (SELECT AVG(d.score_peanut) from dining_review d where d.restaurant = ?1 and d.zipcode = ?2)" +
            ", r.score_dairy = (SELECT AVG(d.score_dairy) from dining_review d where d.restaurant = ?1 and d.zipcode = ?2)" +
            ", r.score_egg = (SELECT AVG(d.score_egg) from dining_review d where d.restaurant = ?1 and d.zipcode = ?2) " +
            "where r.name= ?1 and r.zipcode = ?2", nativeQuery = true)
    void updateScores(String name, String zipcode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE Restaurant r " +
            "set r.average_rating = (r.score_peanut + r.score_dairy + r.score_egg)/3 " +
            "where r.name= ?1 and r.zipcode = ?2", nativeQuery = true)
    void updateAverageRating(String name, String zipcode);

    /*
    List<Restaurant> findByZipcodeAndScoreEggGreaterThanEqualOrderByScoreEggDesc(Integer number, String zipcode);

    List<Restaurant> findByZipcodeAndScoreDairyGreaterThanEqualOrderByScoreDairyDesc(Integer number, String zipcode);

    List<Restaurant> findByZipcodeAndScorePeanutGreaterThanEqualOrderByScorePeanutDesc(Integer number, String zipcode);
    */

    @Query(value = "select * from restaurant r where r.zipcode = :zipcode and r.score_dairy >= 1 order by r.score_dairy desc", nativeQuery = true)
    List<Restaurant> findByZipcodeAndHasInterestedInDairyAllergiesOrderByScoreDesc(@Param("zipcode") String zipcode);

    @Query(value = "select * from restaurant r where r.zipcode = :zipcode and r.score_egg >= 1 order by r.score_egg desc", nativeQuery = true)
    List<Restaurant> findByZipcodeAndHasInterestedInEggAllergiesOrderByScoreDesc(@Param("zipcode") String zipcode);

    @Query(value = "select * from restaurant r where r.zipcode = :zipcode and r.score_peanut >= 1 order by r.score_peanut desc", nativeQuery = true)
    List<Restaurant> findAllByZipcodeAndHasInterestedInPeanutAllergiesOrderByScoreDesc(@Param("zipcode") String zipcode);


}
