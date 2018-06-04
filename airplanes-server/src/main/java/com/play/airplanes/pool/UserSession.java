package com.play.airplanes.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserSession {

    private static final Logger logger = LoggerFactory.getLogger(UserSession.class);

    private String sessionId;
    private String userName;
    private boolean isLoggedIn = false;

    UserSession(String sessionId) throws Exception {
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
}
