package io.github.jacopogobbi.hangman.dto;

/**
 * The Version DTO.
 * For API health check
 */
public class VersionDTO {
    private final String version;

    public VersionDTO(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }
}
