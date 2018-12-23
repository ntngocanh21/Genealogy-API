package com.api.genealogy.scheduler.death;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component("deathAnniversarySchedule")
public class DeathAnniversarySchedule {
	
	@Autowired
//    @Qualifier(value="infDeathScheduler")
    private TaskScheduler taskScheduler;
	
	@Autowired
    private DeathAnniversaryCronConfig cronConfig;
	
	@Autowired
    private DeathAnniversaryTask myTask;

//    @Scheduled(fixedRate = 86400000)
    public void scheduleAllCrons() {
        cronConfig.initial();
        cronConfig.getSchedules().forEach( cron -> taskScheduler.schedule(myTask, new CronTrigger(cron)) );
    }
}