package com.play.airplanes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.play.airplanes.domain.Game;
import com.play.airplanes.domain.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class AirplanesGameService {

    private static final Logger logger = LoggerFactory.getLogger(AirplanesGameService.class);

    private final List<UserSession> userSessions = new CopyOnWriteArrayList<>();

    private final List<Game> userGames = new CopyOnWriteArrayList<>();

    public synchronized void addNewPlayerToGame(UserSession userSession){
        //validate user session
        if (null == userSession.getSessionId()
            || null == userSession.getUserName()
        ){
            logger.warn("Cannot add user to gamers due to incomplete data. UserSession {}",userSession);
            return;
        }

        //check if user session is already added
        for (UserSession existingSession: userSessions){
            if (userSession.getSessionId().equals(existingSession.getSessionId())){
                logger.warn("Same user session already exists");
                return;
            }
        }

        //valid user session -> add to players list -> broadcast message to all clients that a new player has joined
        userSessions.add(userSession);
        broadcastNewPlayerJoined(userSession);
    }

    private void broadcastNewPlayerJoined(UserSession userSession){

        ObjectMapper om = new ObjectMapper();
        String userSessionJSON = null;
        try {
            userSessionJSON = om.writeValueAsString(userSession);
        } catch (JsonProcessingException e) {
            logger.warn("cannot serialize user session {}", userSession);
        }

        for (UserSession existingSession: userSessions){
            if ( !userSession.getSessionId().equals(existingSession.getSessionId())){
                existingSession.getUserOutputStream().println("NEW_USER_JOINED:"+userSessionJSON);
            }
        }
    }
}
