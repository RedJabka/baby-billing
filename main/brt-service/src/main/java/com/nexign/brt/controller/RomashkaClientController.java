package com.nexign.brt.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.StatusDto;
import com.nexign.brt.service.RomashkaClientService;

@RestController
public class RomashkaClientController {

    private final RomashkaClientService romashkaClientService;

    @Autowired
    public RomashkaClientController(RomashkaClientService romashkaClientService) {
        this.romashkaClientService = romashkaClientService;
    }
    
    @PostMapping("/clients/client/login")
    public StatusDto getClientByMsisdn(@RequestBody String msisdn) {
        return romashkaClientService.loginClient(msisdn);
    }

    @PutMapping("/clients/client/balance")
    public BigDecimal changeBalance(@RequestBody BalanceDto balanceDto) {
        return romashkaClientService.changeBalance(balanceDto);
    }

    @GetMapping("/clients/client?tarriffId={tariffId}")
    public List<Long> getClientsByTariff(@PathVariable("tariffId") String tariffId) {
        return romashkaClientService.findClientsByTariff(tariffId);
    }
}
