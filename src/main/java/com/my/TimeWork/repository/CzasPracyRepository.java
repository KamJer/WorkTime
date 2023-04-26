package com.my.TimeWork.repository;

import com.my.TimeWork.entity.CzasPracy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CzasPracyRepository extends JpaRepository<CzasPracy, Long> {
    Optional<CzasPracy> findByPracownikIdAndEndOfWorkIsNull(Long pracownikId);
}

