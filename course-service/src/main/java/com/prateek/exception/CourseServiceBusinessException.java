package com.prateek.exception;

public class CourseServiceBusinessException extends RuntimeException {
    public CourseServiceBusinessException(String message) {
        super(message);
    }
}
