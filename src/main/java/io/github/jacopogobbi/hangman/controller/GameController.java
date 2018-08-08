package io.github.jacopogobbi.hangman.controller;

import io.github.jacopogobbi.hangman.dto.RequestDTO;
import io.github.jacopogobbi.hangman.dto.ResponseDTO;
import io.github.jacopogobbi.hangman.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


/**
 * The type Game controller.
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    private final GameService gameService;

    /**
     * Instantiates a new Game controller.
     *
     * @param gameService the game service
     */
    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * Game initialisation.
     *
     * @param requestDTO the request dto
     * @return the response dto as a wrapper for a successful response
     */
    @RequestMapping(value = "/init", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseDTO gameInit(@RequestBody @Valid RequestDTO requestDTO) {
        return gameService.gameInit(requestDTO);
    }

    /**
     * Returns the status of the game for the current player.
     *
     * @param requestDTO the request dto
     * @return the response dto as a wrapper for a successful response
     */
    @RequestMapping(value = "/status", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseDTO gameStatus(@RequestBody @Valid RequestDTO requestDTO) {
        return gameService.status(requestDTO);
    }

    /**
     * Play response dto.
     *
     * @param requestDTO the request dto
     * @return the response dto as a wrapper for a successful response
     */
    @RequestMapping(value = "/play", method = RequestMethod.POST, produces = "application/json")
    public @ResponseBody ResponseDTO attempt(@RequestBody @Valid RequestDTO requestDTO) {
        return gameService.attempt(requestDTO);
    }


}
