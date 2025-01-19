package com.my.TimeWork.controller;

import com.my.TimeWork.dto.WorkTimeDto;
import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.service.WorkTimeService;
import lombok.extern.log4j.Log4j2;
import org.hibernate.NonUniqueResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.logging.Logger;

@RestController
@RequestMapping("/work-time")
@Log4j2
public class WorkTimeController {

    @Autowired
    private WorkTimeService workTimeService;

    @PostMapping("/{id}")
    public ResponseEntity<Boolean> endOrStartWorkTime(@PathVariable Long employeeId) {
        log.info("POST: /work-time/{}", employeeId);
        return ResponseEntity.ok(workTimeService.createOrEndWorkTime(employeeId));
    }

    @GetMapping("{id}")
    public ResponseEntity<Boolean> getIfWorkTimeStarted(Long employeeId) {
        log.info("GET: /work-time/{}", employeeId);
        return ResponseEntity.ok(workTimeService.)
    }

//    @GetMapping("/get/{id}")
//    public ResponseEntity<?> getWorkTime(@PathVariable Long id) {
//        log.info("/work-time/get/{}", id);
//        try{
//            return ResponseEntity.ok(workTimeService.getWorkTimeById(id));
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }

//    /**
//     * Finds WorkTime in a database with null endOfWork field (it means that employee did not end his shift yet), it ends the shift
//     * @param employeeId is an id of a worker
//     * @return {@link ResponseEntity} with either WorkTime as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
//     */
//    @GetMapping("/get-not-ended-WorkTime-and-end-it/{EmployeeId}")
//    public ResponseEntity<WorkTime> getNotEndedWorkTimeAndEndIt(@PathVariable Long employeeId){
//        log.info("/work-time/get-not-ended-WorkTime-and-end-it/{}", employeeId);
//        try {
//            return ResponseEntity.ok(workTimeService.endStartedWorkTime(employeeId));
//        }catch (NoSuchElementException | NonUniqueResultException e){
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    /**
//     * Finds WorkTime in a database with null endOfWork field (it means that employee did not end his shift yet), it does not end a shift
//     * @param EmployeeId is an id of a worker
//     * @return {@link ResponseEntity} with either WorkTime as generic or notFound entity (ResponseEntity.notFound().build()) if an entity was not found
//     */
//    @GetMapping("/get-not-ended-WorkTime/{EmployeeId}")
//    public ResponseEntity<WorkTime> getNotEndedWorkTime(@PathVariable Long EmployeeId){
//        log.info("/work-time/get-not-ended-WorkTime/{}", EmployeeId);
//        try {
//            WorkTime notEndedWorkTime = workTimeService.getNotEndedWorkTime(EmployeeId);
//            return ResponseEntity.ok(notEndedWorkTime);
//        }catch (NoSuchElementException e){
//            log.info(e.getMessage());
//            return ResponseEntity.notFound().build();
//        } catch (NonUniqueResultException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().build();
//        }
//    }

//    @PostMapping("/post")
//    public boolean postWorkTime(@RequestBody WorkTimeDto workTimeDto) {
//        log.info("/work-time/post/");
//        try {
//            workTimeService.addWorkTime(workTimeDto);
//            return true;
//        } catch (Exception e) {
//            log.info(e.getMessage());
//            return false;
//        }
//    }
//
//    @GetMapping("/get-new-WorkTime/{EmployeeId}")
//    public ResponseEntity<WorkTime> getNewWorkTime(@PathVariable Long employeeId) {
//        log.info("/work-time/get-new-WorkTime/{}", employeeId);
//        WorkTime workTime = new WorkTime();
//        workTime.setEmployeeId(employeeId);
//        workTime.setBeginningOfWork(LocalDateTime.now());
//        try {
//            workTime = workTimeService.addWorkTime(workTime);
//            return ResponseEntity.ok(workTime);
//        }catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            return  ResponseEntity.internalServerError().build();
//        }
//    }
//
//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteWorkTime(@PathVariable Long id){
//        log.info("/czas-pracy/delete/{}", id);
//        try {
//            workTimeService.deleteWorkTime(id);
//            return ResponseEntity.ok(true);
//        }catch (DataAccessException e) {
//            //for now, it returns internalServerError as generic error
//            return ResponseEntity.internalServerError().build();
//        }
//    }
}
