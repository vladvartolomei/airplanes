package com.play.airplanes.domain;

/**
 * Mapping object for user session from server
 */
public class UserSession {

    //ignored from marshaling

    //marshaled properties
    private String sessionId;
    private String userName;
    private boolean isLoggedIn;

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }
}
