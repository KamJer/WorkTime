package com.my.TimeWork.controller;

import com.my.TimeWork.service.WorkTimeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/work-time")
@Log4j2
@AllArgsConstructor
public class WorkTimeController {

    private WorkTimeService workTimeService;

    @PostMapping("/{employeeId}")
    public ResponseEntity<Void> endOrStartWorkTime(@PathVariable Long employeeId) {
        log.info("POST: /work-time/{}", employeeId);
        workTimeService.createOrEndWorkTime(employeeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{employeeId}")
    public ResponseEntity<Boolean> isWorkTimeStarted(@PathVariable Long employeeId) {
        log.info("GET: /work-time/{}", employeeId);
        return ResponseEntity.ok(workTimeService.isWorkTimeStarted(employeeId));
    }
}
