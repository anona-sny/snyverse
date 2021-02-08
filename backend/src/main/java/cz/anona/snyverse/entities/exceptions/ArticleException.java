package cz.anona.snyverse.entities.exceptions;

import cz.anona.snyverse.dtos.ExceptionDTO;
import cz.anona.snyverse.entities.enums.ArticleExceptionType;

public class ArticleException extends Exception implements FrontendException{

    private ArticleExceptionType type;

    public ArticleException(ArticleExceptionType type, String message) {
        super(message);
        this.type = type;
    }

    @Override
    public ExceptionDTO getData() {
        return ExceptionDTO.builder().message(this.getMessage()).type(this.type.name()).build();
    }


}


