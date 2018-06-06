package com.play.airplanes.support;

import javafx.application.Platform;
import javafx.scene.control.Alert;


public class AlertService {

    public static void showErrorAlertAndExit(Throwable throwable) {
        Alert alert = new Alert(Alert.AlertType.ERROR, "Oops! An unrecoverable error occurred.\n" +
                "Please contact your software vendor.\n\n" +
                "The application will stop now.\n\n" +
                "Error: " + throwable.getMessage());
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }

    public static void showErrorAlertAndExit(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR, message);
        alert.showAndWait().ifPresent(response -> Platform.exit());
    }
}
