package com.canoo.hackergarten.register.web.server;

import com.canoo.hackergarten.register.domain.CheckIn;
import com.canoo.hackergarten.register.domain.Event;
import com.canoo.hackergarten.register.domain.User;
import com.canoo.hackergarten.register.web.CheckInResouce;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;


/**
 * @author edewit
 */
public class CheckInServerResource extends ServerResource implements CheckInResouce {

    @Put
    public void checkIn(String email, Long eventId) {
        EntityManager entityManager = null;
        try {
            entityManager = EMF.get().createEntityManager();
            final CheckIn checkIn = new CheckIn();
            checkIn.setCheckinTime(new Date());

            final Query query = entityManager.createQuery("select u from User u where u.email = :email");
            query.setParameter("email", email);
            final User user = (User) query.getSingleResult();
            checkIn.setUserId(user.getId().getId());
            final Key key = KeyFactory.createKey(Event.class.getSimpleName(), eventId);
            Event event = entityManager.find(Event.class, key);
            checkIn.setEventId(event.getId().getId());

            entityManager.persist(checkIn);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }

    }

    @Post
    public void checkIn(Representation inRepresentation) {
        final Form form = new Form(inRepresentation);
        String email = form.getFirstValue("email");
        String eventId = form.getFirstValue("eventId");
        checkIn(email, Long.valueOf(eventId));
    }
}
