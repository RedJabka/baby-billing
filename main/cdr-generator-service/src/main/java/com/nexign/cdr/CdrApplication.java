package com.nexign.cdr;

import com.nexign.cdr.service.CDRService;
import com.nexign.cdr.service.serviceImpl.CDRServiceImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class CdrApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(CdrApplication.class, args);

		CDRService fileGenerationService = context.getBean(CDRServiceImpl.class);

		fileGenerationService.generateCDR();

		context.close();
	}

}
