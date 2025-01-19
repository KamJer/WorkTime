package com.my.TimeWork.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "WORK_TIME")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WorkTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "EMPLOYEE_ID")
    @ManyToOne()
    private Long employeeId;

    @Column(name = "BEGINNING_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginningOfWork;

    @Column(name = "END_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endOfWork;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

