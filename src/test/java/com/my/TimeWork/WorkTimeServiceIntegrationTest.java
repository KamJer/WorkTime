package com.my.TimeWork;

import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.repository.WorkTimeRepository;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.service.WorkTimeService;
import com.my.TimeWork.service.EmployeeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class WorkTimeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private WorkTimeService workTimeService;

    @Autowired
    private WorkTimeRepository workTimeRepository;

    @Test
    public void testAddWorkTime() {
        // given
        WorkTime workTime = new WorkTime();
        workTime.setEmployeeId(1L);
        workTime.setBeginningOfWork(LocalDateTime.now());

        // when
        WorkTime addedWorkTime = workTimeService.addWorkTime(workTime);

        // then
        Assertions.assertThat(addedWorkTime).isNotNull();
        Assertions.assertThat(addedWorkTime.getId()).isNotNull();
        Assertions.assertThat(addedWorkTime.getEmployeeId()).isEqualTo(workTime.getEmployeeId());
        Assertions.assertThat(addedWorkTime.getBeginningOfWork()).isEqualTo(workTime.getBeginningOfWork());
        Assertions.assertThat(workTimeRepository.findById(addedWorkTime.getId())).isPresent();
    }

//    @Test(expected = NoSuchElementException.class)
//    public void testGetNotEndedWorkTimeWhenNoneExists() {
//        // given
//        Long nonExistentEmployeeId = 1L;
//
//        // when
//        WorkTimeService.getNotEndedWorkTime(nonExistentEmployeeId);
//
//        // then
//        // expects NoSuchElementException
//    }

    @Test
    public void testGetNotEndedWorkTime() {
//      given
//      creating Employee
        Employee employee = new Employee();
        employee.setName("Jan Nowak");
        Employee returnedEmployee = employeeRepository.save(employee);
//      creating WorkTime
        WorkTime workTime = new WorkTime();
        workTime.setEmployeeId(returnedEmployee.getId());
        workTime.setBeginningOfWork(LocalDateTime.now());
        workTimeService.addWorkTime(workTime);

        // when
        WorkTime foundWorkTime = workTimeService.getNotEndedWorkTime(workTime.getEmployeeId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // then
        Assertions.assertThat(foundWorkTime).isNotNull();
        Assertions.assertThat(foundWorkTime.getId()).isEqualTo(workTime.getId());
        Assertions.assertThat(foundWorkTime.getEmployeeId()).isEqualTo(workTime.getEmployeeId());
        Assertions.assertThat(foundWorkTime.getBeginningOfWork().format(formatter)).isEqualTo(workTime.getBeginningOfWork().format(formatter));
    }
}
