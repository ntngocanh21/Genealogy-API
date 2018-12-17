package com.api.genealogy.service.response;

import java.util.List;

import com.api.genealogy.model.Event;

public class EventResponse {

	private MessageResponse error;
    private List<Event> eventList;

    public EventResponse() {
    }

    public EventResponse(MessageResponse error, List<Event> eventList) {
        this.error = error;
        this.eventList = eventList;
    }

    public MessageResponse getError() {
        return error;
    }

    public void setError(MessageResponse error) {
        this.error = error;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }
	
}
