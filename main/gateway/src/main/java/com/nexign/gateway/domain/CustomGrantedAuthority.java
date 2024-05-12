package com.nexign.gateway.domain;

import org.springframework.security.core.GrantedAuthority;

import com.nexign.gateway.domain.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomGrantedAuthority implements GrantedAuthority {

    private Role role;

    @Override
    public String getAuthority() {
        return getRole().name();
    }
    
}
