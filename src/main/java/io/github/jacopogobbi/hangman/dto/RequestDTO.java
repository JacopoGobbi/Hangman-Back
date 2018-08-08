package io.github.jacopogobbi.hangman.dto;

import javax.validation.constraints.NotNull;

/**
 * Request DTO.
 * Used to map requests
 */
public class RequestDTO {
    @NotNull
    private String sessionCookie;
    private String playerName;
    private Character letterAttempt;

    public String getSessionCookie() {
        return sessionCookie;
    }

    public void setSessionCookie(String sessionCookie) {
        this.sessionCookie = sessionCookie;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Character getLetterAttempt() {
        return letterAttempt;
    }

    public void setLetterAttempt(Character letterAttempt) {
        this.letterAttempt = letterAttempt;
    }
}
