package com.shire42.wallet.service;

import com.shire42.wallet.controller.rest.CardRest;
import com.shire42.wallet.controller.rest.WalletRest;
import com.shire42.wallet.exception.WalletAlreadyExistsException;
import com.shire42.wallet.exception.WalletNotFoundException;
import com.shire42.wallet.model.Card;
import com.shire42.wallet.model.Wallet;
import com.shire42.wallet.repository.WalletRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class WalletService {

    private final WalletRepository customerRepository;

    public WalletRest createNewWallet(WalletRest customerRest) throws WalletAlreadyExistsException {
        final Wallet wallet = new Wallet();

        wallet.setCustomerId(customerRest.getCustomerId());
        wallet.setWalletName(customerRest.getWalletName());
        wallet.setCash(Double.parseDouble("10000.00"));
        wallet.setDescription(customerRest.getDescription());
        wallet.setCustomerId(customerRest.getCustomerId());
        wallet.setId(UUID.randomUUID().toString());


        wallet.setCards(customerRest.getCards().stream().map(c -> Card.builder()
                .id(UUID.randomUUID().toString())
                .cvc(c.getCvc())
                .expire(c.getExpire())
                .number(c.getNumber())
                .holderName(c.getHolderName())
                .build()
            ).toList()
        );

        customerRepository.save(wallet);

        customerRest.setId(UUID.fromString(wallet.getId()));

        return customerRest;
    }

    public WalletRest findWalletById(String id) throws WalletNotFoundException {
        final Wallet wallet = customerRepository.getById(id);
        return WalletRest.builder()
                .walletName(wallet.getWalletName())
                .cash(wallet.getCash())
                .customerId(wallet.getCustomerId())
                .description(wallet.getDescription())
                .walletName(wallet.getWalletName())
                .id(UUID.fromString(wallet.getId()))
                .cards(
                        wallet.getCards().stream().map(c -> CardRest.builder()
                                .number(c.getNumber())
                                .expire(c.getExpire())
                                .holderName(c.getHolderName())
                                .cvc(c.getCvc())
                                .id(UUID.fromString(c.getId()))
                                .build()
                        ).toList()
                )
                .build();
    }

}
