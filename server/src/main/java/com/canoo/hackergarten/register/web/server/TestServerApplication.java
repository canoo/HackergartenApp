package com.canoo.hackergarten.register.web.server;

import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.resource.Directory;
import org.restlet.routing.Router;

public class TestServerApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());

		router.attachDefault(new Directory(getContext(), "war:///"));
//		router.attach("/contacts/123", ContactServerResource.class);

		router.attach("/user/", UserServerResource.class);
        router.attach("/event/", EventServerResource.class);
        router.attach("/checkin/", CheckInServerResource.class);

		return router;
	}

}
