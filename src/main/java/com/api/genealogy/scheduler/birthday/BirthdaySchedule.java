package com.api.genealogy.scheduler.birthday;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component("birthdaySchedule")
public class BirthdaySchedule {
	
	@Autowired
    @Qualifier(value="infBirthdayScheduler")
    private TaskScheduler taskScheduler;
	
	@Autowired
    private BirthdayCronConfig cronConfig;
	
	@Autowired
    private BirthdayTask myTask;

	public void scheduleAllCrons() {
		cronConfig.initial();
        cronConfig.getSchedules().forEach( cron -> taskScheduler.schedule(myTask, new CronTrigger(cron)) );
    }
	
}