package org.example.pillarofhope_backend.controller;

import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Donation;
import org.example.pillarofhope_backend.service.DonationService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/donations")
public class DonationController {

    private DonationService donationService;

    @GetMapping("/gold")
    public ResponseEntity<List<Donation>> getGoldDonations() {
        List<Donation> goldDonations = donationService.getGoldDonations();
        return ResponseEntity.ok(goldDonations);
    }

    @GetMapping("/silver")
    public ResponseEntity<List<Donation>> getSilverDonations() {
        List<Donation> silverDonations = donationService.getSilverDonations();
        return ResponseEntity.ok(silverDonations);
    }

    @GetMapping("/bronze")
    public ResponseEntity<List<Donation>> getBronzeDonations() {
        List<Donation> bronzeDonations = donationService.getBronzeDonations();
        return ResponseEntity.ok(bronzeDonations);
    }

}