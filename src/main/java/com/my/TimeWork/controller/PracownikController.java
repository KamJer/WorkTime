package com.my.TimeWork.controller;

import com.my.TimeWork.entity.Pracownik;
import com.my.TimeWork.service.PracownikService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pracownicy")
public class PracownikController {

    private final PracownikService pracownikService;

    public PracownikController(PracownikService pracownikService) {
        this.pracownikService = pracownikService;
    }

    @GetMapping("/get")
    public List<Pracownik> getAllPracownicy() {
        return pracownikService.getAllPracownicy();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Pracownik> getPracownikById(@PathVariable Long id) {
        Optional<Pracownik> optionalPracownik = pracownikService.getPracownikById(id);
        return optionalPracownik.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/post")
    public Pracownik createPracownik(@RequestBody Pracownik pracownik) {
        return pracownikService.createPracownik(pracownik);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePracownik(@PathVariable Long id) {
        if (pracownikService.getPracownikById(id).isPresent()) {
            pracownikService.deletePracownik(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

