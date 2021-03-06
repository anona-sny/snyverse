package cz.anona.snyverse.entities.exceptions;

import cz.anona.snyverse.dtos.ExceptionDTO;
import cz.anona.snyverse.entities.enums.UserExceptionType;
import lombok.Getter;

@Getter
public class UserException extends Exception implements FrontendException {

    private final UserExceptionType type;

    public UserException(UserExceptionType type, String description) {
        super(description);
        this.type = type;
    }

    @Override
    public ExceptionDTO getData() {
        return ExceptionDTO.builder().message(this.getMessage()).type(this.getType().name()).build();
    }

}
