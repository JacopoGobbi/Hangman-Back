package io.github.jacopogobbi.hangman.dto;

import io.github.jacopogobbi.hangman.domain.GameStatus;

import java.util.Set;

/**
 * Success DTO.
 * Used to provide a successful response
 */
public class SuccessDTO {
    private String cookie;
    private String playerName;
    private int gamesWon;
    private int attemptsLeft;
    private String uncompletedWord;
    private Set<Character> letterAttempts;
    private GameStatus gameStatus;

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public void setAttemptsLeft(int attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public void setUncompletedWord(String uncompletedWord) {
        this.uncompletedWord = uncompletedWord;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public Set<Character> getLetterAttempts() {
        return letterAttempts;
    }

    public void setLetterAttempts(Set<Character> letterAttempts) {
        this.letterAttempts = letterAttempts;
    }

    public String getCookie() {
        return cookie;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    public String getUncompletedWord() {
        return uncompletedWord;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }
}
