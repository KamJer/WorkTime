package com.my.TimeWork;

import com.my.TimeWork.entity.CzasPracy;
import com.my.TimeWork.repository.CzasPracyRepository;
import com.my.TimeWork.service.CzasPracyService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class CzasPracyServiceTest {


    @Mock
    private CzasPracyRepository czasPracyRepository;

    @InjectMocks
    private CzasPracyService czasPracyService;

    @Test
    public void testGetCzasPracyById() {
        Long id = 1L;
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setId(id);
        Mockito.when(czasPracyRepository.findById(id)).thenReturn(Optional.of(czasPracy));

        CzasPracy result = czasPracyService.getCzasPracyById(id);

        Assertions.assertEquals(id, result.getId());
        Mockito.verify(czasPracyRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void testGetCzasPracyById_notFound() {
        Long id = 1L;
        Mockito.when(czasPracyRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            czasPracyService.getCzasPracyById(id);
        });

        Mockito.verify(czasPracyRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void addCzasPracyShouldReturnSavedCzasPracy() {
        // given
        CzasPracy czasPracy = new CzasPracy();
        czasPracy.setBeginningOfWork(LocalDateTime.now());

        Mockito.when(czasPracyRepository.save(czasPracy)).thenReturn(czasPracy);

        // when
        CzasPracy savedCzasPracy = czasPracyService.addCzasPracy(czasPracy);

        // then
        Assertions.assertEquals(czasPracy, savedCzasPracy);
        Mockito.verify(czasPracyRepository,  Mockito.times(1)).save(czasPracy);
    }
}
