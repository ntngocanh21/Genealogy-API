package com.api.genealogy.service;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AndroidPushNotificationsServiceImpl implements AndroidPushNotificationsService{

	private static final String FIREBASE_SERVER_KEY = "AAAAH1tfbBA:APA91bEhhQf0hS1Tipgjm6MOD9FbgXGLT_DKJ5KwRguXqP3VUpGMALax8Jy2NaYajMJVHdJfXpnlPyP989vXjXyUSyXgXJLCYHZY-TrnG8KymNfpost1XHaeLSLHnythroVy2AxrjKEY";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";
	
	@Async
	@Override
	public CompletableFuture<String> send(HttpEntity<String> entity) {
 
		RestTemplate restTemplate = new RestTemplate();
 
		/**
		https://fcm.googleapis.com/fcm/send
		Content-Type:application/json
		Authorization:key=FIREBASE_SERVER_KEY*/
 
		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);
 
		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);
 
		return CompletableFuture.completedFuture(firebaseResponse);
	}
	
}
