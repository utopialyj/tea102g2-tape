package com.chat.model;

import java.util.Collection;
import java.util.Set;

public class State {
	private String type;
	// the user changing the state
	private String user;
	// total users
	private Collection<String> users;

	public State(String type, String user, Collection<String> users) {
		super();
		this.type = type;
		this.user = user;
		this.users = users;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Collection<String> getUsers() {
		return users;
	}

	public void setUsers(Collection<String> users) {
		this.users = users;
	}

}
