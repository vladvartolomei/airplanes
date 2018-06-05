package com.play.airplanes.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Command {

    LOGIN(0L),
    LOGOUT(1L),
    GET_PLAYERS_SUBSCRIPTION(2L),
    START_GAME(3L);

    private Long value;
    private static final Map<Long,Command> lookup = new HashMap<>();

    static {
        for(Command s : EnumSet.allOf(Command.class))
            lookup.put(s.getValue(), s);
    }

    public static Command instanceOf(Long i){
        return lookup.get(i);
    }


    Command(Long value) {
        this.value = value;
    }

    public Long getValue(){
        return this.value;
    }



}
