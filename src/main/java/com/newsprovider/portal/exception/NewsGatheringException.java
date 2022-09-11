package com.newsprovider.portal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class NewsGatheringException extends RuntimeException {
    public NewsGatheringException() {
        super("There was an unexpected problem while gathering news from the news provider");
    }
}
