package com.nexign.hrs.domain.entity;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "billing_clients")
public class BillingClient {
    
    @Id
    @Column(name = "client_id")
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "tariff_id", referencedColumnName = "tariff_id")
    private Tariff tariff;

    @OneToMany(mappedBy = "client")
    private List<ClientRemainingResource> clientRemainingResources;
}
