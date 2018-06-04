package com.play.airplanes.service;

import org.springframework.stereotype.Component;

@Component
public class GreeterActionService implements AwesomeActionService {


    @Override
    public String processName(String name) {
        return "Hello fucker "+name;
    }
}
