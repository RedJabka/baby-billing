package com.nexign.brt.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.CostFromHRS;
import com.nexign.brt.domain.TransactionForHRS;
import com.nexign.brt.domain.dto.StatusDto;

@FeignClient(name = "hrs-service")
public interface HRSClient {
    
    @GetMapping("/tariffs")
    String getTariffs();

    @PostMapping("/calculateCost")
    CostFromHRS calculateCost(@RequestBody TransactionForHRS transactionForHRS);

    @PutMapping("/clients/tariffs")
    StatusDto updateClientsTariffs(@RequestBody List<ClientTariffToHRS> clientTariffToHRS);
}
