package io.github.jacopogobbi.hangman.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Optional;

/**
 * Player Session hibernate POJO.
 */
@Entity(name = "PlayerSession")
@Table(schema = "hangman_core", name = "player_session")
public class PlayerSession {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "cookie", nullable = false)
    private String cookie;

    @OneToOne(mappedBy = "playerSession")
    private Player player;

    public PlayerSession() {

    }

    public PlayerSession(String cookie) {
        this.cookie = cookie;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getCookie() {
        return cookie;
    }

    public Optional<Player> getPlayer() {
        return Optional.ofNullable(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
