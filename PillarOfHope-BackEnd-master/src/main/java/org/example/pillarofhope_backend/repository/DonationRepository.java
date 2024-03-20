package org.example.pillarofhope_backend.repository;

import org.example.pillarofhope_backend.entity.Donation;
import org.example.pillarofhope_backend.entity.DonationType;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByDonationType(DonationType donationType);
}