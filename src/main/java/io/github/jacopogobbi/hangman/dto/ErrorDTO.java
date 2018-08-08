package io.github.jacopogobbi.hangman.dto;


/**
 * The Error DTO.
 * Maps an error message to use in a response.
 * Not used yet.
 */
public class ErrorDTO {
    private String errorCode;
    private String errorMessage;

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
