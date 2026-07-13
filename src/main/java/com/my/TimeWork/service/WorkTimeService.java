package com.my.TimeWork.service;

import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.repository.WorkTimeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkTimeService {

    private final WorkTimeRepository workTimeRepository;
    private final EmployeeRepository employeeRepository;

    public boolean isWorkTimeStarted(Long employeeId) {
        return workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId).isPresent();
    }

    public boolean createOrEndWorkTime(Long employeeId) {
        Optional<WorkTime> workTimeOptional = workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId);
        return workTimeOptional.map(this::endOldWorkTime).orElseGet(() -> createNewWorkTime(employeeId));
    }

    private boolean endOldWorkTime(WorkTime workTime) {
        workTime.setEndOfWork(LocalDateTime.now());
        workTimeRepository.save(workTime);
        return true;
    }

    private boolean createNewWorkTime(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new NoSuchElementException("Employee with id " + employeeId + " not found"));
        WorkTime workTime = new WorkTime();
        workTime.setEmployee(employee);
        workTime.setBeginningOfWork(LocalDateTime.now());
        workTimeRepository.save(workTime);
        return false;
    }
}
