package com.shire42.customer.controller.rest;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CustomerRest {

    private UUID id;
    private String name;
    private String lastName;
    private String email;

}
