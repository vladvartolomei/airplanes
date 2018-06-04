package com.play.airplanes;

import com.play.airplanes.support.AbstractJavaFxApplicationSupport;
import com.play.airplanes.view.HelloworldView;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirplanesClientApplication extends AbstractJavaFxApplicationSupport {
	public static void main(String[] args) {
		launch(AirplanesClientApplication.class, HelloworldView.class, args);
	}
}