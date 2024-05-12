package com.nexign.brt.service.implementation;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.nexign.brt.client.HRSClient;
import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.CostFromHRS;
import com.nexign.brt.domain.StatusMessage;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.ChangeTariffRequestDto;
import com.nexign.brt.domain.dto.NewAbonentRequestDto;
import com.nexign.brt.domain.entity.RomashkaClient;
import com.nexign.brt.repository.RomashkaClientRepository;
import com.nexign.brt.service.RomashkaClientService;

import jakarta.persistence.EntityNotFoundException;

@Service
public class RomashkaClientServiceImpl implements RomashkaClientService {

    private final RomashkaClientRepository romashkaClientRepository;

    private final HRSClient hrsClient;

    @Autowired
    public RomashkaClientServiceImpl(
            RomashkaClientRepository romashkaClientRepository,
            HRSClient hrsClient) {
        this.romashkaClientRepository = romashkaClientRepository;
        this.hrsClient = hrsClient;
    }

    @Override
    public StatusMessage checkClientByMsisdn(String msisdn) {
        if (romashkaClientRepository.findByMsisdn(msisdn) != null) {
            return StatusMessage.builder()
                    .status(HttpStatus.OK.value())
                    .message("Client found")
                    .build();
        }
        return StatusMessage.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .message("Client not found")
                .build();

    }


    @Override
    public StatusMessage saveClient(NewAbonentRequestDto newAbonentRequestDto) {
        if (romashkaClientRepository.findByMsisdn(newAbonentRequestDto.getMsisdn()) != null) {
            return StatusMessage.builder()
                    .status(HttpStatus.CONFLICT.value())
                    .message("Client already exists")
                    .build();
        }

        if (hrsClient.tariffExists(newAbonentRequestDto.getTariffId()).getStatus() != HttpStatus.OK.value()) {
            return StatusMessage.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Tariff not found")
                    .build();
        }

        RomashkaClient romashkaClient = romashkaClientRepository.save(RomashkaClient.builder()
                .msisdn(newAbonentRequestDto.getMsisdn())
                .tariffId(newAbonentRequestDto.getTariffId())
                .balance(newAbonentRequestDto.getMoney())
                .build());

        hrsClient.updateClientsTariffs(List.of(ClientTariffToHRS.builder()
                .clientId(romashkaClient.getId())
                .tariffId(romashkaClient.getTariffId())
                .build()));

        return StatusMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Client saved. Id: " + romashkaClient.getId())
                .build();

    }

    @Override
    public BigDecimal changeBalance(BalanceDto balanceDto) {
        RomashkaClient romashkaClient = romashkaClientRepository.findByMsisdn(balanceDto.getMsisdn());
        if (romashkaClient == null) {
            throw new EntityNotFoundException("Client not found");
        }
        romashkaClient.setBalance(romashkaClient.getBalance().add(balanceDto.getMoney()));
        romashkaClientRepository.save(romashkaClient);
        return romashkaClient.getBalance();
    }

    @Override
    public StatusMessage changeTariff(ChangeTariffRequestDto changeTariffRequestDto) {
        RomashkaClient romashkaClient = romashkaClientRepository
                .findByMsisdn(changeTariffRequestDto.getMsisdn());
        if (romashkaClient == null) {
            return StatusMessage.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Client not found")
                    .build();
        }

        if (hrsClient.tariffExists(changeTariffRequestDto.getTariffId()).getStatus() != HttpStatus.OK.value()) {
            return StatusMessage.builder()
                    .status(HttpStatus.NOT_FOUND.value())
                    .message("Tariff not found")
                    .build();
        }

        CostFromHRS costFromHRS = hrsClient.changeTariff(ClientTariffToHRS.builder()
                .clientId(romashkaClient.getId())
                .tariffId(romashkaClient.getTariffId())
                .build());
        if (costFromHRS.getCost().compareTo(BigDecimal.ZERO) != 0) {
            romashkaClient.setBalance(romashkaClient.getBalance().subtract(costFromHRS.getCost()));
        }
        romashkaClient.setTariffId(changeTariffRequestDto.getTariffId());
        romashkaClientRepository.save(romashkaClient);
        return StatusMessage.builder()
                .status(HttpStatus.OK.value())
                .message("Tariff changed. Abonent Id: " + romashkaClient.getId())
                .build();
    }

}
