package com.my.TimeWork;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.TimeWork.controller.EmployeeController;
import com.my.TimeWork.dto.EmployeeDto;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class ValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateEmployee_emptyName_returns400() throws Exception {
        EmployeeDto dto = new EmployeeDto(null, "");

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEmployee_blankName_returns400() throws Exception {
        EmployeeDto dto = new EmployeeDto(null, "   ");

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEmployee_nullName_returns400() throws Exception {
        EmployeeDto dto = new EmployeeDto(null, null);

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEmployee_nameTooLong_returns400() throws Exception {
        String longName = "a".repeat(129);
        EmployeeDto dto = new EmployeeDto(null, longName);

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testCreateEmployee_validName_returns200() throws Exception {
        EmployeeDto dto = new EmployeeDto(null, "Jan Kowalski");
        Employee savedEmployee = new Employee(1L, "Jan Kowalski");

        when(employeeService.createEmployee(any(EmployeeDto.class))).thenReturn(savedEmployee);

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jan Kowalski"));
    }

    @Test
    void testCreateEmployee_idNotNull_returns400() throws Exception {
        EmployeeDto dto = new EmployeeDto(1L, "Jan Kowalski");

        mockMvc.perform(post("/employees/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetEmployee_negativeId_returns400() throws Exception {
        mockMvc.perform(get("/employees/get/-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetEmployee_zeroId_returns400() throws Exception {
        mockMvc.perform(get("/employees/get/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetEmployee_validId_returns200() throws Exception {
        Employee employee = new Employee(1L, "Jan Kowalski");
        when(employeeService.getEmployeeById(1L)).thenReturn(Optional.of(employee));

        mockMvc.perform(get("/employees/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Jan Kowalski"));
    }

    @Test
    void testGetAllEmployees_returnsDtoList() throws Exception {
        List<Employee> employees = List.of(
                new Employee(1L, "Jan Kowalski"),
                new Employee(2L, "Anna Nowak")
        );
        when(employeeService.getAllEmployee()).thenReturn(employees);

        mockMvc.perform(get("/employees/get"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("Jan Kowalski"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Anna Nowak"));
    }
}
