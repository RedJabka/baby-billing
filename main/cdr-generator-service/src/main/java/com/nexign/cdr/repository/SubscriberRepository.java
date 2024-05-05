package com.nexign.cdr.repository;

import com.nexign.cdr.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {
    Subscriber findByMsisdn(String msisdn);
}
