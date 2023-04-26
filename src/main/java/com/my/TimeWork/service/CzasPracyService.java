package com.my.TimeWork.service;

import com.my.TimeWork.entity.CzasPracy;
import com.my.TimeWork.repository.CzasPracyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CzasPracyService {
    @Autowired
    private CzasPracyRepository czasPracyRepository;

    public CzasPracy getCzasPracyById(Long id) {
        Optional<CzasPracy> czasPracyOptional = czasPracyRepository.findById(id);
        if (!czasPracyOptional.isPresent()) {
            throw new NoSuchElementException("Czas pracy o id " + id + " nie istnieje");
        }
        CzasPracy czasPracy = czasPracyOptional.get();
        return czasPracy;
    }

    public CzasPracy getNotEndedCzasPracy(Long pracownikId) {
        Optional<CzasPracy> czasPracyOptional = czasPracyRepository.findByPracownikIdAndEndOfWorkIsNull(pracownikId);
        if (!czasPracyOptional.isPresent()) {
            throw new NoSuchElementException("nie zakończony czas pracy o pracownik id " + pracownikId + " nie istnieje");
        }
        CzasPracy czasPracy = czasPracyOptional.get();
        return czasPracy;
    }

    public CzasPracy addCzasPracy(CzasPracy czasPracy) {
        return czasPracyRepository.save(czasPracy);
    }

    public Boolean deleteCzasPracy(Long id) throws DataAccessException {
        czasPracyRepository.deleteById(id);
        if (czasPracyRepository.existsById(id)){
            return false;
        }
        return true;
    }
}
