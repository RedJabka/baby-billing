package com.nexign.gateway.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAbonentRequestToBrtDto {
    private String msisdn;
    private Long tariffId;
    private BigDecimal money;
}
