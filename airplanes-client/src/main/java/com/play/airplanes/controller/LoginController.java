package com.play.airplanes.controller;

import com.play.airplanes.AirplanesClientApplication;
import com.play.airplanes.domain.DB;
import com.play.airplanes.service.AwesomeActionService;
import com.play.airplanes.support.FXMLController;
import com.play.airplanes.view.AirplanesView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@FXMLController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @FXML
    private Label helloLabel;

    @FXML
    private TextField nameField;

    // Be aware: This is a Spring bean. So we can do the following:
    @Autowired
    private AwesomeActionService actionService;

    @FXML
    private void login(final Event event){
        DB.storeUserName(nameField.getText());
        logger.info("Login info stored {}",DB.getStoredUserName());
        AirplanesClientApplication.changeView(AirplanesView.class);
    }
}