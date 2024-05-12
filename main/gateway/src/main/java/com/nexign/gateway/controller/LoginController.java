package com.nexign.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.LoginRequestDto;
import com.nexign.gateway.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "Login method")
@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "Login method", description = "Checks if user exists and password is correct")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = StatusMessage.class)) }) })
    @PostMapping("/login")
    public StatusMessage login(@RequestBody LoginRequestDto loginRequestDto) {
        return loginService.login(loginRequestDto);
    }
}
