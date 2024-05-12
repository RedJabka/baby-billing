package com.nexign.hrs.domain.entity;

import java.math.BigDecimal;
import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tariffs")
public class Tariff {
    
    @Id
    @Column(name = "tariff_id")
    private Long id;

    private String tariffName;
    private String billingMethod;
    private BigDecimal monthlyCost;
    private String description;

    // @OneToOne(mappedBy = "tariff", cascade = CascadeType.ALL, orphanRemoval = true)
    // private BillingClient billingClient;

    @OneToMany(mappedBy = "tariff")
    private List<IncludedResource> includedResource;
}
