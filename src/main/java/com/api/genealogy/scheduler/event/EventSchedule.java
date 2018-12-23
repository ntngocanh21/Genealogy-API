package com.api.genealogy.scheduler.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component("eventSchedule")
public class EventSchedule {
	
	@Autowired
    @Qualifier(value="infEventScheduler")
    private TaskScheduler taskScheduler;
	
	@Autowired
    private EventCronConfig cronConfig;
	
	@Autowired
    private EventTask myTask;

	public void scheduleAllCrons() {
        cronConfig.initial();
        cronConfig.getSchedules().forEach(
                cron -> taskScheduler.schedule(myTask, new CronTrigger(cron))
        );
    }
}