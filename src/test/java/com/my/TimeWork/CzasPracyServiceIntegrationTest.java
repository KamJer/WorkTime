package com.my.TimeWork;

import com.my.TimeWork.entity.CzasPracy;
import com.my.TimeWork.entity.Pracownik;
import com.my.TimeWork.repository.CzasPracyRepository;
import com.my.TimeWork.repository.PracownikRepository;
import com.my.TimeWork.service.CzasPracyService;
import com.my.TimeWork.service.PracownikService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@SpringBootTest
public class CzasPracyServiceIntegrationTest {

    @Autowired
    private PracownikService pracownikService;
    @Autowired
    private PracownikRepository pracownikRepository;

    @Autowired
    private CzasPracyService czasPracyService;

    @Autowired
    private CzasPracyRepository czasPracyRepository;

    @Test
    public void testAddCzasPracy() {
        // given
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownikId(1L);
        czasPracy.setBeginningOfWork(LocalDateTime.now());

        // when
        CzasPracy addedCzasPracy = czasPracyService.addCzasPracy(czasPracy);

        // then
        Assertions.assertThat(addedCzasPracy).isNotNull();
        Assertions.assertThat(addedCzasPracy.getId()).isNotNull();
        Assertions.assertThat(addedCzasPracy.getPracownikId()).isEqualTo(czasPracy.getPracownikId());
        Assertions.assertThat(addedCzasPracy.getBeginningOfWork()).isEqualTo(czasPracy.getBeginningOfWork());
        Assertions.assertThat(czasPracyRepository.findById(addedCzasPracy.getId())).isPresent();
    }

//    @Test(expected = NoSuchElementException.class)
//    public void testGetNotEndedCzasPracyWhenNoneExists() {
//        // given
//        Long nonExistentPracownikId = 1L;
//
//        // when
//        czasPracyService.getNotEndedCzasPracy(nonExistentPracownikId);
//
//        // then
//        // expects NoSuchElementException
//    }

    @Test
    public void testGetNotEndedCzasPracy() {
//      given
//      creating pracownik
        Pracownik pracownik = new Pracownik();
        pracownik.setName("Jan Nowak");
        Pracownik returnedPracownik = pracownikRepository.save(pracownik);
//      creating czasPracy
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setPracownikId(returnedPracownik.getId());
        czasPracy.setBeginningOfWork(LocalDateTime.now());
        czasPracyService.addCzasPracy(czasPracy);

        // when
        CzasPracy foundCzasPracy = czasPracyService.getNotEndedCzasPracy(czasPracy.getPracownikId());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

        // then
        Assertions.assertThat(foundCzasPracy).isNotNull();
        Assertions.assertThat(foundCzasPracy.getId()).isEqualTo(czasPracy.getId());
        Assertions.assertThat(foundCzasPracy.getPracownikId()).isEqualTo(czasPracy.getPracownikId());
        Assertions.assertThat(foundCzasPracy.getBeginningOfWork().format(formatter)).isEqualTo(czasPracy.getBeginningOfWork().format(formatter));
    }
}
