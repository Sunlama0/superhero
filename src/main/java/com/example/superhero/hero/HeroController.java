package com.example.superhero.hero;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/heroes")
@RequiredArgsConstructor
public class HeroController {

    private final HeroRepository heroRepository;

    @GetMapping
    public List<Hero> all() {
        return heroRepository.findAll();
    }

    @GetMapping("/{id}")
    public Hero one(@PathVariable Long id) {
        return heroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public Hero create(@RequestBody Hero hero) {
        hero.setId(null);
        return heroRepository.save(hero);
    }

    @PutMapping("/{id}")
    public Hero update(@PathVariable Long id, @RequestBody Hero hero) {
        Hero existing = heroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        existing.setName(hero.getName());
        existing.setPower(hero.getPower());
        existing.setLevel(hero.getLevel());
        existing.setCity(hero.getCity());

        return heroRepository.save(existing);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        heroRepository.deleteById(id);
    }
}
