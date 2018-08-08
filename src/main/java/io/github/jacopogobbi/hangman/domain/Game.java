package io.github.jacopogobbi.hangman.domain;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Optional;
import java.util.Set;

/**
 * Game hibernate domain object.
 */
@Entity(name = "Game")
@Table(schema = "hangman_core", name = "game")
public class Game {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "word_to_guess", nullable = false)
    private String wordToGuess;

    @ElementCollection(targetClass = Character.class)
    @Column(name = "letter_attempts", nullable = false)
    private Set<Character> letterAttempts;

    @Column(name = "attempts_left", nullable = false)
    private Integer attemptsLeft;

    @Column(name = "game_status", nullable = false)
    private GameStatus gameStatus;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Game() {

    }

    public Game(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setWordToGuess(String wordToGuess) {
        this.wordToGuess = wordToGuess;
    }

    public String getWordToGuess() {
        return wordToGuess;
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

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
