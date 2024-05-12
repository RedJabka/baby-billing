package com.nexign.hrs.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.TransactionForHRS;
import com.nexign.hrs.service.TransactionService;

/**
 * Controller for calculate cost for transaction
 */
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }
    
    @PostMapping("/calculateCost")
    public CostFromHRS calculateCost(@RequestBody TransactionForHRS transaction) {
        return transactionService.calculateCost(transaction);
    }

    @PostMapping("/calculateCostForMonthlyTariff")
    public List<CostFromHRS> calculateCostForMonthlyTariff() {
        return transactionService.calculateCostForMonthlyTariff();
    }
}
