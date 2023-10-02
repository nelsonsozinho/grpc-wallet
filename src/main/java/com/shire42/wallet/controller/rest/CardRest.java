package com.shire42.wallet.controller.rest;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CardRest {

    private UUID id;
    private String holderName;
    private String number;
    private String expire;
    private String cvc;

}
