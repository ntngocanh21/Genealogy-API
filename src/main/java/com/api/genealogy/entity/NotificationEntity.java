package com.api.genealogy.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "notification")
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title")
    private String title;

	@Column(name = "date")
	private Date date;

    @Column(name = "content", columnDefinition="TEXT")
    private String content;

    @Column(name = "read_status")
    private Boolean readStatus;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public UserEntity userNotificationEntity;

    @ManyToOne
    @JoinColumn(name = "notification_type_id", nullable = false)
    public NotificationTypeEntity notificationTypeEntity;

    public NotificationEntity() {
    }
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(Boolean readStatus) {
		this.readStatus = readStatus;
	}

	public UserEntity getUserNotificationEntity() {
		return userNotificationEntity;
	}

	public void setUserNotificationEntity(UserEntity userNotificationEntity) {
		this.userNotificationEntity = userNotificationEntity;
	}

	public NotificationTypeEntity getNotificationTypeEntity() {
		return notificationTypeEntity;
	}

	public void setNotificationTypeEntity(NotificationTypeEntity notificationTypeEntity) {
		this.notificationTypeEntity = notificationTypeEntity;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}

