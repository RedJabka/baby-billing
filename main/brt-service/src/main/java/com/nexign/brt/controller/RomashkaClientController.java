package com.nexign.brt.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.ChangeTariffRequestDto;
import com.nexign.brt.domain.dto.NewAbonentRequestDto;
import com.nexign.brt.service.RomashkaClientService;

/**
 * Controller that allows to work with Romashka clients
 * for Gateway API
 */
@RestController
public class RomashkaClientController {

    private final RomashkaClientService romashkaClientService;

    @Autowired
    public RomashkaClientController(RomashkaClientService romashkaClientService) {
        this.romashkaClientService = romashkaClientService;
    }
    
    @GetMapping("/clients/clientExists")
    public StatusMessage checkClientByMsisdn(@RequestParam("msisdn") String msisdn) {
        return romashkaClientService.checkClientByMsisdn(msisdn);
    }

    @PutMapping("/clients/client/balance")
    public BigDecimal changeBalance(@RequestBody BalanceDto balanceDto) {
        return romashkaClientService.changeBalance(balanceDto);
    }

    @PutMapping("/clients/client/tariff")
    public StatusMessage changeTariff(@RequestBody ChangeTariffRequestDto changeTariffRequestDto) {
        return romashkaClientService.changeTariff(changeTariffRequestDto);
    }

    @PostMapping("/clients/client")
    public StatusMessage saveClient(@RequestBody NewAbonentRequestDto newAbonentRequestDto) {
        return romashkaClientService.saveClient(newAbonentRequestDto);
    }
}
