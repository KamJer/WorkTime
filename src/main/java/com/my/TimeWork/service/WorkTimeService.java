package com.my.TimeWork.service;

import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.WorkTimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class WorkTimeService {
    @Autowired
    private WorkTimeRepository workTimeRepository;

    public WorkTime getWorkTimeById(Long id) {
        Optional<WorkTime> WorkTimeOptional = workTimeRepository.findById(id);
        if (!WorkTimeOptional.isPresent()) {
            throw new NoSuchElementException("Czas pracy o id " + id + " nie istnieje");
        }
        WorkTime workTime = WorkTimeOptional.get();
        return workTime;
    }

    public WorkTime getNotEndedWorkTime(Long employeeId) {
        Optional<WorkTime> WorkTimeOptional = workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId);
        if (!WorkTimeOptional.isPresent()) {
            throw new NoSuchElementException("nie zakończony czas pracy o Employee id " + employeeId + " nie istnieje");
        }
        WorkTime workTime = WorkTimeOptional.get();
        return workTime;
    }

    public WorkTime addWorkTime(WorkTime workTime) {
        return workTimeRepository.save(workTime);
    }

    public Boolean deleteWorkTime(Long id) throws DataAccessException {
        workTimeRepository.deleteById(id);
        if (workTimeRepository.existsById(id)){
            return false;
        }
        return true;
    }
}
