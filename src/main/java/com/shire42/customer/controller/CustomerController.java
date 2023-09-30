package com.shire42.customer.controller;

import com.shire42.customer.controller.rest.CustomerRest;
import com.shire42.customer.exception.CustomerAlreadyExistsException;
import com.shire42.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/customer", produces = {MediaType.APPLICATION_JSON_VALUE})
public class CustomerController {

    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<?> saveNewCustomer(@RequestBody CustomerRest customerRest) throws CustomerAlreadyExistsException {
        final HttpHeaders responseHeaders = new HttpHeaders();
        final CustomerRest customer = customerService.createNewCustomer(customerRest);
        responseHeaders.add("Location", "/customer/"+customer.getId());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerRest> getCustomerById(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @GetMapping()
    public ResponseEntity<List<CustomerRest>> filterByEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(customerService.filterCustomersByEmail(email));
    }

}
