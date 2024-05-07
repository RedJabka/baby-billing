package com.nexign.brt.service.implementation;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nexign.brt.domain.ClientTariffToHRS;
import com.nexign.brt.domain.dto.BalanceDto;
import com.nexign.brt.domain.dto.StatusDto;
import com.nexign.brt.domain.entity.RomashkaClient;
import com.nexign.brt.repository.RomashkaClientRepository;
import com.nexign.brt.service.RomashkaClientService;

@Service
public class RomashkaClientServiceImpl implements RomashkaClientService {

    private final RomashkaClientRepository romashkaClientRepository;

    @Autowired
    public RomashkaClientServiceImpl(RomashkaClientRepository romashkaClientRepository) {
        this.romashkaClientRepository = romashkaClientRepository;
    }

    @Override
    public StatusDto loginClient(String msisdn) {
        RomashkaClient romashkaClient = romashkaClientRepository.findByMsisdn(msisdn);
        if (romashkaClient == null) {
            return StatusDto.builder()
                .status(401)
                .message("Client not found")
                .build();
        }
        return StatusDto.builder()
            .status(200)
            .message("Client found")
            .build();
    }

    @Override
    public List<ClientTariffToHRS> getAllClientsTariffs() {
        return romashkaClientRepository.findAll()
            .stream()
            .map(client -> ClientTariffToHRS.builder()
                .clientId(client.getId())
                .tariffId(client.getTariffId())
                .build())
            .toList();
    }

    @Override
    public RomashkaClient findClientByMsisdn(String msisdn) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findClientByMsisdn'");
    }

    @Override
    public RomashkaClient saveClient(RomashkaClient client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveClient'");
    }

    @Override
    public RomashkaClient changeTariff(RomashkaClient client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeTariff'");
    }

    @Override
    public BigDecimal changeBalance(BalanceDto balanceDto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeBalance'");
    }

    @Override
    public List<Long> findClientsByTariff(String tariffId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findClientsByTariff'");
    }
    

}
