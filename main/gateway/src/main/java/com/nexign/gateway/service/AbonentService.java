package com.nexign.gateway.service;

import java.security.Principal;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.PayRequestDto;

public interface AbonentService {
    StatusMessage pay(PayRequestDto payRequestDto, Principal principal);
}
