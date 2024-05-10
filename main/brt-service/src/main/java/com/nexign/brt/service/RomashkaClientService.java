package com.nexign.brt.service;

import java.math.BigDecimal;
import java.util.List;

import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.ChangeTariffRequestDto;
import com.nexign.brt.domain.dto.NewAbonentRequestDto;
import com.nexign.brt.domain.entity.RomashkaClient;

public interface RomashkaClientService {
    
    StatusMessage checkClientByMsisdn(String msisdn);
    RomashkaClient findClientByMsisdn(String msisdn);
    StatusMessage saveClient(NewAbonentRequestDto newAbonentRequestDto);
    StatusMessage changeTariff(ChangeTariffRequestDto changeTariffRequestDto);
    BigDecimal changeBalance(BalanceDto balanceDto);
    List<Long> findClientsByTariff(Long tariffId);
    List<ClientTariffToHRS> getAllClientsTariffs();
}
