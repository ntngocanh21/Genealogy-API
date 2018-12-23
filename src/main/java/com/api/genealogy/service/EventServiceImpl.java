package com.api.genealogy.service;

import com.api.genealogy.constant.HTTPCodeResponse;
import com.api.genealogy.entity.EventEntity;
import com.api.genealogy.model.Event;
import com.api.genealogy.repository.*;
import com.api.genealogy.service.response.EventResponse;
import com.api.genealogy.service.response.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired 
	private BranchRepository branchRepository;

	@Override
	public EventResponse pushEvent(Event event, String username) {
		EventResponse eventResponse = new EventResponse();
		EventEntity eventEntity = parseEventToEventEntity(event);
		eventEntity.setUserCreatedEventEntity(userRepository.findUserEntityByUsername(username));
		EventEntity newEvent = eventRepository.save(eventEntity);
		ArrayList<Event> events = new ArrayList<>();
		Event createdEvent = parseEventEntityToEvent(newEvent);
		events.add(createdEvent);
		eventResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
		eventResponse.setEventList(events);
		return eventResponse;
	}

	@Override
	public EventResponse getCreatedEvent(String currentUserName, Integer branchId) {
		EventResponse eventResponse = new EventResponse();
		List<EventEntity> createdEventEntities = eventRepository.findEventEntityByUserCreatedEventEntity_UsernameAndBranchEventEntity_IdOrderByDateDesc(currentUserName, branchId);
		List<Event> eventList = parseListEventEntityToListEvent(createdEventEntities);
		eventResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
		eventResponse.setEventList(eventList);
		return eventResponse;
	}

	@Override
	public EventResponse getEvents(Integer branchId) {
		EventResponse eventResponse = new EventResponse();
		List<EventEntity> createdEventEntities = eventRepository.findEventEntityByBranchEventEntity_IdOrderByDate(branchId);
		List<Event> eventList = parseListEventEntityToListEvent(createdEventEntities);
		eventResponse.setError(new MessageResponse(HTTPCodeResponse.SUCCESS,"Success"));
		eventResponse.setEventList(eventList);
		return eventResponse;
	}

	private EventEntity parseEventToEventEntity(Event event) {
		EventEntity eventEntity = new EventEntity();
		eventEntity.setContent(event.getContent());
		eventEntity.setDate(event.getDate());
		if(event.getUserId() != null){
			eventEntity.setUserCreatedEventEntity(userRepository.findUserEntityById(event.getUserId()));
		}
		eventEntity.setBranchEventEntity(branchRepository.findBranchEntityById(event.getBranchId()));
		return eventEntity;
	}

	public static Event parseEventEntityToEvent(EventEntity eventEntity) {
		Event event = new Event();
		event.setContent(eventEntity.getContent());
		event.setDate(eventEntity.getDate());
		event.setId(eventEntity.getId());
		event.setBranchId(eventEntity.getBranchEventEntity().getId());
		return event;
	}

	public static List<Event> parseListEventEntityToListEvent(List<EventEntity> eventEntities) {
		List<Event> events = new ArrayList<>();
		for (EventEntity eventEntity : eventEntities) {
			Event event = parseEventEntityToEvent(eventEntity);
			events.add(event);
		}
		return events;
	}

	@Override
	public List<Event> getAllEventFromSystem() {
		List<EventEntity> eventEntities = (List<EventEntity>) eventRepository.findAll();
		List<Event> eventList = parseListEventEntityToListEvent(eventEntities);
		return eventList;
	}
}
