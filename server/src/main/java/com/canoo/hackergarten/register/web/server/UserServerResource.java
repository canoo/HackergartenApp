package com.canoo.hackergarten.register.web.server;

import com.canoo.hackergarten.register.domain.User;
import com.canoo.hackergarten.register.web.UserResource;
import org.restlet.data.Form;
import org.restlet.representation.Representation;
import org.restlet.resource.*;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class UserServerResource extends ServerResource implements UserResource {

    @Put
    public void registerUser(User user) {
        EntityManager entityManager = null;
        try {
            entityManager = EMF.get().createEntityManager();
            entityManager.persist(user);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    @Post
    public void registerUser(Representation event) {
        Form form = new Form(event);
        User entity = new User();
        FormPostHandler.getInstance().copyFormParameters(entity, form);
        registerUser(entity);
    }

    @Get
    public List<User> hallOfFame() {
        EntityManager entityManager = null;
        try {
            entityManager = EMF.get().createEntityManager();
            entityManager.getTransaction().begin();
            Query query = entityManager.createQuery("select u from User u order by u.checkinCount desc");
            query.setMaxResults(20);
            return query.getResultList();
        } finally {
            if (entityManager != null) {
                entityManager.getTransaction().commit();
                entityManager.close();
            }
        }
    }

    @Delete
    public void remove(Long id) {
        EntityManager entityManager = null;
        try {
            entityManager = EMF.get().createEntityManager();
            final User user = entityManager.find(User.class, id);
            entityManager.remove(user);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

}
