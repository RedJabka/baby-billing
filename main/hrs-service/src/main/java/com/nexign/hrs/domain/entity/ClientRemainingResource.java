package com.nexign.hrs.domain.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ClientRemainingResource {

    @Id
    @Column(name = "remaining_resources_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private MonthlyBillingClient client;

    @OneToOne
    @JoinColumn(name = "resource_id")
    private IncludedResource resource;

    private BigDecimal remainingResourceAmount;
}
