package com.shire42.wallet.controller.rest;

import com.shire42.wallet.model.Card;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WalletRest {

    private UUID id;
    private String customerId;
    private String walletName;
    private String description;
    private Double cash;
    private List<CardRest> cards;


}
