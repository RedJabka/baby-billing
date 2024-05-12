package com.nexign.gateway.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChangeTariffRequestDto {
    private String msisdn;
    private Long tariffId;
}
