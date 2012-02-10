package org.hackergarten.gwt.hackergarten.client.service.domain;

import java.io.Serializable;
import java.util.Date;

public class CheckIn implements Serializable {
    private static final long serialVersionUID = 1L;

	private Long id;

	private Long userId;

	private Long eventId;

	private Date checkinTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long inEventId) {
        eventId = inEventId;
    }

    public Date getCheckinTime() {
		return checkinTime;
	}

	public void setCheckinTime(Date checkinTime) {
		this.checkinTime = checkinTime;
	}
}
