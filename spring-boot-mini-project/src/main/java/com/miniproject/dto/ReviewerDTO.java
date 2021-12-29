package com.miniproject.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ReviewerDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	@NotNull
	@Size(min = 5, max = 200, message = "full name should be in range of 5 to 200")
	private String fullName;
	@Email
	public String email;
	private Boolean active;
	@NotNull
	@Size(min = 3, max = 200, message = "address should be in range of 3 to 200")
	private String address;
	@NotNull
	@Size(min = 3, max = 200, message = "occupation should be in range of 3 to 200")
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
