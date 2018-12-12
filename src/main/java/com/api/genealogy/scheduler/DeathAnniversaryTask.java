package com.api.genealogy.scheduler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import com.api.genealogy.constant.PushNotificateionType;
import com.api.genealogy.model.Notification;
import com.api.genealogy.model.People;
import com.api.genealogy.service.AndroidPushNotificationsService;
import com.api.genealogy.service.NotificationService;
import com.api.genealogy.service.PeopleService;

@Component
public class DeathAnniversaryTask implements Runnable {

	@Autowired
    private PeopleService peopleService;
	
	@Autowired
    private NotificationService notificationService;
	
	@Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;
	
    @Override
    public void run() {
    	List<People> peopleList = peopleService.getAllPeopleFromSystem().getPeopleList();
    	for(int index = 0; index < peopleList.size(); index++) {
        	if (peopleList.get(index).getDeathDay() != null)
        		validateTime(peopleList.get(index).getBranchId(), peopleList.get(index).getDeathDay());	
        }
    }
    
    private void validateTime(Integer branchId, Date dealth) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dealth);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        if (day <= 5) {
        	day = 28;
        	if (month != 0) {
        		month = month - 1;	
        	}
        } else {
        	day = day - 5;
        }
		
        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        int day1 = currentTime.get(Calendar.DATE);
        int month1 = currentTime.get(Calendar.MONTH) + 1;
        
        if (day == day1 && month == month1) {
        	List<People> arrPeople = peopleService.getPeopleByBranchId(branchId).getPeopleList();
        	for (int index = 0; index < arrPeople.size(); index++) {
        		JSONObject body = new JSONObject();
                Notification item = new Notification();
                item.setTitle("Dealth Aniverssary");
                item.setType(PushNotificateionType.DEATH_ANNIVERSARY);
                item.setContent("You are going to have Dealth aniverssary of "+arrPeople.get(index).getName()+" Please arrange your time.");
                item.setDeviceId(branchId+"Branch");
                item.setUsername("System");
                notificationService.addNotification(item);
                try {
                    body.put("to", "/topics/" + branchId+"Branch");
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("title", item.getTitle());
                    notification.put("body", item.getContent());
                    JSONObject data = new JSONObject();
                    body.put("notification", notification);
                    body.put("data", data);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                HttpEntity<String> request = new HttpEntity<>(body.toString());

                CompletableFuture<String> pushNotification = androidPushNotificationsService.send(request);
                CompletableFuture.allOf(pushNotification).join();

                try {
                    String firebaseResponse = pushNotification.get();
                    System.out.println("Firebase response"
                            + "\n Response: " + firebaseResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
		
        	}
        }
	}
}