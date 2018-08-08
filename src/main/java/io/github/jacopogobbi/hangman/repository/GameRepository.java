package io.github.jacopogobbi.hangman.repository;

import io.github.jacopogobbi.hangman.domain.Game;
import io.github.jacopogobbi.hangman.domain.GameStatus;
import io.github.jacopogobbi.hangman.domain.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Game Repository interface.
 * Simplifies data access thanks to Spring Data.
 */
public interface GameRepository extends CrudRepository<Game, Integer> {
    Optional<Game> findByPlayerAndGameStatus(Player player, GameStatus gameStatus);
}
