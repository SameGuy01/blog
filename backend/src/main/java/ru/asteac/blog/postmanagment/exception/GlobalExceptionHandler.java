package ru.asteac.blog.postmanagment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final static String ROLE_NOT_FOUND = "Role is not found";
    private final static String USER_NOT_FOUND = "User is not found";
    private final static String POST_NOT_FOUND = "Post is not found";

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CommentNotFoundException.class)
    public ResponseEntity<?> handlerCommentNotFount(){
        ErrorResponse errorResponse = new ErrorResponse(ROLE_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handlerUserNotFound(){
        ErrorResponse errorResponse = new ErrorResponse(USER_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PostNotFoundException.class)
    public ResponseEntity<?> handlerPostNotFound(){
        ErrorResponse errorResponse = new ErrorResponse(POST_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<?> handlerRoleNotFound(){
        ErrorResponse errorResponse = new ErrorResponse(ROLE_NOT_FOUND);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = ex.getBindingResult().getAllErrors()
                .stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.toList());
        ErrorResponse errorResponse = new ErrorResponse("Validation failed", details);
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }
}
