package com.nexign.brt.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.CostFromHRS;
import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.TransactionForHRS;

/**
 * Feign client for communication with HRS
 */
@FeignClient(name = "hrs-service")
public interface HRSClient {
    
    @GetMapping("/tariffs")
    String getTariffs();

    @PostMapping("/calculateCost")
    CostFromHRS calculateCost(@RequestBody TransactionForHRS transactionForHRS);

    @PutMapping("/clients/tariffs")
    StatusMessage updateClientsTariffs(@RequestBody List<ClientTariffToHRS> clientTariffToHRS);

    @PostMapping("/calculateCostForMonthlyTariff")
    public List<CostFromHRS> calculateCostForMonthlyTariff();

    @GetMapping("/clients/tariffExists")
    public StatusMessage tariffExists(@RequestParam("tariffId") Long tariffId);

    @PutMapping("/clients/changeTariffAndCalculateCost")
    public CostFromHRS changeTariff(@RequestBody ClientTariffToHRS clientTariff);

    @GetMapping("/health")
    public String health();
}
