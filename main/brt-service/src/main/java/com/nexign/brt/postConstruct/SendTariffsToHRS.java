package com.nexign.brt.postConstruct;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexign.brt.client.HRSClient;
import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.dto.StatusDto;
import com.nexign.brt.service.RomashkaClientService;

import jakarta.annotation.PostConstruct;

@Component
public class SendTariffsToHRS {

    private RomashkaClientService romashkaClientService;
    private HRSClient hrsClient;

    @Autowired
    public SendTariffsToHRS(RomashkaClientService romashkaClientService, HRSClient hrsClient) {
        this.romashkaClientService = romashkaClientService;
        this.hrsClient = hrsClient;
    }

    @PostConstruct
    public void sendTariffsToHRS() {
        List<ClientTariffToHRS> allClientsTariffs = romashkaClientService.getAllClientsTariffs();
        StatusDto statusDto = hrsClient.updateClientsTariffs(allClientsTariffs);
        System.out.println(statusDto.getStatus());
    }
}
