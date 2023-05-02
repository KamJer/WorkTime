package com.my.TimeWork.service;

import com.my.TimeWork.entity.Pracownik;
import com.my.TimeWork.repository.PracownikRepository;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PracownikService {

    private final PracownikRepository pracownikRepository;

    public PracownikService(PracownikRepository pracownikRepository) {
        this.pracownikRepository = pracownikRepository;
    }

    public List<Pracownik> getAllPracownicy() {
        return pracownikRepository.findAll();
    }

    public Optional<Pracownik> getPracownikById(Long id) {
        return pracownikRepository.findById(id);
    }

    public Pracownik createPracownik(Pracownik pracownik) {
        return pracownikRepository.save(pracownik);
    }

    public void deletePracownik(Long id) {
        pracownikRepository.deleteById(id);
    }
}
