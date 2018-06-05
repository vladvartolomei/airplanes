package com.play.airplanes.domain;

public class Game {

    private UserSession playerA;

    private UserSession playerB;

    private String gameState;

    public UserSession getPlayerA() {
        return playerA;
    }

    public void setPlayerA(UserSession playerA) {
        this.playerA = playerA;
    }

    public UserSession getPlayerB() {
        return playerB;
    }

    public void setPlayerB(UserSession playerB) {
        this.playerB = playerB;
    }

    public String getGameState() {
        return gameState;
    }

    public void setGameState(String gameState) {
        this.gameState = gameState;
    }
}
