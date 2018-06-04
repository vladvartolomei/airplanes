package com.play.airplanes.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.Socket;


public class WorkerRunnable implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(WorkerRunnable.class);

    protected Socket clientSocket = null;
    protected UserSession userSession   = null;

    public WorkerRunnable(Socket clientSocket, UserSession userSession) {
        this.clientSocket = clientSocket;
        this.userSession = userSession;


    }

    public void run() {
        try (
                BufferedReader input  = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {

            String command = "";
            while ( !command.equals("EXIT")){
                command = input.readLine();
                callCommandListener(userSession, command);
                output.println("Mirror - "+command);
            }
            clientSocket.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    private void callCommandListener(UserSession userSession, String requestBody) {
        if (requestBody.isEmpty())
            return;
        logger.info("NEW REQUEST -> Client: {} | Request: {}", userSession.getSessionId(), requestBody);

        switch (requestBody){
            case "LOGIN":
                break;
            case "CHALLENGE":
                break;
        }
    }

}