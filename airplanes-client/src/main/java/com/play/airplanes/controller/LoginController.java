package com.play.airplanes.controller;

import com.play.airplanes.AirplanesClientApplication;
import com.play.airplanes.service.AwesomeActionService;
import com.play.airplanes.support.FXMLController;
import com.play.airplanes.view.AirplanesView;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;

@FXMLController
public class LoginController {

    @FXML
    private Label helloLabel;

    @FXML
    private TextField nameField;

    // Be aware: This is a Spring bean. So we can do the following:
    @Autowired
    private AwesomeActionService actionService;

    @FXML
    private void setHelloText(final Event event) {
        final String textToBeShown = actionService.processName(nameField.getText());
        helloLabel.setText(textToBeShown);
    }

    @FXML
    private void login(final Event event){
        AirplanesClientApplication.changeView(AirplanesView.class);
    }
}