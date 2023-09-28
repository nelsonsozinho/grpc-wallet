package com.shire42.customer.service;

import com.shire42.customer.controller.rest.CustomerRest;
import com.shire42.customer.model.Customer;
import com.shire42.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerRest createNewCustomer(CustomerRest customerRest) {

        final Customer customer = new Customer();
        customer.setName(customerRest.getName());
        customer.setLastName(customerRest.getLastName());
        customer.setEmail(customerRest.getEmail());
        customer.setId(UUID.randomUUID().toString());

        customerRepository.save(customer);
        customerRest.setId(UUID.fromString(customer.getId()));

        return customerRest;
    }

    public CustomerRest findCustomerById(String id) {
        final Customer customer = customerRepository.getById(id);
        return CustomerRest.builder()
                .name(customer.getName())
                .email(customer.getEmail())
                .lastName(customer.getLastName())
                .id(UUID.fromString(customer.getId()))
                .build();
    }

}
