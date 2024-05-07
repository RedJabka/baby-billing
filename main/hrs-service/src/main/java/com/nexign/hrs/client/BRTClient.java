package com.nexign.hrs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "brt-service")
public interface BRTClient {

    @GetMapping(value = "/clients", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClients();

    @GetMapping(value = "/clients/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClientById(@PathVariable("id") String id);
}
