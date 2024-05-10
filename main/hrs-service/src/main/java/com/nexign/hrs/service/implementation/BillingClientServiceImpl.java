package com.nexign.hrs.service.implementation;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nexign.hrs.domain.ClientTariffToHRS;
import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.dto.StatusMessage;
import com.nexign.hrs.domain.entity.BillingClient;
import com.nexign.hrs.domain.entity.ClientRemainingResource;
import com.nexign.hrs.domain.entity.Tariff;
import com.nexign.hrs.repository.BillingClientRepository;
import com.nexign.hrs.repository.ClientRemainingResourceRepository;
import com.nexign.hrs.repository.TariffRepository;
import com.nexign.hrs.service.BillingClientService;

@Service
public class BillingClientServiceImpl implements BillingClientService {

    private final BillingClientRepository billingClientRepository;

    private final ClientRemainingResourceRepository clientRemainingResourceRepository;

    private final TariffRepository tariffRepository;

    @Autowired
    public BillingClientServiceImpl(
            BillingClientRepository billingClientRepository,
            TariffRepository tariffRepository,
            ClientRemainingResourceRepository clientRemainingResourceRepository) {
        this.billingClientRepository = billingClientRepository;
        this.tariffRepository = tariffRepository;
        this.clientRemainingResourceRepository = clientRemainingResourceRepository;
    }

    @Override
    public CostFromHRS changeTariffAndCalculateCost(ClientTariffToHRS clientTariff) {
        Tariff oldTariff = tariffRepository.getReferenceById(clientTariff.getTariffId());
        BigDecimal cost = BigDecimal.valueOf(0);
        if (oldTariff.getBillingMethod().equals("monthly")) {
            cost.add(oldTariff.getMonthlyCost());
        }
        changeTariff(clientTariff);
        return CostFromHRS.builder()
                .clientId(clientTariff.getClientId())
                .cost(cost)
                .build();
    }

    @Override
    public StatusMessage updateClientsTariffs(List<ClientTariffToHRS> clientTariffList) {
        for (ClientTariffToHRS clientTariff : clientTariffList) {
            changeTariff(clientTariff);
        }

        return StatusMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Tariffs and clients updated")
                .build();
    }

    private void changeTariff(ClientTariffToHRS clientTariff) {
        Tariff newTariff = tariffRepository.getReferenceById(clientTariff.getTariffId());
        billingClientRepository.findById(clientTariff.getClientId())
            .ifPresentOrElse(client -> {
                if (!clientTariff.getTariffId().equals(client.getTariff().getId())) {
                    clientRemainingResourceRepository.deleteAll(client.getClientRemainingResources());

                    List<ClientRemainingResource> clientRemainingResources = new ArrayList<>();
                    if (newTariff.getBillingMethod().equals("monthly")) {
                        newTariff.getIncludedResource().forEach(resource -> {
                            ClientRemainingResource clientRemainingResource = clientRemainingResourceRepository
                                    .save(ClientRemainingResource.builder()
                                            .client(client)
                                            .resource(resource)
                                            .remainingResourceAmount(resource.getIncludedAmount())
                                            .build());
                            clientRemainingResources.add(clientRemainingResource);
                        });
                    }
                    client.setClientRemainingResources(clientRemainingResources);
                    client.setTariff(newTariff);
                    billingClientRepository.save(client);
                }
            }, () -> {
                BillingClient client = billingClientRepository.save(BillingClient.builder()
                        .id(clientTariff.getClientId())
                        .tariff(newTariff)
                        .build());
                newTariff.getIncludedResource().forEach(resource -> {
                    clientRemainingResourceRepository
                            .save(ClientRemainingResource.builder()
                                    .client(client)
                                    .resource(resource)
                                    .remainingResourceAmount(resource.getIncludedAmount())
                                    .build());
                });
            });
    }

    @Override
    public StatusMessage checkTariffExists(Long tariffId) {
        if (tariffRepository.findById(tariffId).isEmpty()) {
            return StatusMessage.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .message("Tariff not found")
                    .build();
        }
        return StatusMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Tariff exists")
                .build();
    }

}
