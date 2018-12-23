package com.api.genealogy;

import com.api.genealogy.scheduler.birthday.BirthdaySchedule;
import com.api.genealogy.scheduler.death.DeathAnniversarySchedule;
import com.api.genealogy.scheduler.event.EventSchedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

@SpringBootApplication
@EnableScheduling
@ImportResource({"classpath*:applicationContext.xml"})
public class GenealogyApplication {
	
	@Bean
    public TaskScheduler taskScheduler() {
        return new ConcurrentTaskScheduler();
    }

    @Autowired
    static DeathAnniversarySchedule mDeathAnniversarySchedule;

    @Autowired
    static BirthdaySchedule mBirthdaySchedule;

    @Autowired
    static EventSchedule mEventSchedule;
	
    public static void main(String[] args) {
        SpringApplication.run(GenealogyApplication.class, args).getBeanDefinitionNames();
    }
}
