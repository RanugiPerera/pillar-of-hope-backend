package org.example.pillarofhope_backend;

import org.example.pillarofhope_backend.controller.CustomerController;
import org.example.pillarofhope_backend.controller.MedicineController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest
class SmokeTest {

    @Autowired
    private CustomerController customerController;
    @Autowired
    private MedicineController medicineController;

    @Test
    void contextLoads() throws Exception {
        assertThat(customerController).isNotNull();
        assertThat(medicineController).isNotNull();
    }
}


