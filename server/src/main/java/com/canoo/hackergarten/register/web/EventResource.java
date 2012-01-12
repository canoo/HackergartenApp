package com.canoo.hackergarten.register.web;

import java.util.List;

import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;

import com.canoo.hackergarten.register.domain.Event;

public interface EventResource {

	@Put
	void createEvent(Event event);
	
    @Post
    void createEvent(Representation event);

	@Delete
	void remove(Long id);

    @Get
    List<Event> listUpcomingEvents();
}
