package com.nexign.brt.service;

import java.math.BigDecimal;
import java.util.List;

import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.StatusDto;
import com.nexign.brt.domain.entity.RomashkaClient;

public interface RomashkaClientService {
    
    StatusDto loginClient(String msisdn);
    RomashkaClient findClientByMsisdn(String msisdn);
    RomashkaClient saveClient(RomashkaClient client);
    RomashkaClient changeTariff(RomashkaClient client);
    BigDecimal changeBalance(BalanceDto balanceDto);
    List<Long> findClientsByTariff(String tariffId);
    List<ClientTariffToHRS> getAllClientsTariffs();
}
