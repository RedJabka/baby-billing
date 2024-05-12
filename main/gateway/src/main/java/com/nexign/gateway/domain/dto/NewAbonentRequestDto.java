package com.nexign.gateway.domain.dto;

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
}