package com.nexign.brt.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nexign.brt.client.HRSClient;
import com.nexign.brt.service.CDRHandlerService;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ServiceInitializer {

    private HRSClient hrsClient;

    private CDRHandlerService cdrHandlerService;

    private final int DELAY = 10000;

    @Autowired
    public ServiceInitializer(
        HRSClient hrsClient, 
        CDRHandlerService cdrHandlerService
    ) {
        this.hrsClient = hrsClient;
        this.cdrHandlerService = cdrHandlerService;
    }

    @PostConstruct
    public void initialize() {
        boolean success = false;
        while (!success) {
            try {
                hrsClient.health();
                success = true;
                log.info("Connected to HRS");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    Thread.sleep(DELAY);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
        cdrHandlerService.sendTariffsToHRS();
    }
}
