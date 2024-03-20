package org.example.pillarofhope_backend.service_tests;

import jakarta.persistence.EntityNotFoundException;
import org.example.pillarofhope_backend.entity.Medicine;
import org.example.pillarofhope_backend.repository.MedicineRepository;
import org.example.pillarofhope_backend.service.MedicineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@WebMvcTest(MedicineService.class)
class MedicineServiceTest {

    @Autowired
    private MedicineService medicineService;

    @MockBean
    private MedicineRepository medicineRepository;

    @Test
    void uploadMedicine() {
        // Given
        Medicine medicine = new Medicine();
        when(medicineRepository.save(any())).thenReturn(medicine);

        // When
        Medicine uploadedMedicine = medicineService.uploadMedicine(medicine);

        // Then
        assertThat(uploadedMedicine).isEqualTo(medicine);
    }

    @Test
    void getMedicineByName() {
        // Given
        List<Medicine> medicines = new ArrayList<>();
        when(medicineRepository.findAllByMedicineName(anyString())).thenReturn(medicines);

        // When
        List<Medicine> retrievedMedicines = medicineService.getMedicineByName("MedicineName");

        // Then
        assertThat(retrievedMedicines).isEqualTo(medicines);
    }

    @Test
    void getAllMedicines() {
        // Given
        List<Medicine> medicines = new ArrayList<>();
        when(medicineRepository.findAll()).thenReturn(medicines);

        // When
        List<Medicine> retrievedMedicines = medicineService.getAllMedicines();

        // Then
        assertThat(retrievedMedicines).isEqualTo(medicines);
    }

    @Test
    List<Medicine> updateMedicine() {
        // Given
        Medicine existingMedicine = new Medicine();
        when(medicineRepository.findAllByMedicineName(anyString())).thenReturn(updateMedicine());
        when(medicineRepository.save(any())).thenReturn(existingMedicine);

        // When
        Medicine updatedMedicine = medicineService.updateMedicine(existingMedicine);

        // Then
        assertThat(updatedMedicine).isEqualTo(existingMedicine);
        return null;
    }

    @Test
    void getMedicineById() {
        // Given
        Optional<Medicine> optionalMedicine = Optional.of(new Medicine());
        when(medicineRepository.findById(anyInt())).thenReturn(optionalMedicine);

        // When
        Medicine retrievedMedicine = medicineService.getMedicineById(1);

        // Then
        assertThat(retrievedMedicine).isEqualTo(optionalMedicine.get());
    }

    @Test
    void deleteMedicine() {
        // Given
        int medicineId = 1;
        when(medicineRepository.existsById(anyInt())).thenReturn(true);

        // When
        medicineService.deleteMedicine(medicineId);

        // Then
        verify(medicineRepository, times(1)).deleteById(medicineId);
    }

    @Test
    void deleteMedicine_WhenMedicineNotFound_ShouldThrowEntityNotFoundException() {
        // Given
        int medicineId = 1;
        when(medicineRepository.existsById(anyInt())).thenReturn(false);

        // When / Then
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            medicineService.deleteMedicine(medicineId);
        });
    }
}