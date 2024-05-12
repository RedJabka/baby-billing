package com.nexign.gateway.service;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.ChangeTariffRequestDto;
import com.nexign.gateway.domain.dto.NewAbonentRequestDto;

public interface ManagerService {
    StatusMessage changeTariff(ChangeTariffRequestDto changeTariffRequestDto);
    StatusMessage saveNewAbonent(NewAbonentRequestDto newAbonentRequestDto);
}
