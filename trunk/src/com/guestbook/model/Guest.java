package com.guestbook.model;

import java.io.Serializable;
import java.util.Set;

public class Guest implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;	
	private String firstName;
	private String lastName;
	private Set<Entry> entries;

	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Set<Entry> getEntries() {
		return entries;
	}
	public void setEntries(Set<Entry> entries) {
		this.entries = entries;
	}

}
