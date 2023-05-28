package com.my.TimeWork;

import com.my.TimeWork.entity.Employee;
import com.my.TimeWork.repository.EmployeeRepository;
import com.my.TimeWork.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class TimeWorkApplicationTests {
	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private EmployeeService employeeService;

	@Test
	public void testGetAllEmployees() {
		// przygotowanie danych testowych
		List<Employee> employees = new ArrayList<>();
		employees.add(new Employee(1L, "Jan Kowalski"));
		employees.add(new Employee(2L, "Anna Nowak"));

		// konfiguracja zachowania mocka
		Mockito.when(employeeRepository.findAll()).thenReturn(employees);

		// wywołanie metody do testowania
		Iterable<Employee> result = employeeService.getAllEmployee();

		// weryfikacja wyniku
		Assertions.assertEquals(employees, result);
	}

	@Test
	public void testGetEmployeeById() {
		// przygotowanie danych testowych
		Employee employee = new Employee(1L, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
		Mockito.when(employeeRepository.findById(2L)).thenReturn(Optional.empty());

		// wywołanie metody do testowania
		Optional<Employee> result1 = employeeService.getEmployeeById(1L);
		Optional<Employee> result2 = employeeService.getEmployeeById(2L);

		// weryfikacja wyniku
		Assertions.assertEquals(Optional.of(employee), result1);
		Assertions.assertEquals(Optional.empty(), result2);
	}

	@Test
	public void testCreateEmployee() {
		// przygotowanie danych testowych
		Employee employee = new Employee(null, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(employeeRepository.save(employee)).thenReturn(new Employee(1L, "Jan Kowalski"));

		// wywołanie metody do testowania
		Employee result = employeeService.creatEemployee(employee);

		// weryfikacja wyniku
		Assertions.assertEquals(new Employee(1L, "Jan Kowalski"), result);
	}


	@Test
	public void testDeleteEmployee() {
		// przygotowanie danych testowych
		Employee employee = new Employee(1L, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(employeeRepository.existsById(1L)).thenReturn(true);
		Mockito.when(employeeRepository.existsById(2L)).thenReturn(false);

		// wywołanie metody do testowania
		employeeService.deleteEmployee(1L);
		ResponseEntity<Void> result1 = ResponseEntity.noContent().build();

		employeeService.deleteEmployee(2L);
		ResponseEntity<Void> result2 = ResponseEntity.notFound().build();

		// weryfikacja wyniku
		Assertions.assertEquals(result1, result1);
		Assertions.assertEquals(result2, result2);
	}
}
