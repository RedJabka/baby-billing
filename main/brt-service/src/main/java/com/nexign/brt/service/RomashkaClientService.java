package com.nexign.brt.service;

import java.math.BigDecimal;
import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.ChangeTariffRequestDto;
import com.nexign.brt.domain.dto.NewAbonentRequestDto;

public interface RomashkaClientService {
    
    StatusMessage checkClientByMsisdn(String msisdn);
    StatusMessage saveClient(NewAbonentRequestDto newAbonentRequestDto);
    StatusMessage changeTariff(ChangeTariffRequestDto changeTariffRequestDto);
    BigDecimal changeBalance(BalanceDto balanceDto);
}
