package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.http.HttpStatus;

public record ApiError(String message, HttpStatus status) { }
