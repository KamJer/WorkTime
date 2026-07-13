package com.my.TimeWork.controller;

import com.my.TimeWork.dto.EmployeeDto;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("GET: /employees/get");
        return ResponseEntity.ok(employeeService.getAllEmployee());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("GET: /employees/get/{}", id);
        return employeeService.getEmployeeById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
    }

    @PostMapping("/post")
    public ResponseEntity<Employee> postEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("POST: /employees/post: {}", employeeDto);
        return ResponseEntity.ok(employeeService.creatEemployee(employeeDto));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("DELETE: /employees/delete/{}", id);
        employeeService.getEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
