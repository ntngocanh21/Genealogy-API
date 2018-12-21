package com.api.genealogy.service;

import com.api.genealogy.model.Event;
import com.api.genealogy.service.response.EventResponse;

import java.util.List;

public interface EventService {
	EventResponse pushEvent(Event event, String username);
	EventResponse getCreatedEvent(String currentUserName, Integer branchId);
	EventResponse getEvents(Integer branchId);
	List<Event> getAllEventFromSystem();
}
