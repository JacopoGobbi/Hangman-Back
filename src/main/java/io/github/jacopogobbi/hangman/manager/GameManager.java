package io.github.jacopogobbi.hangman.manager;

import io.github.jacopogobbi.hangman.domain.Game;
import io.github.jacopogobbi.hangman.domain.GameStatus;
import io.github.jacopogobbi.hangman.domain.Player;
import io.github.jacopogobbi.hangman.domain.PlayerSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

/**
 * The type Game manager.
 */
@Component
public class GameManager {
    private List<String> wordList;

    @Value("${application.game.totalAttempts}")
    private Integer totalAttempts;

    @Value("classpath:/words/dictionary.txt")
    private Resource dictionary;

    /**
     * Instantiates a new Game manager.
     */
    public GameManager () {

    }

    /**
     * Initialise the object.
     */
    @PostConstruct
    public void init() {
        wordList = new ArrayList<>();
        if(dictionary != null && dictionary.exists() && dictionary.isReadable()){
            try (Stream<String> stream = Files.lines(Paths.get(dictionary.getURI()))) {
                stream.forEach(s -> wordList.add(s));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Creates a game.
     *
     * @return the game created
     */
    public Game createGame() {
        String randomWord = getRandomWord();
        Game game = new Game(randomWord);
        game.setLetterAttempts(new HashSet<Character>(){});
        game.setGameStatus(GameStatus.PLAYING);
        game.setAttemptsLeft(totalAttempts);
        return game;
    }

    /**
     * Creates a player.
     *
     * @param name the player name
     * @return the player created
     */
    public Player createPlayer(String name) {
        Player player = new Player();
        player.setName(name);
        return player;
    }

    /**
     * Create player session player session.
     *
     * @param sessionCookie the session cookie
     * @return the player session created
     */
    public PlayerSession createPlayerSession(String sessionCookie) {
        return new PlayerSession(sessionCookie);
    }

    /**
     * Create uncompleted word string.
     *
     * @param wordToGuess    the word to guess
     * @param letterAttempts the letter attempts
     * @param filler         the filler
     * @return the un/completed word
     */
    public String createUncompletedWord(String wordToGuess, Set<Character> letterAttempts, char filler) {
        StringBuilder sb = new StringBuilder();
        wordToGuess
                .chars()
                .mapToObj(i -> (char) i)
                .forEach(c -> sb.append(letterAttempts.contains(c) ? c : filler));
        return sb.toString();
    }

    private String getRandomWord() {
        return wordList.get(new Random().nextInt(wordList.size()));
    }

    /**
     * Attempts to see if a letter is present in the word to guess.
     *
     * @param letter        the letter to attempt
     * @param game          the current game
     * @param player        the current player
     * @param playerSession the current player session
     * @return the updated game
     */
    public Game attempt(Character letter, Game game, Player player, PlayerSession playerSession) {
        Set<Character> letterAttempts = game.getLetterAttempts();

        // Reduces attempts number if the letter is not present in the word to guess
        // or if it has already been choosed
        if (game.getWordToGuess().indexOf(letter) < 0 || letterAttempts.contains(letter)) {
            game.setAttemptsLeft(game.getAttemptsLeft() - 1);
        }

        // Adds the letter to the set
        letterAttempts.add(letter);

        // Updates the game object
        game.setLetterAttempts(letterAttempts);

        String uncompletedWord = createUncompletedWord(game.getWordToGuess(), game.getLetterAttempts(), '_');

        // Updates the game status
        game.setGameStatus(uncompletedWord.equals(game.getWordToGuess()) ? GameStatus.WON :
                game.getAttemptsLeft() > 0 ? GameStatus.PLAYING : GameStatus.LOST);

        // Updates the counter in case the player won
        if (game.getGameStatus() == GameStatus.WON) {
            player.setGamesWon(player.getGamesWon() + 1);
        }

        return game;
    }
}
