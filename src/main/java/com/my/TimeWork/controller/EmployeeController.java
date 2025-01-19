package com.my.TimeWork.controller;

import com.my.TimeWork.dto.EmployeeDto;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.service.EmployeeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employees")
@AllArgsConstructor
@Log4j2
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        log.info("/employees/get");
        try {
            return ResponseEntity.ok(employeeService.getAllEmployee());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * Returns employee by its id
     * @param id of an employee to look for
     * @return employee
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        log.info("/get/{}", id);
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        return optionalEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Adds employee to database
     * @param employeeDto
     * @return
     */
    @PostMapping("/post")
    public ResponseEntity<Employee> postEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("/employees/post: {}", employeeDto.toString());
        try {
            Employee savedEmployee =  employeeService.creatEemployee(employeeDto);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        log.info("/delete/{}", id);
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

