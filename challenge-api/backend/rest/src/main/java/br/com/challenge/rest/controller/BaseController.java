package br.com.challenge.rest.controller;

import java.net.URI;

import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.challenge.core.util.I18N;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class BaseController {
    @Autowired
    protected Validator validator;

    @Autowired
    protected I18N i18n;

    @Getter
    private final String currentPath;

    public BaseController(String currentPath) {
        this.currentPath = currentPath;
    }

    protected URI createURI(String id) {
        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(String.format("%s/%s", currentPath, id))
                .build()
                .toUri();
    }
}
