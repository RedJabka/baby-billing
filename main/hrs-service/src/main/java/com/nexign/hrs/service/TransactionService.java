package com.nexign.hrs.service;

import com.nexign.hrs.domain.CostFromHRS;
import com.nexign.hrs.domain.TransactionForHRS;

public interface TransactionService {
    
    CostFromHRS calculateCost(TransactionForHRS transaction);
}
