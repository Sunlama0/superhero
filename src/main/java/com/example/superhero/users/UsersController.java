package com.example.superhero.users;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UsersController {

    private final UsersRepository usersRepository;

    @GetMapping
    public List<Users> all() {
        return usersRepository.findAll();
    }
}
