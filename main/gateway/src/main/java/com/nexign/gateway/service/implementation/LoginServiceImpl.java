package com.nexign.gateway.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nexign.gateway.client.BRTClient;
import com.nexign.gateway.domain.CustomGrantedAuthority;
import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.LoginRequestDto;
import com.nexign.gateway.domain.enums.Role;
import com.nexign.gateway.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService {

    private AuthenticationManager authenticationManager;

    private BRTClient brtClient;

    @Autowired
    public LoginServiceImpl(
        AuthenticationManager authenticationManager,
        BRTClient brtClient
        ) {
        this.authenticationManager = authenticationManager;
        this.brtClient = brtClient;
    }

    @Override
    public StatusMessage login(LoginRequestDto loginRequestDto) {
        String login = loginRequestDto.getLogin();
        String password = loginRequestDto.getPassword();

        UsernamePasswordAuthenticationToken token;

        if (password == null) {
            StatusMessage statusMessage = brtClient.checkClientByMsisdn(login);
            if (statusMessage.getStatus() == 200) {
                token = new UsernamePasswordAuthenticationToken(
                    login,
                    "",
                    List.of(new CustomGrantedAuthority(Role.ROLE_ABONENT)));
            } else {
                throw new BadCredentialsException("Authentication failed");
            }
        } else if ("admin".equals(login) && "admin".equals(password)) {
            token = new UsernamePasswordAuthenticationToken(
                login, 
                password, 
                List.of(new CustomGrantedAuthority(Role.ROLE_MANAGER)));
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
        
        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return StatusMessage.builder()
            .status(HttpStatus.OK.value())
            .message("Success")
            .build();
    }
    
    
}
