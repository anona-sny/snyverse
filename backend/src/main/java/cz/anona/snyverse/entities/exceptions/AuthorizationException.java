package cz.anona.snyverse.entities.exceptions;

import cz.anona.snyverse.dtos.ExceptionDTO;
import lombok.Getter;

@Getter
public class AuthorizationException extends Exception implements FrontendException {

    public AuthorizationException(String message) {
        super(message);
    }

    @Override
    public ExceptionDTO getData() {
        return ExceptionDTO.builder().message(this.getMessage()).type("AUTHORIZATION").build();
    }
}
