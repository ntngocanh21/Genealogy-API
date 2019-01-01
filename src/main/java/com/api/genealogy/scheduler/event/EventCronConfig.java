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

    private ArrayList<String> getDateSchedulerFromDatabase(List<Event> eventList) {
        ArrayList<String> temp = new ArrayList<>();
        for(int index = 0; index < eventList.size(); index++) {
            if (removeTaskIfNoExistedTime(eventList.get(index))) {
                temp.add(checkDayToSendPushNotification(eventList.get(index).getDate()));
            }
        }
        return temp;
    }

    private boolean removeTaskIfNoExistedTime(Event event) {
        boolean isCheck = false;
        Calendar cal = Calendar.getInstance();
        cal.setTime(event.getDate());
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR) == 0 ? 12 : cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE) - 1;

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        int currentDay = currentTime.get(Calendar.DATE);
        int currentMonth = currentTime.get(Calendar.MONTH) + 1;
        int currentYear = currentTime.get(Calendar.YEAR);
        int currentHour = currentTime.get(Calendar.HOUR)  == 0 ? 12 : cal.get(Calendar.HOUR);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        if (day == currentDay && month == currentMonth && year == currentYear && hour == currentHour && minute == currentMinute) {
            isCheck =  true;
        }
        return isCheck;
    }

    private String checkDayToSendPushNotification(Date eventDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventDate);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int hour = Integer.parseInt(converDateToString(eventDate));
        int minute = cal.get(Calendar.MINUTE);
        return "00 " + minute + " " + hour + " " + day + " " + month + " *";
    }

    @Bean
    public List<String> schedules() {
        return this.schedules;
    }

    public ArrayList<String> getSchedules() {
        return getDateSchedulerFromDatabase(eventService.getAllEventFromSystem());
    }

    private String converDateToString(Date date) {
        return date.toString().substring(11,13);
    }

    public void setSchedules(ArrayList<String> schedules) {
        this.schedules = schedules;
    }

}