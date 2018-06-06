package com.play.airplanes.pool;

import com.play.airplanes.domain.Command;
import com.play.airplanes.domain.UserSession;
import com.play.airplanes.service.AirplanesGameService;
import com.play.airplanes.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;

public class WorkerRunnable implements Runnable{

    protected Socket clientSocket = null;
    protected UserSession userSession   = null;

    private static final Logger logger = LoggerFactory.getLogger(WorkerRunnable.class);
    private final AirplanesGameService airplaneGameService;

    private LoginService loginService;

    public WorkerRunnable(Socket clientSocket, UserSession userSession,LoginService loginService, AirplanesGameService airplanesGameService) {
        this.clientSocket = clientSocket;
        this.userSession = userSession;
        this.airplaneGameService = airplanesGameService;
        this.loginService = loginService;
    }

    public void run() {
        try (
                BufferedReader input  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
            userSession.setUserOutputStream(outputWriter);
            String command = "";
            while ( !command.equals("EXIT")){
                command = input.readLine();
                callCommandListener(userSession, command);
            }
            clientSocket.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    /**
     * Translate request command
     * @param userSession
     * @param requestBody
     */
    private void callCommandListener(UserSession userSession, String requestBody) {
        if (null == requestBody || requestBody.isEmpty())
            return;
        logger.info("REQUEST->Client: {}|Request: {}", userSession.getSessionId(), requestBody);
        Command command = null;
        try {
             command = Command.instanceOf(Long.valueOf(requestBody.substring(0, 1)));
        }catch (Exception e){
            userSession.getUserOutputStream().println("Echo: "+requestBody);
            userSession.getUserOutputStream().println("Commands must start with a number(eg: 0: login, 1: logout, 2: get players list, 3: start game");
            return;
        }

        switch (command){
            case LOGIN:
                try {
                    loginService.loginUser(userSession, requestBody);
                }catch (IllegalArgumentException e){
                    //outputStreamWriter.println("Failed to login user");
                    logger.warn("Failed to LOGIN user: {}",e.getMessage(),e);
                }
                break;
            case LOGOUT:
                try{
                    loginService.logoutUser(requestBody);
                }catch (IllegalArgumentException e){
                    logger.warn("Failed to LOGOUT user");
                }
                break;
            case GET_PLAYERS_SUBSCRIPTION:
                break;
            case START_GAME:
                break;
            default:
                userSession.getUserOutputStream().println("WRONG COMMAND");
                break;

        }
    }

}