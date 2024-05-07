package com.nexign.brt.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.nexign.brt.service.CDRHandlerService;

@Service
public class KafkaConsumer {

    private final CDRHandlerService cdrHandlerService;

    @Autowired
    public KafkaConsumer(CDRHandlerService cdrHandlerService) {
        this.cdrHandlerService = cdrHandlerService;
    }

    @KafkaListener(topics = "${kafka.topics.cdr}", groupId = "brt_consumer")
    public void getCDR(String CDRFile) {
        try {
            cdrHandlerService.handleCDR(CDRFile);
            System.out.println("File was processed");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
