package com.nexign.gateway.service;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.LoginRequestDto;

public interface LoginService {
    
    StatusMessage login(LoginRequestDto loginRequestDto);
}
