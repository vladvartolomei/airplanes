package com.play.airplanes;

import com.play.airplanes.support.AbstractJavaFxApplicationSupport;
import com.play.airplanes.view.LoginView;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirplanesClientApplication extends AbstractJavaFxApplicationSupport {
	public static void main(String[] args) {
		launch(AirplanesClientApplication.class, LoginView.class, args);
	}
}