package com.api.genealogy.scheduler.event;

import com.api.genealogy.model.Event;
import com.api.genealogy.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@SuppressWarnings("all")
@Configuration
public class EventCronConfig {
	
	@Autowired
    private EventService eventService;
	
	private ArrayList<String> schedules;
	
	public void initial() {
		schedules = getDateSchedulerFromDatabase(eventService.getAllEventFromSystem());
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

    private ArrayList<String> getDateSchedulerFromDatabase(List<Event> eventList) {
    	ArrayList<String> temp = new ArrayList<>();
        for(int index = 0; index < eventList.size(); index++) {
			temp.add(checkDayToSendPushNotification(eventList.get(index).getDate()));
        }
		return temp;
	}

	private String checkDayToSendPushNotification(Date eventDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventDate);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR) == 0 ? 12 : cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		System.out.println("Date: " + "00 " + minute + " " + hour + " " + day + " " + month + " *");
		return "00 " + minute + " " + hour + " " + day + " " + month + " *";
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
