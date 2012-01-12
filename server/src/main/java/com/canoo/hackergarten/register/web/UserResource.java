package com.canoo.hackergarten.register.web;

import org.restlet.resource.Delete;
import org.restlet.resource.Put;

import com.canoo.hackergarten.register.domain.User;

public interface UserResource {

	@Put
	void registerUser(User user);
	
	@Delete
	void remove(Long id);
}
