package com.api.genealogy.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.genealogy.entity.BranchEntity;
import com.api.genealogy.entity.EventEntity;
import com.api.genealogy.entity.NotificationEntity;
import com.api.genealogy.model.Branch;
import com.api.genealogy.model.Event;
import com.api.genealogy.model.Notification;
import com.api.genealogy.repository.BranchRepository;
import com.api.genealogy.repository.EventRepository;
import com.api.genealogy.repository.NotificationRepository;
import com.api.genealogy.repository.NotificationTypeReponsitory;
import com.api.genealogy.repository.UserRepository;
import com.api.genealogy.service.response.BranchResponse;
import com.api.genealogy.service.response.EventResponse;
import com.api.genealogy.service.response.MessageResponse;
import com.api.genealogy.service.response.NotificationResponse;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	@Autowired
	private NotificationTypeReponsitory notificationTypeReponsitory;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired 
	private BranchRepository branchRepository;
	
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
        notificationEntity.setNotificationTypeEntity(notificationTypeReponsitory.findNotificationTypeEntityById(notification.getNotification_type_id()));
        notificationEntity.setContent(notification.getContent());
        notificationEntity.setReadStatus(notification.getReadStatus());
        notificationEntity.setUserNotificationEntity(userRepository.findUserEntityById(notification.getUser_id()));
        return notificationEntity;
    }

	@Override
	public NotificationResponse getListOfNotifications(String username, Notification notification) {
        return null;
	}
	
	public static Notification parseNotificationEntityToNotification(NotificationEntity notificationEntity) {
        Notification notification = new Notification();
        notification.setId(notificationEntity.getId());
        notification.setTitle(notificationEntity.getTitle());
        notification.setReadStatus(notificationEntity.getReadStatus());
        notification.setNotification_type_id(notificationEntity.getNotificationTypeEntity().getId());
        notification.setUser_id(notificationEntity.getUserNotificationEntity().getId());
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

	@Override
	public EventResponse pushEvent(Event event) {
		EventEntity eventEntity = parseEventToEventEntity(event);
		eventRepository.save(eventEntity);
		
//		List<EventEntity> eventEntities = (List<EventEntity>) eventRepository
//                .findEventEntitiesById(genealogyId);
//		
//		List<Event> events = parseListBranchEntityToListBranch(eventEntities);
//        MessageResponse messageResponse = new MessageResponse(0,"Success");
//        EventResponse eventResponse = new EventResponse(messageResponse, events);
		return null;
	}
	
	private EventEntity parseEventToEventEntity(Event event) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setTitle(event.getTitle());
		eventEntity.setContent(event.getContent());
		eventEntity.setDate(event.getDate());
		eventEntity.setUserCreatedEventEntity(userRepository.findUserEntityByUsername(event.getUsername()));
		eventEntity.setBranchEventEntity(branchRepository.findBranchEntityById(event.getBranch_id()));
        return eventEntity;
    }
}
