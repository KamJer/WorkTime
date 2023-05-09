package com.my.TimeWork.repository;

import com.my.TimeWork.entity.WorkTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WorkTimeRepository extends JpaRepository<WorkTime, Long> {
    Optional<WorkTime> findByEmployeeIdAndEndOfWorkIsNull(Long employeeId);
}

