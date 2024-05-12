package com.nexign.brt.service;

public interface CDRHandlerService {

    void handleCDR(String CDRFile);
    void sendTariffsToHRS();
}
