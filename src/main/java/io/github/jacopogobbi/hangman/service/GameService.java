package io.github.jacopogobbi.hangman.service;

import com.google.common.collect.Lists;
import io.github.jacopogobbi.hangman.domain.Game;
import io.github.jacopogobbi.hangman.dto.GameDTO;
import io.github.jacopogobbi.hangman.dto.PlayersDTO;
import io.github.jacopogobbi.hangman.dto.SuccessDTO;
import io.github.jacopogobbi.hangman.repository.GameRepository;
import io.github.jacopogobbi.hangman.repository.PlayerRepository;
import io.github.jacopogobbi.hangman.domain.GameStatus;
import io.github.jacopogobbi.hangman.domain.Player;
import io.github.jacopogobbi.hangman.domain.PlayerSession;
import io.github.jacopogobbi.hangman.dto.RequestDTO;
import io.github.jacopogobbi.hangman.dto.ResponseDTO;
import io.github.jacopogobbi.hangman.manager.GameManager;
import io.github.jacopogobbi.hangman.repository.PlayerSessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The Game service.
 */
@Service
public class GameService {

    private final PlayerRepository playerRepository;
    private final PlayerSessionRepository playerSessionRepository;
    private final GameRepository gameRepository;
    private final GameManager gameManager;
    private ResponseDTO responseDTO;
    private SuccessDTO successDTO;

    /**
     * Instantiates a new Game service.
     *
     * @param playerRepository        the player repository
     * @param playerSessionRepository the player session repository
     * @param gameRepository          the game repository
     * @param gameManager             the game manager
     */
    @Autowired
    public GameService(PlayerRepository playerRepository,
                       PlayerSessionRepository playerSessionRepository,
                       GameRepository gameRepository,
                       GameManager gameManager) {
        this.playerRepository = playerRepository;
        this.playerSessionRepository = playerSessionRepository;
        this.gameRepository = gameRepository;
        this.gameManager = gameManager;
    }

    /**
     * Initialise the object.
     */
    @PostConstruct
    public void init() {
        responseDTO = new ResponseDTO();
        successDTO = new SuccessDTO();
    }


    /**
     * Starts a new game for the current player.
     *
     * @param requestDTO the request DTO
     * @return the response DTO
     */
    public ResponseDTO gameInit(RequestDTO requestDTO) {
        PlayerSession playerSession;
        Player player;
        List<Game> gameList;

        // Creates new session if it hasn't found any
        Optional<PlayerSession> activePlayerSession = playerSessionRepository.findByCookie(requestDTO.getSessionCookie());
        playerSession = activePlayerSession.orElseGet(() -> gameManager.createPlayerSession(requestDTO.getSessionCookie()));

        // Creates new player if it hasn't found any
        Optional<Player> existingPlayer = playerSession.getPlayer();
        player = existingPlayer.orElseGet(() -> gameManager.createPlayer(requestDTO.getPlayerName()));

        // Creates new list of games if it hasn't found any
        Game game = gameManager.createGame();
        gameList = player.getGames().orElseGet(() -> new ArrayList<Game>(){{add(game);}});

        // Bind objects
        player.setGames(gameList);
        player.setPlayerSession(playerSession);
        game.setPlayer(player);

        // Saves the objects in the DB
        playerRepository.save(player);
        gameRepository.save(game);
        playerSessionRepository.save(playerSession);

        // Setup response object
        successDTO = new SuccessDTO();
        successDTO.setCookie(playerSession.getCookie());
        successDTO.setPlayerName(player.getName());
        successDTO.setGamesWon(player.getGamesWon());
        successDTO.setLetterAttempts(game.getLetterAttempts());
        successDTO.setAttemptsLeft(game.getAttemptsLeft());
        successDTO.setUncompletedWord(gameManager.createUncompletedWord(game.getWordToGuess(), game.getLetterAttempts(), '_'));
        successDTO.setGameStatus(game.getGameStatus());

        responseDTO.setSuccessDTO(successDTO);
        return responseDTO;
    }

