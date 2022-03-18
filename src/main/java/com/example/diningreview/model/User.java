package com.example.diningreview.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String city;
    private String state;
    private String zipcode;
    private Boolean interestedInPeanutAllergies;
    private Boolean interestedInEggAllergies;
    private Boolean interestedInDairyAllergies;
}
