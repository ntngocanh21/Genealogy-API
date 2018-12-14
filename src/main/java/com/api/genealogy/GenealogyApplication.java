package com.api.genealogy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import com.api.genealogy.scheduler.birthday.BirthdaySchedule;
import com.api.genealogy.scheduler.death.DeathAnniversarySchedule;

@SpringBootApplication
@EnableScheduling
public class GenealogyApplication {
	
	@Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }
	
    public static void main(String[] args) {
    	ApplicationContext ctx = SpringApplication.run(GenealogyApplication.class, args);
        DeathAnniversarySchedule deathAnniversaryTask = ctx.getBean(DeathAnniversarySchedule.class);
        deathAnniversaryTask.scheduleAllCrons();
        BirthdaySchedule birthdayTask = ctx.getBean(BirthdaySchedule.class);
        birthdayTask.scheduleAllCrons();
    }
}
