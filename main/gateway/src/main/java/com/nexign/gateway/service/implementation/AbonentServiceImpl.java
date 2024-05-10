package com.nexign.gateway.service.implementation;

import java.math.BigDecimal;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nexign.gateway.client.BRTClient;
import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.ChangeBalanceRequestToBrtDto;
import com.nexign.gateway.domain.dto.PayRequestDto;
import com.nexign.gateway.exceptionHandler.exception.NegativePayMoneyException;
import com.nexign.gateway.service.AbonentService;

@Service
public class AbonentServiceImpl implements AbonentService {

    private BRTClient brtClient;

    @Autowired
    public AbonentServiceImpl(BRTClient brtClient) {
        this.brtClient = brtClient;
    }

    @Override
    public StatusMessage pay(PayRequestDto payRequestDto, Principal principal) {
        if (payRequestDto.getMoney().signum() < 0) {
            throw new NegativePayMoneyException("Money can't be negative");
        }
        BigDecimal balance = brtClient.changeBalance(ChangeBalanceRequestToBrtDto.builder()
            .msisdn(principal.getName())
            .money(payRequestDto.getMoney())
            .build());
        return StatusMessage.builder()
            .status(HttpStatus.OK.value())
            .message("Abonent payed. Balance: " + balance)
            .build();
    }
    
}
