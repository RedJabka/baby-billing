package com.nexign.hrs.service.implementation;

import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.TransactionForHRS;
import com.nexign.hrs.domain.entity.BillingClient;
import com.nexign.hrs.domain.entity.Tariff;
import com.nexign.hrs.repository.CallRateRepository;
import com.nexign.hrs.repository.ClientRemainingResourceRepository;
import com.nexign.hrs.repository.BillingClientRepository;
import com.nexign.hrs.repository.TariffRepository;
import com.nexign.hrs.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

    private TariffRepository tariffRepository;

    private BillingClientRepository billingClientRepository;

    private final CallRateRepository callRateRepository;

    private final ClientRemainingResourceRepository clientRemainingResourceRepository;

    @Autowired
    public TransactionServiceImpl(
            TariffRepository tariffRepository,
            BillingClientRepository billingClientRepository,
            CallRateRepository callRateRepository,
            ClientRemainingResourceRepository clientRemainingResourceRepository) {
        this.tariffRepository = tariffRepository;
        this.billingClientRepository = billingClientRepository;
        this.callRateRepository = callRateRepository;
        this.clientRemainingResourceRepository = clientRemainingResourceRepository;
    }

    @Override
    public CostFromHRS calculateCost(TransactionForHRS transaction) {
        Tariff tariff = tariffRepository.findById(transaction.getTariffId()).orElseThrow();
        boolean checkForMonthlyTariff = tariff.getBillingMethod().equals("monthly");

        BigDecimal cost = BigDecimal.valueOf(0);

        BigDecimal durationMinutes = BigDecimal.valueOf(
                Math.ceil(
                        (transaction.getEndTime().toEpochSecond(ZoneOffset.UTC) -
                                transaction.getStartTime().toEpochSecond(ZoneOffset.UTC)) / 60.0));

        BillingClient client = billingClientRepository.findById(transaction.getClientId()).orElseThrow();
        if (checkForMonthlyTariff) {
            client.getClientRemainingResources().stream()
                    .forEach(resource -> {
                        if (resource.getRemainingResourceAmount().compareTo(durationMinutes) >= 0) {
                            resource.setRemainingResourceAmount(
                                    resource.getRemainingResourceAmount().subtract(durationMinutes));
                        } else {
                            BigDecimal remainingDurationMinutes = durationMinutes
                                    .subtract(resource.getRemainingResourceAmount());
                            resource.setRemainingResourceAmount(BigDecimal.valueOf(0));
                            Tariff classicTariff = tariffRepository.findByTariffName("Classic");

                            cost.add(calculateCostForPerMinute(remainingDurationMinutes, classicTariff, transaction));
                        }
                        clientRemainingResourceRepository.save(resource);
                    });
        } else {
            cost.add(calculateCostForPerMinute(durationMinutes, tariff, transaction));
        }

        return CostFromHRS.builder()
                .clientId(transaction.getClientId())
                .cost(cost)
                .build();
    }

    private BigDecimal calculateCostForPerMinute(BigDecimal durationMinutes, Tariff tariff,
            TransactionForHRS transaction) {
        BigDecimal ratePerMinute = callRateRepository.findByTariffIdAndCallTypeAndWithinTheNetworkCheck(
                tariff.getId(),
                transaction.getCallType(),
                transaction.getSubscriberConnectedCheck())
                .getRatePerMinute();
        return durationMinutes.multiply(ratePerMinute);
    }

    @Override
    public List<CostFromHRS> calculateCostForMonthlyTariff() {
        List<CostFromHRS> costs = new ArrayList<>();
        List<BillingClient> billingClients = billingClientRepository.findAllWhereBillingMethodLike("monthly");
        for (BillingClient client : billingClients) {
            CostFromHRS cost = CostFromHRS.builder()
                    .clientId(client.getId())
                    .cost(client.getTariff().getMonthlyCost())
                    .build();
            costs.add(cost);

            client.getClientRemainingResources().stream()
                    .forEach(resource -> resource
                            .setRemainingResourceAmount(resource.getResource().getIncludedAmount()));

            billingClientRepository.save(client);
        }

        return costs;
    }

}
