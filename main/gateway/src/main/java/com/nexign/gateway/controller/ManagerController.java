package com.nexign.gateway.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nexign.gateway.domain.StatusMessage;
import com.nexign.gateway.domain.dto.ChangeTariffRequestDto;
import com.nexign.gateway.domain.dto.NewAbonentRequestDto;
import com.nexign.gateway.service.ManagerService;

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
@Tag(name = "Manager", description = "Operations that can do manager")
@SecurityRequirement(name = "basicAuth")
@RequestMapping("/manager")
public class ManagerController {

    private ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @Operation(summary = "Manager change tariff for abonent", description = "In this method manager can change tariff for abonent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                @Content(schema = @Schema(implementation = StatusMessage.class), mediaType = "application/json") }),
                @ApiResponse(responseCode = "401", content = {
                        @Content(schema = @Schema(implementation = StatusMessage.class)) }),
                @ApiResponse(responseCode = "403", content = {
                        @Content(schema = @Schema(implementation = StatusMessage.class)) }),
                @ApiResponse(responseCode = "404", content = {
                        @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }) })
    @PutMapping("/changeTariff")
    public StatusMessage changeTariff(@RequestBody ChangeTariffRequestDto changeTariffRequestDto) {
        return managerService.changeTariff(changeTariffRequestDto);
    }

    @Operation(summary = "Manager save new abonent", description = "In this method manager can save new abonent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "401", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "403", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "404", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "409", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }),
            @ApiResponse(responseCode = "500", content = {
                    @Content(schema = @Schema(implementation = StatusMessage.class)) }) })
    @PostMapping("/save")
    public StatusMessage saveNewAbonent(@RequestBody NewAbonentRequestDto newAbonentRequestDto) {
        return managerService.saveNewAbonent(newAbonentRequestDto);
    }

}
