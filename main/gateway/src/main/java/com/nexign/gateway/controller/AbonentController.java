package com.nexign.gateway.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.PayRequestDto;
import com.nexign.gateway.service.AbonentService;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@OpenAPIDefinition(info = @Info(title = "Baby Billing API", version = "v1"))
@Tag(name = "Abonent", description = "Operations that can do abonent")
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/abonent")
public class AbonentController {

    private AbonentService abonentService;

    @Autowired
    public AbonentController(AbonentService abonentService) {
        this.abonentService = abonentService;
    }

    @Operation(summary = "Abonent pays his balance to the account", description = "With this method, the subscriber tops up his balance on the phone number by specifying how much money should be topped up")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }) })
    @PutMapping("/pay")
    public StatusMessage pay(PayRequestDto payRequestDto, Principal principal) {
        return abonentService.pay(payRequestDto, principal);
    }

}
