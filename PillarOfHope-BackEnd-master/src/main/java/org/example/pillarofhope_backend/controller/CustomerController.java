package org.example.pillarofhope_backend.controller;
import lombok.AllArgsConstructor;
import org.example.pillarofhope_backend.entity.Customer;
import org.example.pillarofhope_backend.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/customers")
public class CustomerController {
    private CustomerService customerService;

    @PostMapping(path = "/register")
    public ResponseEntity registerCustomer(@RequestBody Customer customer){
        try {
            customer.setRole("USER");
            customerService.createCustomer(customer);
            return ResponseEntity.ok().body(customer);
        }catch (Exception exception){
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PostMapping(path = "/register-admin")
    public ResponseEntity registerAdmin(@RequestBody Customer customer){
        try {
            customer.setRole("ADMIN");
            customerService.createCustomer(customer);
            return ResponseEntity.ok().body(customer);
        }catch (Exception exception){
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @GetMapping(path = "/{email}")
    public ResponseEntity getCustomer(@PathVariable String email){
        try {
            Customer customer=customerService.getCustomer(email);
            return ResponseEntity.ok().body(customer);
        }catch (Exception exception){
        System.out.println(exception);
        return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }
    @GetMapping
    public ResponseEntity getAllCustomers(){
        try {
           List<Customer> customers= customerService.getAllCustomers();
            return ResponseEntity.ok().body(customers);
        }catch (Exception exception){
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateCustomers(@PathVariable Long id, @RequestBody Customer updatedCustomer){
        try{
            Customer existingCustomer = customerService.getCustomerById(id);
            if (existingCustomer == null) {
                return ResponseEntity.notFound().build(); // Return 404 if customer with given ID is not found
            }

            // Update the existing customer with the new information
            existingCustomer.setEmail(updatedCustomer.getEmail());
            existingCustomer.setRole(updatedCustomer.getRole());


            // Save the updated medicine
           customerService.uploadCustomer(existingCustomer);

            return ResponseEntity.ok().body(existingCustomer);
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.internalServerError().body(exception.getMessage());
        }
        }

}
