package org.example.pillarofhope_backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Medicine;
import org.example.pillarofhope_backend.repository.MedicineRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MedicineService {
    private MedicineRepository medicineRepository;

    public Medicine uploadMedicine(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public List<Medicine> getMedicineByName(String medicineName){
        return medicineRepository.findAllByMedicineName(medicineName);
    }

    public List<Medicine> getAllMedicines(){
        return medicineRepository.findAll();
    }

    public Medicine updateMedicine(Medicine updatedMedicine) {
        // Check if the medicine exists in the database
        Medicine existingMedicine = (Medicine) medicineRepository.findAllByMedicineName(updatedMedicine.getMedicineName());
        if (existingMedicine == null) {
            throw new EntityNotFoundException("Medicine not found with name: " + updatedMedicine.getMedicineName());
        }

        // Save and return the updated medicine
        return medicineRepository.save(existingMedicine);
    }

    public Medicine getMedicineById(Integer id) {
        Optional<Medicine> optionalMedicine = medicineRepository.findById(id);
        return optionalMedicine.orElse(null); // Return null if no medicine found with the given ID
    }

    public void deleteMedicineById(Integer id) {
    }

    public void deleteMedicine(Integer id) {
        // Check if the medicine exists
        if (!medicineRepository.existsById(id)) {
            throw new EntityNotFoundException("Medicine not found with id: " + id);
        }

        // Delete the medicine by its ID
        medicineRepository.deleteById(id);
    }
    }

