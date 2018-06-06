package com.play.airplanes.controller;

import com.play.airplanes.domain.DB;
import com.play.airplanes.domain.UserSession;
import com.play.airplanes.listener.ServerConnectionHandler;
import com.play.airplanes.service.ServerConnection;
import com.play.airplanes.support.FXMLController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FXMLController
public class AirplanesController {

    private static final Logger logger = LoggerFactory.getLogger(AirplanesController.class);

    volatile ServerConnection serverConnection;
    volatile ServerConnectionHandler serverConnectionHandler;

    @FXML
    private volatile TableView playersList;

    @FXML
    private volatile Label gameStateLabel;

    @FXML
    public void initialize() {
        serverConnection = new ServerConnection();
        serverConnection.postConstruct();

        serverConnectionHandler = serverConnection.getConnectionHandler();

        //timer updates
        Timeline oneSecTimeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                logger.info("Looping");
                if ( DB.isDirty()){
                    if (DB.isUserSessionReceived()){
                        updateUserInfo(DB.getUserSession());
                    }
                }
            }
        }));
        oneSecTimeline.setCycleCount(Timeline.INDEFINITE);
        oneSecTimeline.play();
    }

    public void updateUserInfo(UserSession userSession) {
        gameStateLabel.setText("Jucator: "+userSession.getUserName()+" ("+userSession.getSessionId()+")");
    }
}
