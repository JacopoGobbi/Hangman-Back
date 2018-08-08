package io.github.jacopogobbi.hangman.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.List;
import java.util.Optional;

/**
 * Player hibernate POJO.
 */
@Entity(name = "Player")
@Table(schema = "hangman_core", name = "player")
public class Player {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "games_won", nullable = false)
    private int gamesWon;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "playerSession_id")
    private PlayerSession playerSession;

    @OneToMany(mappedBy = "player")
    private List<Game> games;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
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

    public Optional<PlayerSession> getPlayerSession() {
        return Optional.ofNullable(playerSession);
    }

    public void setPlayerSession(PlayerSession playerSession) {
        this.playerSession = playerSession;
    }

    public Optional<List<Game>> getGames() {
        return Optional.ofNullable(games);
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }
}
