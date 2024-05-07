package com.nexign.hrs.controller;

import java.math.BigDecimal;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.TransactionForHRS;

@RestController
public class TransactionController {
    
    @PostMapping("/calculateCost")
    public CostFromHRS calculateCost(@RequestBody TransactionForHRS transaction) {
        return CostFromHRS.builder()
            .clientId(transaction.getClientId())
            .cost(BigDecimal.valueOf(10))
            .build();
    }
}
