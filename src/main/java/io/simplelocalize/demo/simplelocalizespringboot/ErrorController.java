package io.simplelocalize.demo.simplelocalizespringboot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Locale;

@RestControllerAdvice
public class ErrorController
{
  @Autowired
  private MessageSource messageSource;

  @ExceptionHandler(Exception.class)
  @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
  public ResponseEntity<ApiError> exception()
  {
    String message = getLocalizedMessage("exception.internalServerError");
    HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    return ResponseEntity
            .status(status)
            .body(new ApiError(message, status));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(code = HttpStatus.BAD_REQUEST)
  public ResponseEntity<ApiError> badRequest()
  {
    String message = getLocalizedMessage("exception.badRequest");
    HttpStatus status = HttpStatus.BAD_REQUEST;
    return ResponseEntity
            .status(status)
            .body(new ApiError(message, status));
  }

  private String getLocalizedMessage(String translationKey)
  {
    Locale locale = LocaleContextHolder.getLocale();
    return messageSource.getMessage(translationKey, null, locale);
  }
}
