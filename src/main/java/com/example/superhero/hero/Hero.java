package com.example.superhero.hero;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Hero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String power;
    private int level;
    private String city;
}
