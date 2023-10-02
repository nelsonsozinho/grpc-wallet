package com.shire42.wallet.controller;

import com.shire42.wallet.controller.rest.WalletRest;
import com.shire42.wallet.exception.WalletAlreadyExistsException;
import com.shire42.wallet.service.WalletService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping(value = "/wallet", produces = {MediaType.APPLICATION_JSON_VALUE})
public class WalletController {

    private WalletService walletService;

    @PostMapping
    public ResponseEntity<?> saveNewWallet(@RequestBody WalletRest walletRest) throws WalletAlreadyExistsException {
        final HttpHeaders responseHeaders = new HttpHeaders();
        final WalletRest walletRestResponse = walletService.createNewWallet(walletRest);
        responseHeaders.add("Location", "/wallet/"+walletRestResponse.getId());
        return new ResponseEntity<>(responseHeaders, HttpStatus.CREATED);
    }

}
