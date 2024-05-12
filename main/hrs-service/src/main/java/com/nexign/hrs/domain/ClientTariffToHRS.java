package com.nexign.hrs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientTariffToHRS {
    
    private Long clientId;
    private Long tariffId;
}
