package com.play.airplanes.listener;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.play.airplanes.controller.AirplanesController;
import com.play.airplanes.domain.DB;
import com.play.airplanes.domain.ServerCommands;
import com.play.airplanes.domain.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerConnectionHandler implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(ServerConnectionHandler.class);
    PrintWriter out;
    BufferedReader in;
    Socket socket;


    public ServerConnectionHandler(Socket socket, PrintWriter out, BufferedReader in){
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        String inString = "";
        //first trigger loggin and then wait for server updates

        triggerLogin();

        while( !inString.equals("CONNECTION_CLOSED") ){
            try {
                inString = in.readLine();
                processServerResponse(inString);
            } catch (IOException e) {
                logger.warn("Connection lost",e);
                return;
            }
        }

        out.close();
        try {

            in.close();

        } catch (IOException e) {
            logger.warn("Cannot close input stream connection",e);
        }

        try{
            socket.close();
        } catch (IOException e) {
            logger.warn("Cannot close socket connection",e);
        }
    }

    private void processServerResponse(String inString) {
        if (null == inString || inString.isEmpty()) {
            logger.info("Empty message received");
            return;
        }

        sanitizeInput(inString);

        if (inString.startsWith(ServerCommands.USER_AUTHENTICATED.getValue()+":")){
            //this triggers view update
            DB.setUserSession(
                    transformLogin(inString.replace(ServerCommands.USER_AUTHENTICATED.getValue()+":",""))
            );
        }

    }

    public void triggerLogin(){
        out.println(ServerCommands.LOGIN.getValue()+ ":"+ DB.getStoredUserName());
        logger.info("Loggin triggered for {}",DB.getStoredUserName());
    }

    public UserSession transformLogin(String messageBody){
        ObjectMapper om = new ObjectMapper();
        UserSession userSession =null;
        try {
            userSession = om.readValue(messageBody, UserSession.class);
        } catch (IOException e) {
            logger.warn("Cannot decode user session response");
        }
        return userSession;
    }

    private void sanitizeInput(String input) {
        input.replaceAll("^![0-9a-zA-Z]$","");
    }

}
