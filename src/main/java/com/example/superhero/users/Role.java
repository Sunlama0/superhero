package com.example.superhero.users;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name; // "ROLE_ADMIN" ou "ROLE_USER"

    @Override
    public String getAuthority() {
        return name;
    }
}
