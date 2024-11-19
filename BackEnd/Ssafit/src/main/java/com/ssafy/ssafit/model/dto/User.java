package com.ssafy.ssafit.model.dto;

public class User {
    private Integer userId;
    private String userEmail;
    private String userPassword;
    private String userName;
    private String userNickname;
    private boolean userStatus;
    private String createdAt;
    private String profileImgPath;

    public User() {
        // TODO Auto-generated constructor stub
    }

    public User(String userEmail, String userName, String userNickname) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        
    }
    
    public User(String userEmail, String userName, String userNickname, String profileImgPath) {
        this.userEmail = userEmail;
        this.userName = userName;
        this.userNickname = userNickname;
        this.profileImgPath = profileImgPath;
    }

    public User(Integer userId, String userEmail, String userPassword, String userName, String userNickname,
            boolean userStatus, String createdAt, String profileImgPath) {
        super();
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userStatus = userStatus;
        this.createdAt = createdAt;
        this.profileImgPath = profileImgPath;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNickname() {
        return userNickname;
    }

    public void setUserNickname(String userNickname) {
        this.userNickname = userNickname;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfileImgPath() {
        return profileImgPath;
    }

    public void setProfileImgPath(String profileImgPath) {
        this.profileImgPath = profileImgPath;
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", userEmail=" + userEmail + ", userPassword=" + userPassword + ", userName="
                + userName + ", userNickname=" + userNickname + ", userStatus=" + userStatus + ", createdAt="
                + createdAt + ", profileImgPath=" + profileImgPath + "]";
    }

}
