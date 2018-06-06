package com.play.airplanes.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ServerCommands {

    //request codes
    LOGIN(0L),
    LOGOUT(1L),
    GET_PLAYERS_SUBSCRIPTION(2L),
    START_GAME(3L),
    PLAYER_SET_UP_FINISHED(4L),


    //response codes
    USER_AUTHENTICATED(5L),
    NEW_USER_ENTERED(6L),
    NEW_GAME_ACCEPTED(7L),
    SET_UP_FINISHED(8L),
    NEW_HIT(9L),
    GAME_FINISHED(10L);

    private Long value;
    private static final Map<Long,ServerCommands> lookup = new HashMap<>();

    static {
        for(ServerCommands s : EnumSet.allOf(ServerCommands.class))
            lookup.put(s.getValue(), s);
    }

    public static ServerCommands instanceOf(Long i){
        return lookup.get(i);
    }


    ServerCommands(Long value) {
        this.value = value;
    }

    public Long getValue(){
        return this.value;
    }
}
