package com.nexign.hrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.hrs.domain.ClientTariffToHRS;
import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.dto.StatusMessage;
import com.nexign.hrs.service.BillingClientService;

/**
 * Controller for working with clients and there tariffs
 */
@RestController
public class BillingClientController {

    private final BillingClientService billingClientService;

    @Autowired
    public BillingClientController(BillingClientService billingClientService) {
        this.billingClientService = billingClientService;
    }

    @PutMapping(value = "/clients/tariffs")
    public StatusMessage updateClientsTariffs(@RequestBody List<ClientTariffToHRS> billingClients) {
        return billingClientService.updateClientsTariffs(billingClients);
    }

    @GetMapping("/clients/tariffExists")
    public StatusMessage tariffExists(@RequestParam("tariffId") Long tariffId) {
        return billingClientService.checkTariffExists(tariffId);
    }

    @PutMapping("/clients/changeTariffAndCalculateCost")
    public CostFromHRS changeTariff(@RequestBody ClientTariffToHRS clientTariff) {
        return billingClientService.changeTariffAndCalculateCost(clientTariff);
    }

}
