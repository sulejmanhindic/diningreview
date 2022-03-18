package com.example.diningreview.model;

import lombok.*;

import javax.persistence.*;

@ToString
@EqualsAndHashCode
@Setter
@Getter
@Data
@Entity
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String zipcode;
    private Integer scorePeanut;
    private Integer scoreEgg;
    private Integer scoreDairy;
    private Double averageRating;

    public Restaurant() {
    }

    public Restaurant(String name, String zipcode, Integer scorePeanut, Integer scoreEgg, Integer scoreDairy) {
        this.name = name;
        this.zipcode = zipcode;
        this.scorePeanut = scorePeanut;
        this.scoreEgg = scoreEgg;
        this.scoreDairy = scoreDairy;
    }



}
