package com.nexign.gateway.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.nexign.gateway.client.BRTClient;
import com.nexign.gateway.domain.enums.Role;
import com.nexign.gateway.domain.CustomGrantedAuthority;
import com.nexign.gateway.domain.StatusMessage;

@Service
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private BRTClient brtClient;

    @Autowired
    public CustomAuthenticationProvider(BRTClient brtClient) {
        this.brtClient = brtClient;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String login = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (password.isEmpty()) {
            StatusMessage statusMessage = brtClient.checkClientByMsisdn(login);
            if (statusMessage.getStatus() == 200) {
                return new UsernamePasswordAuthenticationToken(
                    login, 
                    password, 
                    List.of(new CustomGrantedAuthority(Role.ROLE_ABONENT)));
            } else {
                throw new BadCredentialsException("Authentication failed");
            }
        } else if ("admin".equals(login) && "admin".equals(password)) {
            return new UsernamePasswordAuthenticationToken(
                login, 
                password,
                List.of(new CustomGrantedAuthority(Role.ROLE_MANAGER)));
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    
}
