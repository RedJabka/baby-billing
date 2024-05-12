package com.nexign.hrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.hrs.domain.entity.Tariff;

public interface TariffRepository extends JpaRepository<Tariff, Long> {
    List<Tariff> findByBillingMethodLike(String billingMethod);
    Tariff findByTariffName(String tariffName);
}
