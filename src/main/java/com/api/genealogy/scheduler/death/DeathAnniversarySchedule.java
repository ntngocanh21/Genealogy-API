package com.api.genealogy.scheduler.death;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component("DeathAnniversaryTask")
public class DeathAnniversarySchedule {
	
	@Autowired
    private TaskScheduler taskScheduler;
	
	@Autowired
    private DeathAnniversaryCronConfig cronConfig;
	
	@Autowired
    private DeathAnniversaryTask myTask;
	
	public void scheduleAllCrons() {
		cronConfig.initial();
        cronConfig.getSchedules().forEach( cron -> taskScheduler.schedule(myTask, new CronTrigger(cron)) );
    }
	
}