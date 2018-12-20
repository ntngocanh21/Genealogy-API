package com.api.genealogy.service;

import com.api.genealogy.model.Notification;
import com.api.genealogy.service.response.NotificationResponse;

public interface NotificationService {
	void addNotification(Notification notification);
	NotificationResponse getListOfNotifications(String username);
}
