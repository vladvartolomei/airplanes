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
                PrintWriter outputWriter = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {

            String command = "";
            while ( !command.equals("EXIT")){
                command = input.readLine();
                callCommandListener(userSession, command, outputWriter);
            }
            clientSocket.close();
        } catch (IOException e) {
            //report exception somewhere.
            e.printStackTrace();
        }
    }

    /**
     * Translate request command into HEADER and BODY eg:
     *      0|myname
     * translates to
     *      Header
     *          0 ->  {@link Command#LOGIN }
     *      Body
     *          user name
     *
     *
     * @param userSession
     * @param requestBody
     */
    private void callCommandListener(UserSession userSession, String requestBody, PrintWriter outputStreamWriter) {
        if (requestBody.isEmpty())
            return;
        logger.info("REQUEST->Client: {}|Request: {}", userSession.getSessionId(), requestBody);

        Command command = Command.instanceOf(Long.valueOf(requestBody.substring(0,1)));

        switch (command){
            case LOGIN:
                break;
            case LOGOUT:
                break;
            default:
                outputStreamWriter.println("WRONG COMMAND");
                break;

        }
    }

}