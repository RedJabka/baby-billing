package com.nexign.brt.domain.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewAbonentRequestDto {
    private String msisdn;
    private Long tariffId;
    private BigDecimal money;
}
