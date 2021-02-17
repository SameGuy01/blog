package ru.andreev.blog.postmanagment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Post is not found")
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(String detail) {
        super("Post in not found" + detail);
    }
}
