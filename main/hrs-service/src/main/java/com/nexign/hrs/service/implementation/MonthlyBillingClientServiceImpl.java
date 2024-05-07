package com.nexign.hrs.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.hrs.domain.dto.StatusDto;
import com.nexign.hrs.domain.entity.MonthlyBillingClient;
import com.nexign.hrs.repository.MonthlyBillingClientRepository;
import com.nexign.hrs.service.MonthlyBillingClientService;

@Service
public class MonthlyBillingClientServiceImpl implements MonthlyBillingClientService {

    private final MonthlyBillingClientRepository monthlyBillingClientRepository;

    @Autowired
    public MonthlyBillingClientServiceImpl(MonthlyBillingClientRepository monthlyBillingClientRepository) {
        this.monthlyBillingClientRepository = monthlyBillingClientRepository;
    }

    @Override
    public StatusDto updateClientsTariffs(List<MonthlyBillingClient> monthlyBillingClients) {
        List<MonthlyBillingClient> updatedClients = monthlyBillingClientRepository.saveAll(monthlyBillingClients);
        return StatusDto.builder()
                .status(200)
                .message(updatedClients.size() + " clients updated")
                .build();
    }
    
}
