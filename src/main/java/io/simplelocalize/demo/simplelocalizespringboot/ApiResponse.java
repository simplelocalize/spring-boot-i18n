package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.http.HttpStatus;

public record ApiResponse(String message, HttpStatus status) { }
