package com.nexign.brt.service.implementation;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.brt.client.HRSClient;
import com.nexign.brt.component.Month;
import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.CostFromHRS;
import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.TransactionForHRS;
import com.nexign.brt.domain.entity.RomashkaClient;
import com.nexign.brt.domain.enums.CallType;
import com.nexign.brt.repository.RomashkaClientRepository;
import com.nexign.brt.service.CDRHandlerService;

@Service
public class CDRHandlerServiceImpl implements CDRHandlerService {

    private final RomashkaClientRepository romashkaClientRepository;
    private final HRSClient hrsClient;
    private final Month month;

    @Autowired
    public CDRHandlerServiceImpl(
            RomashkaClientRepository romashkaClientRepository,
            HRSClient hrsClient,
            Month month) {
        this.romashkaClientRepository = romashkaClientRepository;
        this.hrsClient = hrsClient;
        this.month = month;
    }

    @Override
    public void handleCDR(String CDRFile) {
        String[] CDRList = CDRFile.split("\n");
        for (String CDR : CDRList) {
            String[] record = CDR.split(",");
            LocalDateTime starTime = LocalDateTime.ofEpochSecond(Long.parseLong(record[3].strip()), 0, ZoneOffset.UTC);
            if (starTime.getMonthValue() != month.getMonth()) {
                calculateMonthlyTariffs();
                setRandomTariffsAndBalance();
                month.setMonth(starTime.getDayOfMonth());
            }

            String msisdn = record[1].strip();
            RomashkaClient romashkaClient = romashkaClientRepository.findByMsisdn(msisdn);
            if (romashkaClient != null) {
                Boolean subscriberConnectedCheck = romashkaClientRepository.findByMsisdn(record[2].strip()) != null;

                TransactionForHRS transactionForHRS = TransactionForHRS.builder()
                        .callType(CallType.getByCode(record[0].strip()))
                        .clientId(romashkaClient.getId())
                        .subscriberConnectedCheck(subscriberConnectedCheck)
                        .startTime(starTime)
                        .endTime(LocalDateTime.ofEpochSecond(Long.parseLong(record[4].strip()), 0, ZoneOffset.UTC))
                        .tariffId(romashkaClient.getTariffId())
                        .build();

                CostFromHRS costFromHRS = hrsClient.calculateCost(transactionForHRS);
                romashkaClient.setBalance(romashkaClient.getBalance().subtract(costFromHRS.getCost()));
                romashkaClientRepository.save(romashkaClient);
            }
        }
    }

    private void setRandomTariffsAndBalance() {
        Random random = new Random();
        Set<Long> tariffIds = new HashSet<>();
        List<RomashkaClient> romashkaClients = romashkaClientRepository.findAll();
        for (RomashkaClient romashkaClient : romashkaClients) {
            romashkaClient.setBalance(romashkaClient.getBalance().add(BigDecimal.valueOf(random.nextInt(100))));
            tariffIds.add(romashkaClient.getTariffId());
        }

        List<Long> tariffIdsList = tariffIds.stream().toList();
        int numClientsToChangeTariff = random.nextInt(3) + 1;

        Set<Integer> chosenIntegers = new HashSet<>();
        for (int i = 0; i < numClientsToChangeTariff; i++) {
            int index;

            do {
                index = random.nextInt(romashkaClients.size());
            } while (chosenIntegers.contains(index));
            chosenIntegers.add(index);

            romashkaClients.get(index).setTariffId(tariffIdsList.get(random.nextInt(tariffIdsList.size())));
        }

        romashkaClientRepository.saveAll(romashkaClients);
        sendTariffsToHRS();
    }

    private void calculateMonthlyTariffs() {
        List<CostFromHRS> costFromHRS = hrsClient.calculateCostForMonthlyTariff();
        for (CostFromHRS cost : costFromHRS) {
            RomashkaClient romashkaClient = romashkaClientRepository.findById(cost.getClientId()).orElseThrow();
            romashkaClient.setBalance(romashkaClient.getBalance().subtract(cost.getCost()));
            romashkaClientRepository.save(romashkaClient);
        }
    }

    public void sendTariffsToHRS() {
        List<ClientTariffToHRS> allClientsTariffs = romashkaClientRepository.findAll()
                .stream()
                .map(client -> ClientTariffToHRS.builder()
                        .clientId(client.getId())
                        .tariffId(client.getTariffId())
                        .build())
                .toList();
        StatusMessage statusDto = hrsClient.updateClientsTariffs(allClientsTariffs);
        System.out.println(statusDto.getStatus());
    }
}
