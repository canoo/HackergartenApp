package org.hackergarten.gwt.hackergarten.client.service.domain;

import java.io.Serializable;

//TODO: Mock!
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String company;
	private String blogUrl;
	private String twitterUrl;
	private int checkinCount;

	public String getName() {
		return name;
	}

	public void setName(String inName) {
		name = inName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getBlogUrl() {
		return blogUrl;
	}

	public void setBlogUrl(String blogUrl) {
		this.blogUrl = blogUrl;
	}

	public String getTwitterUrl() {
		return twitterUrl;
	}

	public void setTwitterUrl(String twitterUrl) {
		this.twitterUrl = twitterUrl;
	}

	public int getCheckinCount() {
		return checkinCount;
	}

	public void setCheckinCount(int inCheckinCount) {
		checkinCount = inCheckinCount;
	}
}
