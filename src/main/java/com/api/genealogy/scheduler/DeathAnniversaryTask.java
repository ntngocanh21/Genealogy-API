package com.api.genealogy.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class DeathAnniversaryTask implements Runnable {

    @Override
    public void run() {
    	System.out.println("Hello: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
    }
}