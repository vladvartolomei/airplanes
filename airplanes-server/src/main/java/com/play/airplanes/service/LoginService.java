package com.play.airplanes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.play.airplanes.domain.Command;
import com.play.airplanes.domain.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginService {

    private static final Logger logger = LoggerFactory.getLogger(LoginService.class);

    private AirplanesGameService airplanesGameService;

    public LoginService(AirplanesGameService airplaneGameService){
        this.airplanesGameService = airplaneGameService;
    }

    public void loginUser(UserSession userSession, String requestBody){
        if (requestBody.length() <=2)
            throw new IllegalArgumentException("Login request must contain username");
        String userName = requestBody.substring(2);
        //sanitize input
        sanitizeInput(userName);

        userSession.setUserName(userName);
        userSession.setLoggedIn(true);
        //return to client
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(userSession);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        userSession.getUserOutputStream().println(Command.USER_AUTHENTICATED.getValue()+":"+json);
        logger.info("Server reply: {}",Command.USER_AUTHENTICATED.getValue()+":"+json);
        //append to players
        airplanesGameService.addNewPlayerToGame(userSession);

    }

    private void sanitizeInput(String input) {
        input.replaceAll("^![0-9a-zA-Z]$","");
    }

    public void logoutUser(String requestBody) {
        String clientSessionId = requestBody.substring(2);


        airplanesGameService.removeUserFromTheGame(clientSessionId);
    }
}
