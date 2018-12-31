package com.api.genealogy.scheduler.birthday;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.sound.midi.Soundbank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.genealogy.model.People;
import com.api.genealogy.service.PeopleService;
import com.google.protobuf.TextFormat.ParseException;

@SuppressWarnings("all")
@Configuration
public class BirthdayCronConfig {
	
	@Autowired
    private PeopleService peopleService;
	
	private ArrayList<String> schedules;
	
	public void initial() {
		schedules = getDateSchedulerFromDatabase(peopleService.getAllPeopleFromSystem().getPeopleList());
	}
	
	/**
	 * Read cron-job here: http://www.quartz-scheduler.org/documentation/quartz-2.x/tutorials/crontrigger.html
	 * *  .  *  .  *  .  *  .  *  .  *  
	   -     -     -     -     -     -
	   |     |     |     |     |     |
	   |     |     |     |     |     +----- year  
	   |     |     |     |     +----- month(1-12)
	   |     |     |     +------- day(0-31)
	   |     |     +--------- hour(0-23)
	   |     +----------- minute(0-59)
	   +------------- second(0-59)
	 */
    private ArrayList<String> getDateSchedulerFromDatabase(List<People> peopleList) {
    	ArrayList<String> temp = new ArrayList<>();
        for(int index = 0; index < peopleList.size(); index++) {
        	if (peopleList.get(index).getBirthday() != null)
        		temp.add(checkDayToSendPushNotification(peopleList.get(index).getBirthday()));	
        }
		return temp;
	}

	private String checkDayToSendPushNotification(Date birthday) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(birthday);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        if (day <= 3) {
        	day = 28;
        	if (month != 0) {
        		month = month - 1;	
        	}
        } else {
        	day = day - 3;
        }
        return "00 16 08 "+day+" "+month+" ?";
	}

	@Bean
    public List<String> schedules() {
        return this.schedules;
    }

    public List<String> getSchedules() {
        return schedules;
    }

    public void setSchedules(ArrayList<String> schedules) {
        this.schedules = schedules;
    }
    
}
