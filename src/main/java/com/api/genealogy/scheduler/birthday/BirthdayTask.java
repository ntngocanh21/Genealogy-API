package com.api.genealogy.scheduler.birthday;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import com.api.genealogy.entity.NotificationEntity;
import com.api.genealogy.entity.UserBranchPermissionEntity;
import com.api.genealogy.entity.UserEntity;
import com.api.genealogy.repository.NotificationRepository;
import com.api.genealogy.repository.NotificationTypeReponsitory;
import com.api.genealogy.repository.UserBranchPermissionRepository;
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
public class BirthdayTask implements Runnable {

    @Autowired
    private PeopleService peopleService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private AndroidPushNotificationsService androidPushNotificationsService;

    @Autowired
    private UserBranchPermissionRepository userBranchPermissionRepository;

    @Autowired
    private NotificationTypeReponsitory notificationTypeReponsitory;

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public void run() {
        List<People> peopleList = peopleService.getAllPeopleFromSystem().getPeopleList();
        for(int index = 0; index < peopleList.size(); index++) {
            if (peopleList.get(index).getBirthday() != null)
                validateTime(peopleList.get(index), peopleList.get(index).getBirthday());
        }
    }

    private void validateTime(People people, Date birthday) {
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

        Calendar currentTime = Calendar.getInstance();
        currentTime.setTime(new Date());
        int day1 = currentTime.get(Calendar.DATE);
        int month1 = currentTime.get(Calendar.MONTH) + 1;

        List<UserEntity> arrPeople = new ArrayList<>();
        if (day == day1 && month == month1) {
            List<UserBranchPermissionEntity>  arr = userBranchPermissionRepository.findUserBranchPermissionEntitiesByBranchUserEntity_IdAndStatus(people.getBranchId(), true);
            for(UserBranchPermissionEntity userBranchPermissionEntity : arr){
                arrPeople.add(userBranchPermissionEntity.getUserBranchEntity());
            }

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(birthday);
            int dayBirthday = calendar.get(Calendar.DATE);
            int monthBirthday = calendar.get(Calendar.MONTH)+1;

            String dayOfBirthday = String.valueOf(dayBirthday+"/"+monthBirthday+"/"+currentTime.get(Calendar.YEAR));
            for (int index = 0; index < arrPeople.size(); index++) {
                JSONObject body = new JSONObject();
                NotificationEntity item = new NotificationEntity();
                item.setTitle("Birthday Party");
                item.setNotificationTypeEntity(notificationTypeReponsitory.findNotificationTypeEntityByNotificationName(PushNotificateionType.BIRTHDAY_PARTY));
                item.setContent("You are going to have birthday of "+people.getName()+"\nPlease arrange your time in "+ dayOfBirthday +".");
                item.setUserNotificationEntity(arrPeople.get(index));
                item.setReadStatus(false);
                Date date = new Date();
                date.setDate(dayBirthday);
                date.setMonth(monthBirthday-1);
                date.setYear(date.getYear()-1);
                item.setDate(date);
                notificationRepository.save(item);
                try {
                    body.put("to", "/topics/" + arrPeople.get(index).getDeviceId());
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
                    System.out.println("Firebase response birthday: " + firebaseResponse);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}