package com.play.airplanes.pool;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Command {

    LOGIN(0L),
    LOGOUT(1L),
    CHALLENGE(2L),
    SETUP_PLANE(3L),
    SETUP_COMPLETE(4L),
    HIT(8L);

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
