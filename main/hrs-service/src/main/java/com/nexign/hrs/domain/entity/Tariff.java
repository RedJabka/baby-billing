package com.nexign.hrs.domain.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tariff {
    
    @Id
    @Column(name = "tariff_id")
    private Long id;

    private String tariffName;
    private String billingMethod;
    private BigDecimal monthlyCost;
    private String description;

    @OneToOne
    @JoinColumn(name = "client_id")
    private MonthlyBillingClient monthlyBillingClient;
}
