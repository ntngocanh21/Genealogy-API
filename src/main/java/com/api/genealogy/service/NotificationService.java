package com.api.genealogy.service;

import com.api.genealogy.model.Event;
import com.api.genealogy.model.Notification;
import com.api.genealogy.service.response.EventResponse;
import com.api.genealogy.service.response.NotificationResponse;

public interface NotificationService {
	void addNotification(Notification notification);
	NotificationResponse getListOfNotifications(String username, Notification notification);
	EventResponse pushEvent(Event event);
}
