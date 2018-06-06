package com.play.airplanes.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Command {

    //request codes
    LOGIN(0L),
    LOGOUT(1L),
    GET_PLAYERS_SUBSCRIPTION(2L),
    START_GAME(3L),
    PLAYER_SET_UP_FINISHED(4L),


    //response codes
    USER_AUTHENTICATED(5L),
    NEW_USER_ENTERED(6L),
    USER_LEFT(7L),
    SET_UP_FINISHED(8L),
    NEW_HIT(9L),
    GAME_FINISHED(10L);

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
