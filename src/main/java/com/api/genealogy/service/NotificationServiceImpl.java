package com.api.genealogy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.api.genealogy.entity.NotificationEntity;
import com.api.genealogy.model.Notification;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.EventRepository;
import com.api.genealogy.repository.NotificationRepository;
import com.api.genealogy.repository.NotificationTypeReponsitory;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.NotificationResponse;

@Service
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationTypeReponsitory notificationTypeReponsitory;

	@Autowired
	private UserRepository userRepository;

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
		notificationEntity.setDate(notification.getDate());
		notificationEntity.setNotificationTypeEntity(notificationTypeReponsitory.findNotificationTypeEntityById(notification.getNotificationTypeId()));
		notificationEntity.setContent(notification.getContent());
		notificationEntity.setReadStatus(notification.getReadStatus());
		notificationEntity.setUserNotificationEntity(userRepository.findUserEntityById(notification.getUserId()));
		return notificationEntity;
	}

	@Override
	public NotificationResponse getListOfNotifications(String username) {
		int userId = userRepository.findUserEntityByUsername(username).getId();
		List<NotificationEntity> arrNotificationEntity = notificationRepository.findNotificationEntitiesByUserNotificationEntity_IdOrderByIdDesc(userId);
		MessageResponse messageResponse = new MessageResponse(0,"Success");
		return new NotificationResponse(messageResponse, parseListNotificationEntityToListNotification(arrNotificationEntity));
	}

	public static Notification parseNotificationEntityToNotification(NotificationEntity notificationEntity) {
		Notification notification = new Notification();
		notification.setId(notificationEntity.getId());
		notification.setTitle(notificationEntity.getTitle());
		notification.setReadStatus(notificationEntity.getReadStatus());
		notification.setNotificationTypeId(notificationEntity.getNotificationTypeEntity().getId());
		notification.setDate(notificationEntity.getDate());
		notification.setContent(notificationEntity.getContent());
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
