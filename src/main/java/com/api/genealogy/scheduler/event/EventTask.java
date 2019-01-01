package com.api.genealogy.scheduler.event;

import com.api.genealogy.constant.PushNotificateionType;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.model.Event;
import com.api.genealogy.model.Notification;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.NotificationTypeReponsitory;
import com.api.genealogy.repository.UserBranchPermissionRepository;
import com.api.genealogy.service.AndroidPushNotificationsService;
import com.api.genealogy.service.EventService;
import com.api.genealogy.service.NotificationService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class EventTask implements Runnable {

	@Autowired
    private EventService eventService;
	
	@Autowired
    private NotificationService notificationService;
	
	@Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;
    
    @Autowired
    private NotificationTypeReponsitory notificationTypeReponsitory;

    @Autowired
    private BranchRepository branchRepository;

    @Override
    public void run() {
        System.out.println("The system are running!");
    	List<Event> eventList = eventService.getAllEventFromSystem();
    	for (int index = 0; index < eventList.size(); index++) {
            validateTime(eventList.get(index), eventList.get(index).getDate());
        }
    }
    
    private void validateTime(Event event, Date eventDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(eventDate);
        int day = cal.get(Calendar.DATE);
        int month = cal.get(Calendar.MONTH) + 1;
        int year = cal.get(Calendar.YEAR);
        int hour = cal.get(Calendar.HOUR) == 0 ? 12 : cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        int currentDay = currentTime.get(Calendar.DATE);
        int currentMonth = currentTime.get(Calendar.MONTH) + 1;
        int currentYear = currentTime.get(Calendar.YEAR);
        int currentHour = currentTime.get(Calendar.HOUR)  == 0 ? 12 : cal.get(Calendar.HOUR);
        int currentMinute = currentTime.get(Calendar.MINUTE);

        List<UserEntity> arrPeople = new ArrayList<>();
        if (day == currentDay && month == currentMonth && year == currentYear && hour == currentHour && minute == currentMinute) {
            List<UserBranchPermissionEntity>  arr = userBranchPermissionRepository.findUserBranchPermissionEntitiesByBranchUserEntity_IdAndStatus(event.getBranchId(), true);
            for(UserBranchPermissionEntity userBranchPermissionEntity : arr){
                arrPeople.add(userBranchPermissionEntity.getUserBranchEntity());
            }

            String branchName = branchRepository.findBranchEntityById(event.getBranchId()).getName();
        	for (int index = 0; index < arrPeople.size(); index++) {

        		JSONObject body = new JSONObject();
                Notification item = new Notification();
                item.setTitle("Event");
                item.setNotificationTypeId(notificationTypeReponsitory.findNotificationTypeEntityByNotificationName(PushNotificateionType.FAMILY_ACTIVITIES).getId());
                
                String text = "You have an Event from "+ branchName + " \n Please arrange your time to join it.";

                item.setContent(event.getContent());
                item.setUserId(arrPeople.get(index).getId());
                item.setReadStatus(false);
                item.setDate(eventDate);
                notificationService.addNotification(item);
                try {
                    body.put("to", "/topics/" + arrPeople.get(index).getDeviceId());
                    body.put("priority", "high");

                    JSONObject notification = new JSONObject();
                    notification.put("title", item.getTitle());
                    notification.put("body", text);
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
                    System.out.println("Firebase response: " + firebaseResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        	}
        }
	}
}