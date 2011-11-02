package com.guestbook.model;

import java.io.Serializable;


public class Entry implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Integer id;	
	private Guest guest;
	private String content;
	
	
	
	
	public Guest getGuest() {
		return guest;
	}
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
