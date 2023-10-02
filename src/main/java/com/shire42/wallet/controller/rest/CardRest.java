package com.shire42.wallet.controller.rest;

import lombok.Data;

import java.util.UUID;

@Data
public class CardRest {

    private UUID id;
    private String holderName;
    private String number;
    private String expire;
    private String cvc;

}
