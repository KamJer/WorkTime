package com.my.TimeWork.service;

import com.my.TimeWork.dto.WorkTimeDto;
import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.repository.WorkTimeRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class WorkTimeService {

    private final WorkTimeRepository workTimeRepository;
    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

//    public WorkTime getWorkTimeById(Long id) {
//        Optional<WorkTime> WorkTimeOptional = workTimeRepository.findById(id);
//        if (!WorkTimeOptional.isPresent()) {
//            throw new NoSuchElementException("Czas pracy o id " + id + " nie istnieje");
//        }
//        WorkTime workTime = WorkTimeOptional.get();
//        return workTime;
//    }
//
//    public WorkTime getNotEndedWorkTime(Long employeeId) {
//        Optional<WorkTime> workTimeOptional = workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId);
//        if (workTimeOptional.isEmpty()) {
//            throw new NoSuchElementException("nie zakończony czas pracy o Employee id " + employeeId + " nie istnieje");
//        }
//        WorkTime workTime = workTimeOptional.get();
//        return workTime;
//    }

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
        employeeRepository.findById(employeeId).ifPresent(employee -> {
            WorkTime workTime = new WorkTime();
            workTime.setEmployee(employee);
            workTime.setBeginningOfWork(LocalDateTime.now());
            workTimeRepository.save(workTime);
        });
        return false;
    }

//    public WorkTime addWorkTime(WorkTimeDto workTimeDto) {
//        WorkTime workTime = modelMapper.map(workTimeDto, WorkTime.class);
//        workTime.setBeginningOfWork(LocalDateTime.now());
//        return workTimeRepository.save(workTime);
//    }
//
//    public WorkTime endStartedWorkTime(Long employeeId) {
//        WorkTime notEndedWorkTime = getNotEndedWorkTime(employeeId);
//        notEndedWorkTime.setEndOfWork(LocalDateTime.now());
//        return notEndedWorkTime;
//    }
//
//    public Boolean deleteWorkTime(Long id) throws DataAccessException {
//        workTimeRepository.deleteById(id);
//        if (workTimeRepository.existsById(id)){
//            return false;
//        }
//        return true;
//    }
}
