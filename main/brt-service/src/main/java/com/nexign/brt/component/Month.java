package com.nexign.brt.component;

import org.springframework.stereotype.Component;

@Component
public class Month {

    private int month = 1;
    
    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
