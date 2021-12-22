package com.miniproject.dto;

import java.util.List;

public class GlobalResponseFormat {
	private boolean error;
	private String message;
	private List<?> data;
	public GlobalResponseFormat(boolean error, String message, List<?> data) {
		super();
		this.error = error;
		this.message = message;
		this.data = data;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<?> getData() {
		return data;
	}
	public void setData(List<?> data) {
		this.data = data;
	}

}
