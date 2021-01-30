package ru.andreev.blog.postmanagment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlers.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CategoryNotFountException.class)
    public ErrorResponse handlerCategoryNotFound(final CategoryNotFountException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(), "Category not found");
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Throwable.class)
    public ErrorResponse handleThrowable(final Throwable ex) {
        logger.error("Unexpected Error", ex);
        return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "An unexpected internal server error occurred");
    }

}
