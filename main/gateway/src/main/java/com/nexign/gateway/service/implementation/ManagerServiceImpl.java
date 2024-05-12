package com.nexign.gateway.service.implementation;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.gateway.client.BRTClient;
import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.ChangeTariffRequestDto;
import com.nexign.gateway.domain.dto.NewAbonentRequestDto;
import com.nexign.gateway.domain.dto.NewAbonentRequestToBrtDto;
import com.nexign.gateway.service.ManagerService;

@Service
public class ManagerServiceImpl implements ManagerService {

    private BRTClient brtClient;

    @Autowired
    public ManagerServiceImpl(BRTClient brtClient) {
        this.brtClient = brtClient;
    }
    @Override
    public StatusMessage changeTariff(ChangeTariffRequestDto changeTariffRequestDto) {
        return brtClient.changeTariff(changeTariffRequestDto);
    }

    @Override
    public StatusMessage saveNewAbonent(NewAbonentRequestDto newAbonentRequestDto) {
        return brtClient.saveClient(NewAbonentRequestToBrtDto.builder()
            .msisdn(newAbonentRequestDto.getMsisdn())
            .tariffId(newAbonentRequestDto.getTariffId())
            .money(BigDecimal.valueOf(100))
            .build());
    }
    
}
