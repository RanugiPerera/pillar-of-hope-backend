package org.example.pillarofhope_backend.service;

import org.example.pillarofhope_backend.entity.Donation;
import org.example.pillarofhope_backend.entity.DonationType;
import org.example.pillarofhope_backend.repository.DonationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    @Autowired
    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    public List<Donation> getGoldDonations() {
        return donationRepository.findAllByDonationType(DonationType.valueOf("Gold"));
    }

    public List<Donation> getSilverDonations() {
        return donationRepository.findAllByDonationType(DonationType.valueOf("Silver"));
    }

    public List<Donation> getBronzeDonations() {
        return donationRepository.findAllByDonationType(DonationType.valueOf("Bronze"));
    }}