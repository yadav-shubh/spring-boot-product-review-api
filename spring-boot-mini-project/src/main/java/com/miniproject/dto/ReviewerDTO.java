package com.miniproject.dto;

import java.io.Serializable;

public class ReviewerDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String fullName;
	public String email;
	private Boolean active;
	private String address;
	private String occupation;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	@Override
	public String toString() {
		return "ReviewerDTO [id=" + id + ", fullName=" + fullName + ", email=" + email + ", active=" + active
				+ ", address=" + address + ", occupation=" + occupation + "]";
	}

}
