package io.github.jacopogobbi.hangman.manager;

import io.github.jacopogobbi.hangman.domain.Game;
import io.github.jacopogobbi.hangman.domain.GameStatus;
import io.github.jacopogobbi.hangman.domain.Player;
import io.github.jacopogobbi.hangman.domain.PlayerSession;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameManagerTest {

    private static final String COOKIE = "biscuit";
    private static final String PLAYER_NAME = "Giovani";
    private static final String WORD_TO_GUESS = "hiredtoday";
    private static final char LETTER_ATTEMPT = 'd';
    private static final char LETTER_NOT_PRESENT_ATTEMPT = 'z';
    private static final String RESULTING_WORD = "____d__d__";
    private static final Integer NUMBER_OF_ATTEMPTS = 9;

    private Game game;
    private PlayerSession playerSession;
    private Player player;
    private GameManager underTest;

    @Before
    public void setUp() throws Exception {
        underTest = new GameManager();
    }

    @Test
    public void createGameTest() throws Exception {
        ReflectionTestUtils.setField(underTest,
                "wordList",
                new ArrayList<String>(){{add(WORD_TO_GUESS);}});

        // For this test case avoiding to load Spring context help the test to run a lot faster
        ReflectionTestUtils.setField(underTest,
                "totalAttempts",
                9);

        Game actual = underTest.createGame();

        assertEquals( GameStatus.PLAYING, actual.getGameStatus());
        assertTrue("Letter attempts should be empty", actual.getLetterAttempts().isEmpty());
        assertEquals(WORD_TO_GUESS, actual.getWordToGuess());
        assertEquals(NUMBER_OF_ATTEMPTS, actual.getAttemptsLeft());
        assertFalse("Game shouldn't have any associated player", actual.getPlayer().isPresent());

    }

    @Test
    public void createPlayerTest() throws Exception {
        Player actual = underTest.createPlayer(PLAYER_NAME);

        assertEquals(PLAYER_NAME, actual.getName());
        assertEquals(0, actual.getGamesWon());
        assertFalse("Player shouldn't have any associated game", actual.getGames().isPresent());
        assertFalse("Player shouldn't have any associated session", actual.getPlayerSession().isPresent());

    }

    @Test
    public void createPlayerSessionTest() throws Exception {
        PlayerSession actual = underTest.createPlayerSession(COOKIE);

        assertEquals( COOKIE, actual.getCookie());
        assertFalse("Player session shouldn't have any associated player", actual.getPlayer().isPresent());

    }

    @Test
    public void createUncompleteWordTest() throws Exception {
        String actual = underTest.createUncompletedWord(WORD_TO_GUESS,
                new HashSet<Character>(){{add(LETTER_ATTEMPT);}},
                '_');
        assertEquals(RESULTING_WORD, actual);
    }

    @Test
    public void attemptsWinTest() throws Exception {
        setupForAttempts();
        Set<Character> letterAttempts = new HashSet<>();
        "hiretoay".chars().mapToObj(i -> (char)i).forEach(letterAttempts::add);
        game.setLetterAttempts(letterAttempts);
        game.setAttemptsLeft(9);

        player.setGames(new ArrayList<Game>(){{add(game);}});
        player.setPlayerSession(playerSession);
        game.setPlayer(player);

        Game actual = underTest.attempt(LETTER_ATTEMPT, game, player, playerSession);

        assertEquals(GameStatus.WON, actual.getGameStatus());
        assertEquals(Integer.valueOf(9), actual.getAttemptsLeft());
        assertEquals(WORD_TO_GUESS, game.getWordToGuess());
        assertEquals(Integer.valueOf(1), game.getId());
        assertTrue("Game doesn't have a related player", game.getPlayer().isPresent());
        assertEquals(player, game.getPlayer().get());
    }

    @Test
    public void attemptsLoseTest() throws Exception {
        setupForAttempts();
        game.setLetterAttempts(new HashSet<>());
        game.setAttemptsLeft(1);

        player.setGames(new ArrayList<Game>(){{add(game);}});
        player.setPlayerSession(playerSession);
        game.setPlayer(player);

        Game actual = underTest.attempt(LETTER_NOT_PRESENT_ATTEMPT, game, player, playerSession);

        assertEquals(GameStatus.LOST, actual.getGameStatus());
        assertEquals(Integer.valueOf(0), actual.getAttemptsLeft());
        assertEquals(WORD_TO_GUESS, game.getWordToGuess());
        assertEquals(Integer.valueOf(1), game.getId());
        assertTrue("Game doesn't have a related player", game.getPlayer().isPresent());
        assertEquals(player, game.getPlayer().get());

    }

    @Test
    public void attemptsStillInPlayTest() throws Exception {
        setupForAttempts();
        game.setLetterAttempts(new HashSet<>());
        game.setAttemptsLeft(9);

        player.setGames(new ArrayList<Game>(){{add(game);}});
        player.setPlayerSession(playerSession);
        game.setPlayer(player);

        Game actual = underTest.attempt(LETTER_ATTEMPT, game, player, playerSession);

        assertEquals(GameStatus.PLAYING, actual.getGameStatus());
        assertEquals(Integer.valueOf(9), actual.getAttemptsLeft());
        assertEquals(WORD_TO_GUESS, game.getWordToGuess());
        assertEquals(Integer.valueOf(1), game.getId());
        assertTrue("Game doesn't have a related player", game.getPlayer().isPresent());
        assertEquals(player, game.getPlayer().get());

    }

    // Before attempts
    private void setupForAttempts() {
        game = new Game(WORD_TO_GUESS);
        game.setGameStatus(GameStatus.PLAYING);
        game.setId(1);

        player = new Player();
        player.setId(2);
        player.setName(PLAYER_NAME);
        player.setGamesWon(1);

        playerSession = new PlayerSession();
        playerSession.setId(3);
    }
}