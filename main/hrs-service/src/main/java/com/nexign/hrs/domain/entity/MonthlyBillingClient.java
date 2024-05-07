package com.nexign.hrs.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class MonthlyBillingClient {
    
    @Id
    @Column(name = "client_id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "tariff_id")
    private Tariff tariff;

    @OneToMany
    private ClientRemainingResource clientRemainingResource;
}
