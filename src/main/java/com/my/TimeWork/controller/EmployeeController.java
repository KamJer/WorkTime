package com.my.TimeWork.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.TimeWork.dto.EmployeeDto;
import com.my.TimeWork.dto.EmployeeDto.CreateGroup;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;
    private final ObjectMapper objectMapper;

    @GetMapping("/get")
    public ResponseEntity<List<EmployeeDto>> getAllEmployees() {
        log.info("GET: /employees/get");
        List<EmployeeDto> dtos = employeeService.getAllEmployee().stream()
                .map(e -> objectMapper.convertValue(e, EmployeeDto.class))
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<EmployeeDto> getEmployeeById(@Positive @PathVariable Long id) {
        log.info("GET: /employees/get/{}", id);
        return employeeService.getEmployeeById(id)
                .map(e -> ResponseEntity.ok(objectMapper.convertValue(e, EmployeeDto.class)))
                .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
    }

    @PostMapping("/post")
    public ResponseEntity<EmployeeDto> postEmployee(@Validated(CreateGroup.class) @RequestBody EmployeeDto employeeDto) {
        log.info("POST: /employees/post: {}", employeeDto);
        Employee employee = employeeService.createEmployee(employeeDto);
        return ResponseEntity.ok(objectMapper.convertValue(employee, EmployeeDto.class));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@Positive @PathVariable Long id) {
        log.info("DELETE: /employees/delete/{}", id);
        employeeService.getEmployeeById(id)
                .orElseThrow(() -> new NoSuchElementException("Employee with id " + id + " not found"));
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
