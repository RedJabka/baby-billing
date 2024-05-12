package com.nexign.hrs.domain.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "client_remaining_resources")
public class ClientRemainingResource {

    @Id
    @Column(name = "remaining_resources_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private BillingClient client;

    @OneToOne
    @JoinColumn(name = "resource_id")
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private IncludedResource resource;

    private BigDecimal remainingResourceAmount;
}
