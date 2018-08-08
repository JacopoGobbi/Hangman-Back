package io.github.jacopogobbi.hangman.repository;

import io.github.jacopogobbi.hangman.domain.PlayerSession;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Player Session interface.
 * Simplifies data access thanks to Spring Data.
 */
public interface PlayerSessionRepository extends CrudRepository<PlayerSession, Integer> {
    Optional<PlayerSession> findByCookie(String cookie);
}
