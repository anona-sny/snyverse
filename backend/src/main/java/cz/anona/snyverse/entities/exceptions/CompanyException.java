package cz.anona.snyverse.entities.exceptions;

import cz.anona.snyverse.dtos.ExceptionDTO;
import cz.anona.snyverse.entities.enums.CompanyExceptionType;
import lombok.Getter;

@Getter
public class CompanyException extends Exception implements FrontendException {

    private CompanyExceptionType type;

    public CompanyException(CompanyExceptionType type, String message) {
        super(message);
        this.type = type;
    }


    @Override
    public ExceptionDTO getData() {
        return ExceptionDTO.builder().message(this.getMessage()).type(this.getType().name()).build();
    }
}
