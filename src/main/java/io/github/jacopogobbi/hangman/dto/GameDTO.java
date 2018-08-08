package io.github.jacopogobbi.hangman.dto;

import io.github.jacopogobbi.hangman.domain.GameStatus;

import java.util.Set;
/**
 * The Error DTO.
 * Used to map PlayersDTO games.
 */
public class GameDTO {
    private Integer id;
    private Set<Character> letterAttempts;
    private Integer attemptsLeft;
    private GameStatus gameStatus;

    public GameDTO() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Set<Character> getLetterAttempts() {
        return letterAttempts;
    }

    public void setLetterAttempts(Set<Character> letterAttempts) {
        this.letterAttempts = letterAttempts;
    }

    public Integer getAttemptsLeft() {
        return attemptsLeft;
    }

    public void setAttemptsLeft(Integer attemptsLeft) {
        this.attemptsLeft = attemptsLeft;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }
}