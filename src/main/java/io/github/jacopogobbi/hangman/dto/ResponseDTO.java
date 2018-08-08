package io.github.jacopogobbi.hangman.dto;

import java.util.List;

/**
 * Response DTO.
 * Used to wrap other DTOs, this allows for error handling support.
 */
public class ResponseDTO {
    private SuccessDTO successDTO;
    private List<PlayersDTO> playersDTOs;
    private ErrorDTO errorDTO;

    public SuccessDTO getSuccessDTO() {
        return successDTO;
    }

    public void setSuccessDTO(SuccessDTO successDTO) {
        this.successDTO = successDTO;
    }

    public List<PlayersDTO> getPlayersDTOs() {
        return playersDTOs;
    }

    public void setPlayersDTOs(List<PlayersDTO> playersDTOs) {
        this.playersDTOs = playersDTOs;
    }

    public ErrorDTO getErrorDTO() {
        return errorDTO;
    }

    public void setErrorDTO(ErrorDTO errorDTO) {
        this.errorDTO = errorDTO;
    }
}
