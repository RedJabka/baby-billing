package com.nexign.hrs.service.implementation;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.TransactionForHRS;
import com.nexign.hrs.domain.entity.MonthlyBillingClient;
import com.nexign.hrs.domain.entity.Tariff;
import com.nexign.hrs.repository.MonthlyBillingClientRepository;
import com.nexign.hrs.repository.TariffRepository;
import com.nexign.hrs.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private int month;

    private TariffRepository tariffRepository;

    private MonthlyBillingClientRepository monthlyBillingClientRepository;

    @Autowired
    public TransactionServiceImpl(
            int month, 
            TariffRepository tariffRepository,
            MonthlyBillingClientRepository monthlyBillingClientRepository
    ) {
        this.month = month;
        this.tariffRepository = tariffRepository;
        this.monthlyBillingClientRepository = monthlyBillingClientRepository;
    }

    @Override
    public CostFromHRS calculateCost(TransactionForHRS transaction) {
        Tariff tariff = tariffRepository.getReferenceById(transaction.getTariffId());
        boolean checkForMonthlyTariff = tariff.getBillingMethod().equals("monthly");

        if (checkForMonthlyTariff) {
            MonthlyBillingClient client = monthlyBillingClientRepository.getReferenceById(transaction.getClientId());
            if (client == null) {
                client = MonthlyBillingClient.builder()
                .id(transaction.getClientId())
                .tariff(tariff)
                .build();
                monthlyBillingClientRepository.save(client);    
            }
        }
        if (month != transaction.getStartTime().getDayOfMonth()) {
            month = transaction.getStartTime().getDayOfMonth();
            if (checkForMonthlyTariff) {

            }
        }



        return CostFromHRS.builder()
            .clientId(transaction.getClientId())
            .cost(BigDecimal.valueOf(10))
            .build();
    }
    

}
