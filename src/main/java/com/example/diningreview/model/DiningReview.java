package com.example.diningreview.model;


import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Entity
public class DiningReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Nullable
    private Integer scorePeanut;

    @Nullable
    private Integer scoreEgg;

    @Nullable
    private Integer scoreDairy;

    @Nullable
    private String commentary;

    private String restaurant;

    private String zipcode;

    private String status;

    public DiningReview() {
    }

    public DiningReview(String name, @Nullable Integer scorePeanut, @Nullable Integer scoreEgg, @Nullable Integer scoreDairy, @Nullable String commentary,
                        String restaurant, String zipcode, String status) {
        this.name = name;
        this.scorePeanut = scorePeanut;
        this.scoreEgg = scoreEgg;
        this.scoreDairy = scoreDairy;
        this.commentary = commentary;
        this.restaurant = restaurant;
        this.zipcode = zipcode;
        this.status = status;
    }
}
