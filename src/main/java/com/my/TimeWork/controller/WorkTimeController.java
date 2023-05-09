package com.my.TimeWork.controller;

import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.service.WorkTimeService;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/czas-pracy")
public class WorkTimeController {

    private static final Logger LOGGER = Logger.getLogger(WorkTimeController.class.getName());

    @Autowired
    private WorkTimeService workTimeService;

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getWorkTime(@PathVariable Long id) {
        LOGGER.info("/czas-pracy/get/" + id);
        try{
            WorkTime workTime = workTimeService.getWorkTimeById(id);
            return ResponseEntity.ok(workTime);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Finds WorkTime in a database with null endOfWork field (it means that employee did not end his shift yet), it ends the shift
     * @param EmployeeId is an id of a worker
     * @return {@link ResponseEntity} with either WorkTime as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
     */
    @GetMapping("/get-not-ended-WorkTime-and-end-it/{EmployeeId}")
    public ResponseEntity<WorkTime> getNotEndedWorkTimeAndEndIt(@PathVariable Long EmployeeId){
        LOGGER.info("/czas-pracy/get-not-ended-WorkTime/" + EmployeeId);
        try {
            WorkTime notEndedWorkTime = workTimeService.getNotEndedWorkTime(EmployeeId);
            notEndedWorkTime.setEndOfWork(LocalDateTime.now());
            WorkTime notEndedWorkTimeUpdated = workTimeService.addWorkTime(notEndedWorkTime);
            return ResponseEntity.ok(notEndedWorkTimeUpdated);
        }catch (NoSuchElementException e){
            return ResponseEntity.notFound().build();
        } catch (NonUniqueResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Finds WorkTime in a database with null endOfWork field (it means that employee did not end his shift yet), it does not end a shift
     * @param EmployeeId is an id of a worker
     * @return {@link ResponseEntity} with either WorkTime as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
     */
    @GetMapping("/get-not-ended-WorkTime/{EmployeeId}")
    public ResponseEntity<WorkTime> getNotEndedWorkTime(@PathVariable Long EmployeeId){
        LOGGER.info("/czas-pracy/get-not-ended-WorkTime/" + EmployeeId);
        try {
            WorkTime notEndedWorkTime = workTimeService.getNotEndedWorkTime(EmployeeId);
            return ResponseEntity.ok(notEndedWorkTime);
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
    public boolean postWorkTime(@RequestBody WorkTime workTime) {
        LOGGER.info("/czas-pracy/post/");
        try {
            workTimeService.addWorkTime(workTime);
            return true;
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return false;
        }
    }

    @GetMapping("/get-new-WorkTime/{EmployeeId}")
    public ResponseEntity<WorkTime> getNewWorkTime(@PathVariable Long EmployeeId) {
        LOGGER.info("/czas-pracy/get-new-WorkTime/" + EmployeeId);
        WorkTime workTime = new WorkTime();
        workTime.setEmployeeId(EmployeeId);
        workTime.setBeginningOfWork(LocalDateTime.now());
        try {
            workTime = workTimeService.addWorkTime(workTime);
            return ResponseEntity.ok(workTime);
        }catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return  ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkTime(@PathVariable Long id){
        LOGGER.info("/czas-pracy/delete/" + id);
        try {
            workTimeService.deleteWorkTime(id);
            return ResponseEntity.ok(true);
        }catch (DataAccessException e) {
            //for now, it returns internalServerError as generic error
            return ResponseEntity.internalServerError().build();
        }
    }
}
