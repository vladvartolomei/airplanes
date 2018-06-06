package com.play.airplanes.domain;

public class DB {

    private static volatile String userName;
    private static volatile UserSession userSession;
    private static volatile boolean dirty = false;
    private static volatile boolean loginReceived = false;
    private static volatile boolean userListUpdated = false;

    public synchronized static void storeUserName(String userName){
        DB.userName = userName;
    }

    public synchronized static String getStoredUserName(){
        return DB.userName;
    }



    public synchronized static void setUserSession(UserSession userSession){
        DB.userSession = userSession;
        DB.loginReceived = true;
        DB.dirty = true;
    }

    public synchronized static UserSession getUserSession(){
        DB.loginReceived = false;
        DB.dirty = false;
        return DB.userSession;
    }

    public synchronized static boolean isDirty() {
        return DB.dirty;
    }

    public synchronized static boolean isUserSessionReceived(){
        return DB.loginReceived;
    }

}
