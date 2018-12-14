package com.api.genealogy.scheduler.birthday;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import com.api.genealogy.entity.PeopleEntity;
import com.api.genealogy.model.People;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.PeopleRepository;
import com.api.genealogy.service.PeopleService;

@SuppressWarnings("all")
@Component("DeathAnniversaryTask")
public class BirthdaySchedule {
	
	@Autowired
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