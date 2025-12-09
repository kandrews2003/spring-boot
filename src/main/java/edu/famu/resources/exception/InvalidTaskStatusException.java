package edu.famu.resources.exception;

public class InvalidTaskStatusException extends RuntimeException {
    public InvalidTaskStatusException(String status) {
        super("Invalid status: " + status + ". Must be PENDING, IN_PROGRESS, or COMPLETED");
    }
}
