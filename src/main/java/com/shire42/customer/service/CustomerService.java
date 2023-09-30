package com.shire42.customer.service;

import com.shire42.customer.controller.rest.CustomerRest;
import com.shire42.customer.exception.CustomerAlreadyExistsException;
import com.shire42.customer.model.Customer;
import com.shire42.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerRest createNewCustomer(CustomerRest customerRest) throws CustomerAlreadyExistsException {

        if(!customerRepository.filterByEmail(customerRest.getEmail()).isEmpty()) {
            throw new CustomerAlreadyExistsException();
        }

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

    public List<CustomerRest> filterCustomersByEmail(final String email) {
        return customerRepository.filterByEmail(email).stream().map(c -> {
            return CustomerRest.builder()
                    .name(c.getName())
                    .id(UUID.fromString(c.getId()))
                    .lastName(c.getLastName())
                    .email(c.getEmail())
                    .build();
        }).toList();
    }

}
