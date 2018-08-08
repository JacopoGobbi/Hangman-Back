package io.github.jacopogobbi.hangman.controller;

import io.github.jacopogobbi.hangman.dto.VersionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Version controller.
 */
@RestController
public class VersionController {

    @Value("${application.version}")
    private String version;

    /**
     * Version version.
     *
     * @return the version
     */
    @RequestMapping(value = "/api/version", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody
    VersionDTO version() {
        return new VersionDTO(version);
    }
}
