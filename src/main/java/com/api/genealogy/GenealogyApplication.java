package com.api.genealogy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
@EnableScheduling
public class GenealogyApplication {

    public static void main(String[] args) {
        SpringApplication.run(GenealogyApplication.class, args);
    }
    
    @Scheduled(fixedRate = 2000)
    public void scheduleTaskWithFixedRate() {
        System.out.println("Fixed Rate Task: " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date().getTime()));
    }

}
