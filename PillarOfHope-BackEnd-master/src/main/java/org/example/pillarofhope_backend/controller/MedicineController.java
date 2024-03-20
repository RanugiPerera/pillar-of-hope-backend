package org.example.pillarofhope_backend.controller;

import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Medicine;
import org.example.pillarofhope_backend.service.MedicineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/medicine")
public class MedicineController {
    private MedicineService medicineService;

    @PostMapping(path = "/upload")
    public ResponseEntity uploadMedicine(@RequestBody Medicine medicine) {
        try {
            // Validate price
            if (medicine.getPrice() < 0) {
                // Reject negative prices
                return ResponseEntity.badRequest().body("Price cannot be negative");
            }

            String transactionType;

            // Check if the medicine is for donation or sale based on the price
            if (medicine.getPrice() <= 0) {
                // If the price is zero or negative, consider it as a donation
                medicine.setPrice(0);
                transactionType = "Donation";
            } else {
                // If the price is positive, consider it as a sale
                transactionType = "Sale";
            }

            // Save the medicine
            medicineService.uploadMedicine(medicine);

            // Add transactionType field in the response to indicate the type of transaction
            // You can adjust the response format as needed
            Map<String, Object> response = new HashMap<>();
            response.put("medicine", medicine);
            response.put("transactionType", transactionType);
            return ResponseEntity.ok().body(response);
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    // Helper method to update donor's medal based on the quantity donated
    public void updateDonorMedal(Medicine medicine) {
        int quantityDonated = medicine.getQuantity();
        if (quantityDonated >= 1000 && quantityDonated < 2000) {
            medicine.setMedal("Bronze");
        } else if (quantityDonated >= 2000 && quantityDonated < 3000) {
            medicine.setMedal("Silver");
        } else if (quantityDonated >= 3000) {
            medicine.setMedal("Gold");
        }
        // Update or save the medicine with updated medal information
        medicineService.uploadMedicine(medicine);
    }

    @GetMapping(path = "/{medicineName}")
    public ResponseEntity getMedicineByName(@PathVariable String medicineName) {
        try {
            return ResponseEntity.ok().body(medicineService.getMedicineByName(medicineName));
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping(path = "/")
    public ResponseEntity getAllMedicines() {
        try {
            return ResponseEntity.ok().body(medicineService.getAllMedicines());
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity updateMedicines(@PathVariable Integer id, @RequestBody Medicine updatedMedicine) {
        try {
            // Retrieve the existing medicine by its ID
            Medicine existingMedicine = medicineService.getMedicineById(id);
            if (existingMedicine == null) {
                return ResponseEntity.notFound().build(); // Return 404 if medicine with given ID is not found
            }

            // Update the existing medicine with the new information
            existingMedicine.setMedicineName(updatedMedicine.getMedicineName());
            existingMedicine.setContactInfo(updatedMedicine.getContactInfo());
            existingMedicine.setManufacturer(updatedMedicine.getManufacturer());
            existingMedicine.setAvailability(updatedMedicine.isAvailability());
            existingMedicine.setPrice(updatedMedicine.getPrice());


            // Save the updated medicine
            medicineService.uploadMedicine(existingMedicine);

            return ResponseEntity.ok().body(existingMedicine);
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteMedicine(@PathVariable Integer id) {
        try {
            // Retrieve the existing medicine by its ID
            Medicine existingMedicine = medicineService.getMedicineById(id);
            if (existingMedicine == null) {
                return ResponseEntity.notFound().build(); // Return 404 if medicine with given ID is not found
            }

            // Delete the medicine
            medicineService.deleteMedicine(id);

            return ResponseEntity.ok().body("Medicine with ID " + id + " deleted successfully");
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }



}

