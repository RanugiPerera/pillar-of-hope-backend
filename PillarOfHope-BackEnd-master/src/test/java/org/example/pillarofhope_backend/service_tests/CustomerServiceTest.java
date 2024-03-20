package org.example.pillarofhope_backend.service_tests;

import jakarta.persistence.EntityNotFoundException;
import org.example.pillarofhope_backend.entity.Customer;
import org.example.pillarofhope_backend.repository.CustomerRepository;
import org.example.pillarofhope_backend.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest(CustomerService.class)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void getCustomer_ShouldReturnCustomer_WhenExists() {
        // Given
        Customer customer = new Customer();
        customer.setEmail("test@example.com");
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.of(customer));

        // When
        Customer retrievedCustomer = customerService.getCustomer("test@example.com");

        // Then
        assertThat(retrievedCustomer).isEqualTo(customer);
    }

    @Test
    void getCustomer_ShouldThrowEntityNotFoundException_WhenNotExists() {
        // Given
        when(customerRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        // When / Then
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            customerService.getCustomer("nonexistent@example.com");
        });
    }
}