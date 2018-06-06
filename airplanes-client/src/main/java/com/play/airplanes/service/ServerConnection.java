package com.play.airplanes.service;

import com.play.airplanes.controller.AirplanesController;
import com.play.airplanes.listener.ServerConnectionHandler;
import com.play.airplanes.support.AlertService;
import javafx.application.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ServerConnection {

    private static final Logger logger = LoggerFactory.getLogger(ServerConnection.class);


    AirplanesController airplanesController;

    private String serverAddress = "localhost";

    private String serverPort = "6666";

    private Socket echoSocket;
    private ServerConnectionHandler connectionHandler;

    @PostConstruct
    public void postConstruct(){
        int portNumber = Integer.parseInt(serverPort);
        Socket echoSocket;
        PrintWriter out;
        BufferedReader in;

        try{
            this.echoSocket = new Socket(serverAddress, portNumber);
            out = new PrintWriter(this.echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(this.echoSocket.getInputStream()));
            //this thread is responsible for closing the socket and the streams
            this.connectionHandler = new ServerConnectionHandler(this.echoSocket, out, in);
            Platform.runLater(this.connectionHandler);

        } catch (UnknownHostException e) {
            logger.warn("Cannot connect to server: Unknown host",e);
        } catch (ConnectException e){
            AlertService.showErrorAlertAndExit(e);
            logger.warn("Cannot connect to server",e);
        } catch (IOException e) {
            logger.warn("Cannot read/write to server",e);
        }
    }

    public ServerConnectionHandler getConnectionHandler() {
        return connectionHandler;
    }
}
