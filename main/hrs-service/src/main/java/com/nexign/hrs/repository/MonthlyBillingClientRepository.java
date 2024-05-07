package com.nexign.hrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.hrs.domain.entity.MonthlyBillingClient;

public interface MonthlyBillingClientRepository extends JpaRepository<MonthlyBillingClient, Long> {
    
}
