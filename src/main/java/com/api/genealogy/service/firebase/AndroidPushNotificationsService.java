package com.api.genealogy.service.firebase;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

/**
 * https://fcm.googleapis.com/fcm/send Content-Type:application/json
 * Authorization:key=FIREBASE_SERVER_KEY
 */

public class AndroidPushNotificationsService {

	private static final String FIREBASE_SERVER_KEY = "AAAAfZmLHbk:APA91bENZdq1dO49OSIqCBZ82mbv3JntRitO2VQlxTozqEi7JVKZYamYhaAKt7wrO_HXPPAYfg7TNO4Xn_FztejeqZ6n-bbJG1i9uz3U61Iz5rN8t_3OUXbYe-9fK95i6he6panSORSZ";

	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {
		RestTemplate restTemplate = new RestTemplate();

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}
}
