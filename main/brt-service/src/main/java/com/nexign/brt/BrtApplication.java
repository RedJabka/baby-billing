package com.nexign.brt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.event.EventListener;

import com.nexign.brt.service.CDRHandlerService;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class BrtApplication {

	@Autowired
	private CDRHandlerService cdrHandlerService;

	public static void main(String[] args) {
		SpringApplication.run(BrtApplication.class, args);
	}

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent event) {
		try {
			cdrHandlerService.sendTariffsToHRS();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
