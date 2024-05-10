package com.nexign.hrs.domain.entity;

import java.math.BigDecimal;

import com.nexign.hrs.domain.enums.CallType;
import com.nexign.hrs.domain.enums.CallTypeConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "call_rates")
public class CallRate {
    
    @Id
    @Column(name = "call_rate_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tariff_id", nullable = false)
    private Tariff tariff;
    
    @Convert(converter = CallTypeConverter.class)
    private CallType callType;

    private Boolean withinTheNetworkCheck;
    
    private BigDecimal ratePerMinute;
}
