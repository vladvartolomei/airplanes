package com.play.airplanes.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.PrintWriter;

public class UserSession {

    //ignored from marshaling
    @JsonIgnore
    private static final Logger logger = LoggerFactory.getLogger(UserSession.class);
    @JsonIgnore
    private PrintWriter userOutputStream;

    //marshaled properties
    private String sessionId;
    private String userName;
    private boolean isLoggedIn = false;

    public UserSession(String sessionId) throws Exception {
        if (null == sessionId)
            throw new Exception("Cannot create new session without session id");

        this.sessionId = sessionId;
        logger.info("New session created "+sessionId);
    }

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

    public PrintWriter getUserOutputStream() {
        return userOutputStream;
    }

    public void setUserOutputStream(PrintWriter userOutputStream) {
        this.userOutputStream = userOutputStream;
    }
}
