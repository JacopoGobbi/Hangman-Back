package io.github.jacopogobbi.hangman.repository;

import io.github.jacopogobbi.hangman.domain.Player;
import org.springframework.data.repository.CrudRepository;

/**
 * Player Repository interface.
 * Simplifies data access thanks to Spring Data.
 */
public interface PlayerRepository extends CrudRepository<Player, Integer> {

}
