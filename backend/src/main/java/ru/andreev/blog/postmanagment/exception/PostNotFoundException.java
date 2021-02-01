package ru.andreev.blog.postmanagment.exception;

public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(Long id) {
        super("Could not find post id: " + id);
    }
}
