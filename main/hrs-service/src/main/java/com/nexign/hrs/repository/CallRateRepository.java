package com.nexign.hrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nexign.hrs.domain.entity.CallRate;
import com.nexign.hrs.domain.enums.CallType;

public interface CallRateRepository extends JpaRepository<CallRate, Long> {
    @Query("SELECT m FROM CallRate m JOIN m.tariff t WHERE t.id = :tariffId AND m.callType = :callType AND m.withinTheNetworkCheck = :withinTheNetworkCheck")
    CallRate findByTariffIdAndCallTypeAndWithinTheNetworkCheck(Long tariffId, CallType callType, Boolean withinTheNetworkCheck);
}
