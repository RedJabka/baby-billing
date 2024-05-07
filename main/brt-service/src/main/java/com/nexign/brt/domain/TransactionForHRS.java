package com.nexign.brt.domain;

import java.time.LocalDateTime;

import com.nexign.brt.domain.enums.CallType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionForHRS {
    private Long transactionId;
    private CallType callType;
    private Long clientId;
    private Boolean subscriberConnectedCheck;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long tariffId;
}
