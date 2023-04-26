package com.my.TimeWork;

import com.my.TimeWork.entity.Pracownik;
import com.my.TimeWork.repository.PracownikRepository;
import com.my.TimeWork.service.PracownikService;
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
	private PracownikRepository pracownikRepository;

	@InjectMocks
	private PracownikService pracownikService;

	@Test
	public void testGetAllPracownicy() {
		// przygotowanie danych testowych
		List<Pracownik> pracownicy = new ArrayList<>();
		pracownicy.add(new Pracownik(1L, "Jan Kowalski"));
		pracownicy.add(new Pracownik(2L, "Anna Nowak"));

		// konfiguracja zachowania mocka
		Mockito.when(pracownikRepository.findAll()).thenReturn(pracownicy);

		// wywołanie metody do testowania
		Iterable<Pracownik> result = pracownikService.getAllPracownicy();

		// weryfikacja wyniku
		Assertions.assertEquals(pracownicy, result);
	}

	@Test
	public void testGetPracownikById() {
		// przygotowanie danych testowych
		Pracownik pracownik = new Pracownik(1L, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(pracownikRepository.findById(1L)).thenReturn(Optional.of(pracownik));
		Mockito.when(pracownikRepository.findById(2L)).thenReturn(Optional.empty());

		// wywołanie metody do testowania
		Optional<Pracownik> result1 = pracownikService.getPracownikById(1L);
		Optional<Pracownik> result2 = pracownikService.getPracownikById(2L);

		// weryfikacja wyniku
		Assertions.assertEquals(Optional.of(pracownik), result1);
		Assertions.assertEquals(Optional.empty(), result2);
	}

	@Test
	public void testCreatePracownik() {
		// przygotowanie danych testowych
		Pracownik pracownik = new Pracownik(null, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(pracownikRepository.save(pracownik)).thenReturn(new Pracownik(1L, "Jan Kowalski"));

		// wywołanie metody do testowania
		Pracownik result = pracownikService.createPracownik(pracownik);

		// weryfikacja wyniku
		Assertions.assertEquals(new Pracownik(1L, "Jan Kowalski"), result);
	}


	@Test
	public void testDeletePracownik() {
		// przygotowanie danych testowych
		Pracownik pracownik = new Pracownik(1L, "Jan Kowalski");

		// konfiguracja zachowania mocka
		Mockito.when(pracownikRepository.existsById(1L)).thenReturn(true);
		Mockito.when(pracownikRepository.existsById(2L)).thenReturn(false);

		// wywołanie metody do testowania
		pracownikService.deletePracownik(1L);
		ResponseEntity<Void> result1 = ResponseEntity.noContent().build();

		pracownikService.deletePracownik(2L);
		ResponseEntity<Void> result2 = ResponseEntity.notFound().build();

		// weryfikacja wyniku
		Assertions.assertEquals(result1, result1);
		Assertions.assertEquals(result2, result2);
	}
}
