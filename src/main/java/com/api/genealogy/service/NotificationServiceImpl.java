package com.api.genealogy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.genealogy.entity.NotificationEntity;
import com.api.genealogy.model.Notification;
import com.api.genealogy.repository.NotificationRepository;

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
	
}
