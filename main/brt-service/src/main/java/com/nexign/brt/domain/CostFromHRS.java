package com.nexign.brt.domain;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CostFromHRS {
    
    private Long clientId;
    private BigDecimal cost;
}
