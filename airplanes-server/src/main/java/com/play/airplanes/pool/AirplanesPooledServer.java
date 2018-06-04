package com.play.airplanes.pool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class AirplanesPooledServer {

    private static final Logger logger = LoggerFactory.getLogger(AirplanesPooledServer.class);

    @Value("${airplanes.server.port}")
    protected int          serverPort;
    protected ServerSocket serverSocket = null;
    protected boolean      isStopped    = false;
    protected Thread       runningThread= null;
    protected ExecutorService threadPool =
            Executors.newFixedThreadPool(10);

    @PostConstruct
    public void run(){
        synchronized(this){
            this.runningThread = Thread.currentThread();
        }
        logger.info("Airplanes Server Socket: STARTING");
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            PrintWriter pr;
            try{
                clientSocket = this.serverSocket.accept();
                pr = new PrintWriter(clientSocket.getOutputStream());

            } catch (IOException e) {
                if(isStopped()) {
                    logger.info("Airplanes Server Socket: STOPPED");
                    break;
                }
                throw new RuntimeException(
                        "Error accepting client connection", e);
            }

            try {
                this.threadPool.execute(
                        new WorkerRunnable(clientSocket,
                                new UserSession(getClientUniqueId()))
                );
            }
            catch (Exception e){
                pr.println("FAILE TO CREATE NEW SESSION");
                try {
                    clientSocket.close();
                    logger.info("Airplanes Server Socket: CLIENT DISCONNECTED");
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        this.threadPool.shutdown();
        logger.info("Airplanes Server Socket: SHUTDOWN");
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    @PreDestroy
    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
            logger.info("Airplanes Server Socket: STOP");
        } catch (IOException e) {
            throw new RuntimeException("Error closing server", e);
        }
    }

    private void openServerSocket() {
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
            logger.info("Airplanes Server Socket: OPENED");
        } catch (IOException e) {
            throw new RuntimeException("Cannot open port", e);
        }
    }

    public String getClientUniqueId() {
        Date current = new Date();
        return (current.getTime()+"_"+ Double.valueOf(Math.random()/1000).toString());
    }
}