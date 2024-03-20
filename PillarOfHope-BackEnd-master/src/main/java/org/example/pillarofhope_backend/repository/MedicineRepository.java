package org.example.pillarofhope_backend.repository;
import org.example.pillarofhope_backend.entity.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Integer> {
    List<Medicine> findAllByMedicineName(String medicineName);


}
