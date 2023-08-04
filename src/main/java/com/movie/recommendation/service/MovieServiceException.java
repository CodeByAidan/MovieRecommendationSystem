package com.movie.recommendation.service;

class MovieServiceException extends RuntimeException {
    public MovieServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
