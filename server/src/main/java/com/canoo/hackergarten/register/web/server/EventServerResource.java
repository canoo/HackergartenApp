package com.canoo.hackergarten.register.web.server;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

//import org.apache.commons.beanutils.BeanUtils;
import com.canoo.hackergarten.register.web.EventResource;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.Delete;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;

import com.canoo.hackergarten.register.domain.Event;

/**
 * @author edewit
 */
public class EventServerResource extends ServerResource implements EventResource {

	@Put
	public void createEvent(Event event) {
		EntityManager entityManager = null;
		try {
			entityManager = EMF.get().createEntityManager();
			entityManager.persist(event);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Post
	public void createEvent(Representation event) {
        Event entity = new Event();
        FormPostHandler.getInstance().copyFormParameters(entity, new Form(event));

        createEvent(entity);
	}

    @Delete
	public void remove(Long id) {
		EntityManager entityManager = null;
		try {
			entityManager = EMF.get().createEntityManager();
			final Event event = entityManager.find(Event.class, id);
			entityManager.remove(event);
		} finally {
			if (entityManager != null) {
				entityManager.close();
			}
		}
	}

	@Get
	public List<Event> listUpcomingEvents() {
		EntityManager entityManager = null;
		try {
			entityManager = EMF.get().createEntityManager();
            entityManager.getTransaction().begin();
			Query query = entityManager.createQuery("select e from Event e where e.start > :today");
			query.setParameter("today", new Date());
			return query.getResultList();
		} finally {
			if (entityManager != null) {
                entityManager.getTransaction().commit();
				entityManager.close();
			}
		}
	}

    @Get
    public List<Event> list() {
        EntityManager entityManager = null;
        try {
            entityManager = EMF.get().createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("select e from Event e where e.start = :today");
            query.setParameter("today", new Date());
            return query.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.getTransaction().commit();
                entityManager.close();
            }
        }
    }

}
