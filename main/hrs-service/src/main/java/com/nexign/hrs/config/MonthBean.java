package com.nexign.hrs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonthBean {
    
    @Bean
    public int getMonth() {
        return 1;
    }
}
