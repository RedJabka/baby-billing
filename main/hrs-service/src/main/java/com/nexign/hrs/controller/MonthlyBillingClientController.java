package com.nexign.hrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.hrs.domain.dto.StatusDto;
import com.nexign.hrs.domain.entity.MonthlyBillingClient;
import com.nexign.hrs.service.MonthlyBillingClientService;

@RestController
public class MonthlyBillingClientController {

    private final MonthlyBillingClientService monthlyBillingClientService;

    @Autowired
    public MonthlyBillingClientController(MonthlyBillingClientService monthlyBillingClientService) {
        this.monthlyBillingClientService = monthlyBillingClientService;
    }

    @PutMapping(value = "/clients/tariffs")
    public StatusDto updateClientsTariffs(@RequestBody List<MonthlyBillingClient> monthlyBillingClients) {
        return monthlyBillingClientService.updateClientsTariffs(monthlyBillingClients);
    }
}
