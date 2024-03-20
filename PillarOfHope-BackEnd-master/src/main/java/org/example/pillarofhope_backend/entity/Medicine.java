package org.example.pillarofhope_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String medicineName;

    @Column
    private int quantity;

    @Column
    private String  manufacturer;

    @Column
    private String contactInfo;

    @Column
    private boolean availability;

    @Column
    private int createdBy;

    @Column
    private double price;

    @Column
    private String medal;

    @Column
    private String donation;
}
