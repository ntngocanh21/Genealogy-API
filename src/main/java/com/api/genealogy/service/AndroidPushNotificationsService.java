package com.api.genealogy.service;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;

public interface AndroidPushNotificationsService {
	public CompletableFuture<String> send(HttpEntity<String> entity);
}
