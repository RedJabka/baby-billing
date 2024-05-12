package com.nexign.gateway.client;

import java.math.BigDecimal;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.ChangeBalanceRequestToBrtDto;
import com.nexign.gateway.domain.dto.ChangeTariffRequestDto;
import com.nexign.gateway.domain.dto.NewAbonentRequestToBrtDto;

@FeignClient(name = "brt-service")
public interface BRTClient {
    
    @GetMapping("/clients/clientExists")
    StatusMessage checkClientByMsisdn(@RequestParam("msisdn") String msisdn);

    @PutMapping("/clients/client/tariff")
    StatusMessage changeTariff(@RequestBody ChangeTariffRequestDto changeTariffRequestDto);

    @PostMapping("/clients/client")
    public StatusMessage saveClient(@RequestBody NewAbonentRequestToBrtDto newAbonentRequestDto);

    @PutMapping("/clients/client/balance")
    public BigDecimal changeBalance(@RequestBody ChangeBalanceRequestToBrtDto changeBalanceRequestToBrtDto);
}
