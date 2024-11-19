package com.ssafy.ssafit.model.dto;

public class KakaoUser {
	private String email;
	private String name;
	private String provider;
	private String profileImgPath;

	public KakaoUser() {
		// TODO Auto-generated constructor stub
	}

	public KakaoUser(String email, String name, String provider, String profileImgPath) {
		super();
		this.email = email;
		this.name = name;
		this.provider = provider;
		this.profileImgPath = profileImgPath;
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

	public String getProfileImgPath() {
		return profileImgPath;
	}

	public void setProfileImgPath(String profileImgPath) {
		this.profileImgPath = profileImgPath;
	}

	@Override
	public String toString() {
		return "KakaoUser [email=" + email + ", name=" + name + ", provider=" + provider + ", profileImgPath="
				+ profileImgPath + "]";
	}
	
	
	
}
