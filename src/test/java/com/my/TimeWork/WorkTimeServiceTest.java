package com.my.TimeWork;

import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.WorkTimeRepository;
import com.my.TimeWork.service.WorkTimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;

@SpringBootTest
public class WorkTimeServiceTest {


    @Mock
    private WorkTimeRepository workTimeRepository;

    @InjectMocks
    private WorkTimeService workTimeService;

    @Test
    public void testGetWorkTimeById() {
        Long id = 1L;
        WorkTime workTime = new WorkTime();
        workTime.setId(id);
        Mockito.when(workTimeRepository.findById(id)).thenReturn(Optional.of(workTime));

        WorkTime result = workTimeService.getWorkTimeById(id);

        Assertions.assertEquals(id, result.getId());
        Mockito.verify(workTimeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void testGetWorkTimeById_notFound() {
        Long id = 1L;
        Mockito.when(workTimeRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            workTimeService.getWorkTimeById(id);
        });

        Mockito.verify(workTimeRepository, Mockito.times(1)).findById(id);
    }

    @Test
    public void addWorkTimeShouldReturnSavedWorkTime() {
        // given
        WorkTime workTime = new WorkTime();
        workTime.setBeginningOfWork(LocalDateTime.now());

        Mockito.when(workTimeRepository.save(workTime)).thenReturn(workTime);

        // when
        WorkTime savedWorkTime = workTimeService.addWorkTime(workTime);

        // then
        Assertions.assertEquals(workTime, savedWorkTime);
        Mockito.verify(workTimeRepository,  Mockito.times(1)).save(workTime);
    }
}
