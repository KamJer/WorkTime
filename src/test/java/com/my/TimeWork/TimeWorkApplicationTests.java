package com.my.TimeWork;

import com.my.TimeWork.dto.EmployeeDto;
import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;

@ExtendWith(MockitoExtension.class)
class TimeWorkApplicationTests {

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	public void testGetAllEmployees() {
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1L, "Jan Kowalski"));
		employees.add(new Employee(2L, "Anna Nowak"));

		Mockito.when(employeeRepository.findAll()).thenReturn(employees);

		List<Employee> result = employeeService.getAllEmployee();

		Assertions.assertEquals(employees, result);
		Mockito.verify(employeeRepository, Mockito.times(1)).findAll();
	}

	@Test
	public void testGetEmployeeById() {
		Employee employee = new Employee(1L, "Jan Kowalski");

		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

		Optional<Employee> result1 = employeeService.getEmployeeById(1L);
		Optional<Employee> result2 = employeeService.getEmployeeById(2L);

		Assertions.assertEquals(Optional.of(employee), result1);
		Assertions.assertTrue(result2.isEmpty());
	}

	@Test
	public void testCreateEmployee() {
		EmployeeDto employeeDto = new EmployeeDto(null, "Jan Kowalski");
		Employee mappedEmployee = new Employee(null, "Jan Kowalski");
		Employee savedEmployee = new Employee(1L, "Jan Kowalski");

		Mockito.when(modelMapper.map(employeeDto, Employee.class)).thenReturn(mappedEmployee);
		Mockito.when(employeeRepository.save(mappedEmployee)).thenReturn(savedEmployee);

		Employee result = employeeService.createEmployee(employeeDto);

		Assertions.assertEquals(savedEmployee, result);
		Assertions.assertEquals("Jan Kowalski", result.getName());
		Mockito.verify(employeeRepository, Mockito.times(1)).save(mappedEmployee);
	}

	@Test
	public void testDeleteEmployee() {
		Long id = 1L;

		employeeService.deleteEmployee(id);

		Mockito.verify(employeeRepository, Mockito.times(1)).deleteById(id);
	}
}
