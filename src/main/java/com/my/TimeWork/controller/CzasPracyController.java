package com.my.TimeWork.controller;

import com.my.TimeWork.entity.CzasPracy;
import com.my.TimeWork.service.CzasPracyService;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
     * Finds CzasPracy in a database with null endOfWork field (it means that employee did not end his shift yet), it ends the shift
     * @param pracownikId is an id of a worker
     * @return {@link ResponseEntity} with either CzasPracy as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
     */
    @GetMapping("/get-not-ended-czasPracy-and-end-it/{pracownikId}")
    public ResponseEntity<CzasPracy> getNotEndedCzasPracyAndEndIt(@PathVariable Long pracownikId){
        LOGGER.info("/get-not-ended-czasPracy/" + pracownikId);
        try {
            CzasPracy notEndedCzasPracy = czasPracyService.getNotEndedCzasPracy(pracownikId);
            notEndedCzasPracy.setEndOfWork(LocalDateTime.now());
            CzasPracy notEndedCzasPracyUpdated = czasPracyService.addCzasPracy(notEndedCzasPracy);
            return ResponseEntity.ok(notEndedCzasPracyUpdated);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (NonUniqueResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Finds CzasPracy in a database with null endOfWork field (it means that employee did not end his shift yet), it does not end a shift
     * @param pracownikId is an id of a worker
     * @return {@link ResponseEntity} with either CzasPracy as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
     */
    @GetMapping("/get-not-ended-czasPracy/{pracownikId}")
    public ResponseEntity<CzasPracy> getNotEndedCzasPracy(@PathVariable Long pracownikId){
        LOGGER.info("/get-not-ended-czasPracy/" + pracownikId);
        try {
            CzasPracy notEndedCzasPracy = czasPracyService.getNotEndedCzasPracy(pracownikId);
            return ResponseEntity.ok(notEndedCzasPracy);
        }catch (NoSuchElementException e){
            LOGGER.info(e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (NonUniqueResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
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
