package com.nexign.hrs.service;

import java.util.List;

import com.nexign.hrs.domain.ClientTariffToHRS;
import com.nexign.hrs.domain.dto.StatusMessage;

public interface BillingClientService {
    
    StatusMessage updateClientsTariffs(List<ClientTariffToHRS> billingClients);

    StatusMessage checkTariffExists(Long tariffId);
}
