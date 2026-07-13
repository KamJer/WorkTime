package com.my.TimeWork;

import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.entity.WorkTime;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.repository.WorkTimeRepository;
import com.my.TimeWork.service.WorkTimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class WorkTimeServiceTest {

    @Mock
    private WorkTimeRepository workTimeRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private WorkTimeService workTimeService;

    @Test
    public void testIsWorkTimeStarted_returnsTrue() {
        Long employeeId = 1L;
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        Mockito.when(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId))
                .thenReturn(Optional.of(workTime));

        boolean result = workTimeService.isWorkTimeStarted(employeeId);

        Assertions.assertTrue(result);
    }

    @Test
    public void testIsWorkTimeStarted_returnsFalse() {
        Long employeeId = 1L;
        Mockito.when(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId))
                .thenReturn(Optional.empty());

        boolean result = workTimeService.isWorkTimeStarted(employeeId);

        Assertions.assertFalse(result);
    }

    @Test
    public void testCreateOrEndWorkTime_endsExistingWorkTime() {
        Long employeeId = 1L;
        WorkTime workTime = new WorkTime();
        workTime.setId(1L);
        workTime.setBeginningOfWork(java.time.LocalDateTime.now().minusHours(8));
        Mockito.when(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId))
                .thenReturn(Optional.of(workTime));

        boolean result = workTimeService.createOrEndWorkTime(employeeId);

        Assertions.assertTrue(result);
        Assertions.assertNotNull(workTime.getEndOfWork());
        Mockito.verify(workTimeRepository, Mockito.times(1)).save(workTime);
    }

    @Test
    public void testCreateOrEndWorkTime_createsNewWorkTime() {
        Long employeeId = 1L;
        Employee employee = new Employee(employeeId, "Jan Kowalski");
        Mockito.when(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId))
                .thenReturn(Optional.empty());
        Mockito.when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.of(employee));

        boolean result = workTimeService.createOrEndWorkTime(employeeId);

        Assertions.assertFalse(result);
        Mockito.verify(workTimeRepository, Mockito.times(1)).save(Mockito.any(WorkTime.class));
    }

    @Test
    public void testCreateOrEndWorkTime_employeeNotFound_throwsException() {
        Long employeeId = 99L;
        Mockito.when(workTimeRepository.findByEmployeeIdAndEndOfWorkIsNull(employeeId))
                .thenReturn(Optional.empty());
        Mockito.when(employeeRepository.findById(employeeId))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> {
            workTimeService.createOrEndWorkTime(employeeId);
        });
    }
}
