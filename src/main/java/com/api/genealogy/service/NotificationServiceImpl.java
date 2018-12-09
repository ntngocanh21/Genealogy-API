package com.api.genealogy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.genealogy.entity.NotificationEntity;
import com.api.genealogy.model.Notification;
import com.api.genealogy.repository.NotificationRepository;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.NotificationResponse;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
    private NotificationRepository notificationRepository;
	
	@Override
	public void addNotification(Notification notification) {
        NotificationEntity notificationEntity = parseNotificationToNotificationEntity(notification);
		notificationRepository.save(notificationEntity);
	}
	
	private NotificationEntity parseNotificationToNotificationEntity(Notification notification) {
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setTitle(notification.getTitle());
        notificationEntity.setType(notification.getType());
        notificationEntity.setContent(notification.getContent());
        notificationEntity.setDeviceId(notification.getDeviceId());
        notificationEntity.setIsPushed(notification.getIsPushed());
        notificationEntity.setUsername(notification.getUsername());
        return notificationEntity;
    }

	@Override
	public NotificationResponse getListOfNotifications(String username, Notification notification) {
		List<NotificationEntity> notificationEntities = notificationRepository.getListOfNotifications(notification.getDeviceId(), username); 
		List<Notification> notifications = parseListNotificationEntityToListNotification(notificationEntities);
        MessageResponse messageResponse = new MessageResponse(0,"Success");
        NotificationResponse notificationResponse = new NotificationResponse(messageResponse, notifications);
        return notificationResponse;
	}
	
	public static Notification parseNotificationEntityToNotification(NotificationEntity notificationEntity) {
        Notification notification = new Notification();
        notification.setId(notificationEntity.getId());
        notification.setTitle(notificationEntity.getTitle());
        notification.setUsername(notificationEntity.getUsername());
        notification.setIsPushed(notificationEntity.getIsPushed());
        notification.setType(notificationEntity.getType());
        notification.setContent(notificationEntity.getContent());
        notification.setDeviceId(notificationEntity.getDeviceId());
        return notification;
    }

    public static List<Notification> parseListNotificationEntityToListNotification(List<NotificationEntity> notificationEntities) {
        List<Notification> notifications = new ArrayList<>();
        for (NotificationEntity notificationEntity : notificationEntities) {
            Notification notification = parseNotificationEntityToNotification(notificationEntity);
            notifications.add(notification);
        }
        return notifications;
    }
}
