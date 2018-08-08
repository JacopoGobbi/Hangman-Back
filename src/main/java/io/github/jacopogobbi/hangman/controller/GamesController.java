package io.github.jacopogobbi.hangman.controller;

import io.github.jacopogobbi.hangman.dto.ResponseDTO;
import io.github.jacopogobbi.hangman.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The Games controller.
 */
@RestController
@RequestMapping("/api/games")
public class GamesController {

    private final GameService gameService;

    /**
     * Instantiates a new Games controller.
     *
     * @param gameService the game service
     */
    @Autowired
    public GamesController(GameService gameService) {
        this.gameService = gameService;
    }


    /**
     * Gets all players and related games.
     *
     * @return the all games
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    ResponseDTO getAllPlayersAndGames() {
        return gameService.getAllPlayersAndGames();
    }
}
