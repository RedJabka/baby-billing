package com.nexign.hrs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nexign.hrs.domain.entity.BillingClient;

public interface BillingClientRepository extends JpaRepository<BillingClient, Long> {

    @Query("SELECT m FROM BillingClient m JOIN m.tariff t WHERE t.billingMethod LIKE :billingMethod")
    List<BillingClient> findAllWhereBillingMethodLike(String billingMethod);
}
