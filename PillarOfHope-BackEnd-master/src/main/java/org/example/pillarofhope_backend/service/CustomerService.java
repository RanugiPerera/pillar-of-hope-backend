package org.example.pillarofhope_backend.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Customer;
import org.example.pillarofhope_backend.entity.Medicine;
import org.example.pillarofhope_backend.repository.CustomerRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CustomerService {
    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Customer createCustomer(Customer customer){
        customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        return customerRepository.save(customer);
    }



    public Customer getCustomer(String email) {
        Optional<Customer>customer= customerRepository.findByEmail(email);
        return unwrapCustomer(customer, email);
    }
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    static Customer unwrapCustomer(Optional<Customer> customer, String email){
        if (customer.isPresent())return customer.get();
        else   {
            throw new EntityNotFoundException();
        }
    }

    public Customer getCustomerById(Long id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        return optionalCustomer.orElse(null);
    }

    public Customer uploadCustomer(Customer existingCustomer) {
        return customerRepository.save(existingCustomer);
    }
}
