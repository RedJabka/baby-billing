package com.nexign.brt.service.implementation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.brt.client.HRSClient;
import com.nexign.brt.domain.CostFromHRS;
import com.nexign.brt.domain.TransactionForHRS;
import com.nexign.brt.domain.entity.RomashkaClient;
import com.nexign.brt.domain.enums.CallType;
import com.nexign.brt.repository.RomashkaClientRepository;
import com.nexign.brt.service.CDRHandlerService;

@Service
public class CDRHandlerServiceImpl implements CDRHandlerService {

    private final RomashkaClientRepository romashkaClientRepository;
    private final HRSClient hrsClient;

    @Autowired
    public CDRHandlerServiceImpl(RomashkaClientRepository romashkaClientRepository, HRSClient hrsClient) {
        this.romashkaClientRepository = romashkaClientRepository;
        this.hrsClient = hrsClient;
    }
    
    @Override
    public void handleCDR(String CDRFile) {
        String[] CDRList = CDRFile.split("\n");
        for (String CDR : CDRList) {
            String[] record = CDR.split(",");
            String msisdn = record[1].strip();
            RomashkaClient romashkaClient = romashkaClientRepository.findByMsisdn(msisdn);
            if (romashkaClient != null) {
                Boolean subscriberConnectedCheck = romashkaClientRepository.findByMsisdn(record[2].strip()) != null;

                TransactionForHRS transactionForHRS = TransactionForHRS.builder()
                    .callType(CallType.getByCode(record[0].strip()))
                    .clientId(romashkaClient.getId())
                    .subscriberConnectedCheck(subscriberConnectedCheck)
                    .startTime(LocalDateTime.ofEpochSecond(Long.parseLong(record[3].strip()), 0, ZoneOffset.UTC))
                    .endTime(LocalDateTime.ofEpochSecond(Long.parseLong(record[4].strip()), 0, ZoneOffset.UTC))
                    .tariffId(romashkaClient.getTariffId())
                    .build();

                CostFromHRS costFromHRS = hrsClient.calculateCost(transactionForHRS);
                romashkaClient.setBalance(romashkaClient.getBalance().add(costFromHRS.getCost().negate()));
                romashkaClientRepository.save(romashkaClient);
            }
        }
    }
}
