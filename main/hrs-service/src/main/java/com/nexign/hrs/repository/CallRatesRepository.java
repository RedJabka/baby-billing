package com.nexign.hrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexign.hrs.domain.entity.CallRate;

public interface CallRatesRepository extends JpaRepository<CallRate, Long> {
    
}
