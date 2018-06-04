package com.play.airplanes;

public enum Commands {

    LOGIN("LOGIN",0),
    LOGOUT("LOGOUT",1),
    CHALLENGE("CHALLENGE",2),
    SETUP_PLANE("SETUP_PLANE",3),
    SETUP_COMPLETE("SETUP_COMPLETE",4),
    HIT("HIT",5);

    private String value;
    private int commandId;


    Commands(String value) {
        this.value = value;
    }

    Commands(String value, int i) {
        this.commandId = i;
        Commands.values();
    }

    Commands(int i){
        this.commandId= i;
    }
}
