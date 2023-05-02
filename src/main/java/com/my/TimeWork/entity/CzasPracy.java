package com.my.TimeWork.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;

@Entity
@Table(name = "CZAS_PRACY")
public class CzasPracy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "PRACOWNIK_ID")
    private Long pracownikId;

    @Column(name = "BEGINNING_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime beginningOfWork;

    @Column(name = "END_OF_WORK")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime endOfWork;

    // Konstruktor bezparametrowy (dla Springa)
    public CzasPracy() {}

    // Konstruktor z parametrami
    public CzasPracy(Long id, Long pracownikId, LocalDateTime beginningOfWork, LocalDateTime endOfWork) {
        this.id = id;
        this.pracownikId = pracownikId;
        this.beginningOfWork = beginningOfWork;
        this.endOfWork = endOfWork;
    }

    // Gettery i settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPracownikId() {
        return pracownikId;
    }

    public void setPracownikId(Long pracownikId) {
        this.pracownikId = pracownikId;
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

