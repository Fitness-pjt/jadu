package com.ssafy.ssafit.model.dto;

public class GoogleUser {
	private String email;
	private String name;
	private String provider;

	public GoogleUser() {
		// TODO Auto-generated constructor stub
	}

	public GoogleUser(String email, String name, String provider) {
		super();
		this.email = email;
		this.name = name;
		this.provider = provider;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}
}