    /**
     * Returns the status of the game for the current player.
     *
     * @param requestDTO the request DTO
     * @return the response DTO
     */
    public ResponseDTO status(RequestDTO requestDTO) {
        successDTO = new SuccessDTO();

        // Looks for an existing player session
        Optional<PlayerSession> activePlayerSession = playerSessionRepository.findByCookie(requestDTO.getSessionCookie());
        if(activePlayerSession.isPresent()) {
            PlayerSession playerSession = activePlayerSession.get();

            successDTO = new SuccessDTO();
            successDTO.setCookie(playerSession.getCookie());

            // Searches for a player in the current session
            Optional<Player> playerOptional = playerSession.getPlayer();
            if (playerOptional.isPresent()) {
                Player player = playerOptional.get();

                successDTO.setPlayerName(player.getName());
                successDTO.setGamesWon(player.getGamesWon());

                // Searches for active games
                Optional<Game> gameOptional = gameRepository.findByPlayerAndGameStatus(player, GameStatus.PLAYING);
                if (gameOptional.isPresent()) {
                    Game game = gameOptional.get();

                    successDTO.setLetterAttempts(game.getLetterAttempts());
                    successDTO.setAttemptsLeft(game.getAttemptsLeft());
                    successDTO.setUncompletedWord(gameManager.createUncompletedWord(game.getWordToGuess(), game.getLetterAttempts(), '_'));
                    successDTO.setGameStatus(game.getGameStatus());
                }
            }
        }

        responseDTO.setSuccessDTO(successDTO);
        return responseDTO;
    }

    /**
     * Gets information about all games and players.
     *
     * @return the response DTO
     */
    public ResponseDTO getAllPlayersAndGames() {
        List<PlayersDTO> playerDTOs = new ArrayList<>();
        Lists.newArrayList(playerRepository.findAll()).forEach(player -> {
            List<GameDTO> gamesList = new ArrayList<>();
            if (player.getGames().isPresent()) {
                player.getGames().get().forEach(game -> {
                    GameDTO gameDTO = new GameDTO();
                    gameDTO.setId(game.getId());
                    gameDTO.setLetterAttempts(game.getLetterAttempts());
                    gameDTO.setAttemptsLeft(game.getAttemptsLeft());
                    gameDTO.setGameStatus(game.getGameStatus());
                    gamesList.add(gameDTO);
                });
            }
            PlayersDTO playerDTO = new PlayersDTO();
            playerDTO.setId(player.getId());
            playerDTO.setName(player.getName());
            playerDTO.setGamesWon(player.getGamesWon());
            playerDTO.setGames(gamesList);
            playerDTOs.add(playerDTO);
        });

        responseDTO.setPlayersDTOs(playerDTOs);
        return responseDTO;
    }

    /**
     * Calls game manager to make an attempt, saves the game and prepare the response object.
     *
     * @param requestDTO the request DTO
     * @return the response DTO
     */
    public ResponseDTO attempt(RequestDTO requestDTO) {

        // Looks for an existing player session
        Optional<PlayerSession> activePlayerSession = playerSessionRepository.findByCookie(requestDTO.getSessionCookie());
        if (activePlayerSession.isPresent()) {
            PlayerSession playerSession = activePlayerSession.get();

            // Searches for a player in the current session
            Optional<Player> playerOptional = playerSession.getPlayer();
            if (playerOptional.isPresent()) {
                Player player = playerOptional.get();

                // Searches for player games
                Optional<List<Game>> gamesOptional = player.getGames();
                if (gamesOptional.isPresent()) {
                    List<Game> games = gamesOptional.get();

                    // Searches for the active player game
                    Optional<Game> gameOptional = games.stream()
                            .filter(g -> g.getGameStatus().equals(GameStatus.PLAYING))
                            .findFirst();

                    if (gameOptional.isPresent()) {
                        Game game = gameOptional.get();
                        game = gameManager.attempt(requestDTO.getLetterAttempt(), game, player, playerSession);

                        // Saves the object in the DB
                        gameRepository.save(game);

                        // Setup response object
                        successDTO.setCookie(playerSession.getCookie());
                        successDTO.setPlayerName(player.getName());
                        successDTO.setGamesWon(player.getGamesWon());
                        successDTO.setLetterAttempts(game.getLetterAttempts());
                        successDTO.setAttemptsLeft(game.getAttemptsLeft());
                        successDTO.setUncompletedWord(gameManager.createUncompletedWord(game.getWordToGuess(), game.getLetterAttempts(), '_'));
                        successDTO.setGameStatus(game.getGameStatus());
                    }
                }
            }
        }

        responseDTO.setSuccessDTO(successDTO);
        return responseDTO;
    }
}
