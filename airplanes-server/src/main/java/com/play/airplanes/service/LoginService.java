package com.play.airplanes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        userName.replaceAll("^![0-9a-zA-Z]$","");

        userSession.setUserName(userName);

        //return to client
        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(userSession);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        userSession.getUserOutputStream().println(json);

        //append to players
        airplanesGameService.addNewPlayerToGame(userSession);

    }

}
