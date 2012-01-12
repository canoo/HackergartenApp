package com.canoo.hackergarten.register.web;

import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
/**
 * @author edewit
 */
public interface CheckInResouce {

    @Put
    void checkIn(String email, Long eventId);

    @Post
    void checkIn(Representation checkIn);
}
