package com.sublinks.sublinksapi.api.lemmy.v3.errorHandler;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.util.Collections;
import java.util.List;

@Getter
public class ApiError {
    private final HttpStatusCode status;
    private final String message;
    private final List<String> errors;

    public ApiError(final HttpStatusCode status, final String message, final List<String> errors) {

        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

    public ApiError(final HttpStatusCode status, final String message, final String error) {

        super();
        this.status = status;
        this.message = message;
        errors = Collections.singletonList(error);
    }
}
