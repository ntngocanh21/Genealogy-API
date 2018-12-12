package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "notification_type")
public class NotificationTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "notification_name")
    private String notificationName;

    @OneToMany(mappedBy = "notificationTypeEntity", cascade=CascadeType.ALL)
    private List<NotificationsEntity> notificationsEntities;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNotificationName() {
        return notificationName;
    }

    public void setNotificationName(String notificationName) {
        this.notificationName = notificationName;
    }

    public List<NotificationsEntity> getNotificationsEntities() {
        return notificationsEntities;
    }

    public void setNotificationsEntities(List<NotificationsEntity> notificationsEntities) {
        this.notificationsEntities = notificationsEntities;
    }
}

