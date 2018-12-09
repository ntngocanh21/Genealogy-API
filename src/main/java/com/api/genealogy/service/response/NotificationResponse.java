package com.api.genealogy.service.response;

import java.util.List;

import com.api.genealogy.model.Notification;

public class NotificationResponse {

	private MessageResponse error;
    private List<Notification> notificationList;

    public NotificationResponse() {
    }

    public NotificationResponse(MessageResponse error, List<Notification> notificationList) {
        this.error = error;
        this.notificationList = notificationList;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<Notification> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<Notification> notificationList) {
        this.notificationList = notificationList;
    }
    
}
