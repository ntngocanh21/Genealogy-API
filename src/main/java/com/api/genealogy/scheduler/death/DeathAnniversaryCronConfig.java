package com.api.genealogy.scheduler.death;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.api.genealogy.model.People;
import com.api.genealogy.service.PeopleService;
import org.springframework.scheduling.annotation.EnableAsync;

@SuppressWarnings("all")
@Configuration
public class DeathAnniversaryCronConfig {
	
	@Autowired
    private PeopleService peopleService;
	
	private ArrayList<String> schedules;
	
	public void initial() {
		schedules = getDateSchedulerFromDatabase(peopleService.getAllPeopleFromSystem().getPeopleList());
	}

    private ArrayList<String> getDateSchedulerFromDatabase(List<People> peopleList) {
    	ArrayList<String> temp = new ArrayList<>();
        for(int index = 0; index < peopleList.size(); index++) {
        	if (peopleList.get(index).getDeathDay() != null)
        		temp.add(checkDayToSendPushNotification(peopleList.get(index).getDeathDay()));	
        }
		return temp;
	}

	private String checkDayToSendPushNotification(Date dealth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dealth);
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
		return "00 14 03 " + day + " " + month + " ?";
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
