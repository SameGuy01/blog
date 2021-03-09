package ru.asteac.blog.postmanagment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User is not found")
public class UserNotFoundException extends RuntimeException {

}
