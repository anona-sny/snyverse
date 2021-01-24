package cz.anona.snyverse.entities.exceptions;

import cz.anona.snyverse.entities.enums.UserExceptionType;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class UserException extends Exception implements FrontendException {

    private final UserExceptionType type;

    public UserException(UserExceptionType type, String description) {
        super(description);
        this.type = type;
    }

    @Override
    public FrontendExceptionObject getData() {
        return FrontendExceptionObject.builder().message(this.getMessage()).type(this.getType().name()).build();
    }

}
