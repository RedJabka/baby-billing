package com.nexign.hrs.service;

import java.util.List;

import com.nexign.hrs.domain.dto.StatusDto;
import com.nexign.hrs.domain.entity.MonthlyBillingClient;

public interface MonthlyBillingClientService {
    
    StatusDto updateClientsTariffs(List<MonthlyBillingClient> monthlyBillingClients);
}
