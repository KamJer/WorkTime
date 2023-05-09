package com.my.TimeWork.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "WORK_TIME")
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMPLOYEE_ID")
    private Long employeeId;

    @Column(name = "BEGINNING_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginningOfWork;

    @Column(name = "END_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endOfWork;

    public WorkTime() {}

    public WorkTime(Long id, Long EmployeeId, LocalDateTime beginningOfWork, LocalDateTime endOfWork) {
        this.id = id;
        this.employeeId = EmployeeId;
        this.beginningOfWork = beginningOfWork;
        this.endOfWork = endOfWork;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long EmployeeId) {
        this.employeeId = EmployeeId;
    }

    public LocalDateTime getBeginningOfWork() {
        return beginningOfWork;
    }

    public void setBeginningOfWork(LocalDateTime beginningOfWork) {
        this.beginningOfWork = beginningOfWork;
    }

    public LocalDateTime getEndOfWork() {
        return endOfWork;
    }

    public void setEndOfWork(LocalDateTime endOfWork) {
        this.endOfWork = endOfWork;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

