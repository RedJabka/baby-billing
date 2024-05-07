package com.nexign.brt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.brt.domain.entity.RomashkaClient;

public interface RomashkaClientRepository extends JpaRepository<RomashkaClient, Long> {
    RomashkaClient findByMsisdn(String msisdn);
}
