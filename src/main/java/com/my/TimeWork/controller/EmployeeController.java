package com.my.TimeWork.controller;

import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private static final Logger LOGGER = Logger.getLogger(EmployeeController.class.getName());

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        LOGGER.info("/employees/get");
        try {
            return ResponseEntity.ok(employeeService.getAllEmployee());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.getEmployeeById(id);
        return optionalEmployee.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/post")
    public ResponseEntity<Employee> postEmployee(@RequestBody Employee employee) {
        LOGGER.info("/employees/post" + " " + employee.toString());
        try {
            Employee savedEmployee =  employeeService.creatEemployee(employee);
            return ResponseEntity.ok(savedEmployee);
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeService.getEmployeeById(id).isPresent()) {
            employeeService.deleteEmployee(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

