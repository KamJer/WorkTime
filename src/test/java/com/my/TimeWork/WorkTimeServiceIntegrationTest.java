package com.my.TimeWork;

import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.repository.WorkTimeRepository;
import com.my.TimeWork.service.WorkTimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@SpringBootTest
@Transactional
public class WorkTimeServiceIntegrationTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Autowired
    private WorkTimeService workTimeService;

    private Employee testEmployee;

    @BeforeEach
    public void setUp() {
        workTimeRepository.deleteAll();
        employeeRepository.deleteAll();
        testEmployee = employeeRepository.save(new Employee(null, "Jan Kowalski"));
    }

    @Test
    public void testCreateOrEndWorkTime_createsNewWorkTime() {
        boolean result = workTimeService.createOrEndWorkTime(testEmployee.getId());

        Assertions.assertFalse(result);
        Assertions.assertTrue(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(testEmployee.getId()).isPresent());
    }

    @Test
    public void testCreateOrEndWorkTime_togglesWorkTime() {
        workTimeService.createOrEndWorkTime(testEmployee.getId());

        boolean result = workTimeService.createOrEndWorkTime(testEmployee.getId());

        Assertions.assertTrue(result);
        Assertions.assertTrue(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(testEmployee.getId()).isEmpty());
    }

    @Test
    public void testIsWorkTimeStarted() {
        Assertions.assertFalse(workTimeService.isWorkTimeStarted(testEmployee.getId()));

        workTimeService.createOrEndWorkTime(testEmployee.getId());

        Assertions.assertTrue(workTimeService.isWorkTimeStarted(testEmployee.getId()));
    }

    @Test
    public void testCreateOrEndWorkTime_employeeNotFound() {
        Assertions.assertThrows(NoSuchElementException.class, () -> {
            workTimeService.createOrEndWorkTime(999L);
        });
    }
}
