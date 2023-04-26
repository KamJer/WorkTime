package com.my.TimeWork.controller;

import com.my.TimeWork.entity.CzasPracy;
import com.my.TimeWork.service.CzasPracyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/czas-pracy")
public class CzasPracyController {

    private static final Logger LOGGER = Logger.getLogger(CzasPracyController.class.getName());

    @Autowired
    private CzasPracyService czasPracyService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCzasPracy(@PathVariable Long id) {
        try{
            CzasPracy czasPracy = czasPracyService.getCzasPracyById(id);
            return ResponseEntity.ok(czasPracy);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Finds CzasPracy in a database with null endOfWork field (it means that worker did not end his shift yet)
     * @param pracownikId is an id of a worker
     * @return {@link ResponseEntity} with either CzasPracy as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
     */
    @GetMapping("/get-not-ended-czasPracy/{pracownikId}")
    public ResponseEntity<?> getNotEndedCzasPracy(@PathVariable Long pracownikId){
        try {
            CzasPracy czasPracy = czasPracyService.getNotEndedCzasPracy(pracownikId);
            return ResponseEntity.ok(czasPracy);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/post")
    public boolean postCzasPracy(@RequestBody CzasPracy czasPracy) {
        try {
            czasPracyService.addCzasPracy(czasPracy);
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @GetMapping("/get-new-czasPracy/{pracownikId}")
    public ResponseEntity<CzasPracy> getNewCzasPracy(@PathVariable Long pracownikId) {
        LOGGER.info("/get-new-czasPracy/" + pracownikId);
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownikId(pracownikId);
        czasPracy.setBeginningOfWork(LocalDateTime.now());
        try {
            czasPracy = czasPracyService.addCzasPracy(czasPracy);
            return ResponseEntity.ok(czasPracy);
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCzasPracy(@PathVariable Long id){
        try {
            czasPracyService.deleteCzasPracy(id);
            return ResponseEntity.ok(true);
        }catch (DataAccessException e) {
            //for now, it returns internalServerError as generic error
            return ResponseEntity.internalServerError().build();
        }
    }
}
