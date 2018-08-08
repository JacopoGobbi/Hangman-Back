package io.github.jacopogobbi.hangman.dto;

import java.util.List;

/**
 * Players DTO.
 * Used to map the response containing the list of players
 * that played the game.
 */
public class PlayersDTO {
    private Integer id;
    private String name;
    private int gamesWon;
    private List<GameDTO> games;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(int gamesWon) {
        this.gamesWon = gamesWon;
    }

    public List<GameDTO> getGames() {
        return games;
    }

    public void setGames(List<GameDTO> games) {
        this.games = games;
    }
}
