package com.example.superhero.auth;

import com.example.superhero.auth.dto.AuthRequest;
import com.example.superhero.auth.dto.AuthResponse;
import com.example.superhero.security.JwtTokenManager;
import com.example.superhero.users.Role;
import com.example.superhero.users.RoleRepository;
import com.example.superhero.users.Users;
import com.example.superhero.users.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsersRepository usersRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest req) {

        if (usersRepository.findByEmail(req.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already used");
        }

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER missing in DB"));

        Users u = new Users();
        u.setEmail(req.getEmail());
        u.setPassword(encoder.encode(req.getPassword()));
        u.setActive(true);
        u.setRoles(java.util.List.of(userRole));

        usersRepository.save(u);

        String token = JwtTokenManager.generateToken(u.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );

        Users user = (Users) auth.getPrincipal();
        String token = JwtTokenManager.generateToken(user.getEmail());
        return ResponseEntity.ok(new AuthResponse(token));
    }
}
