package es.upm.sos.garajesyempleados.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GarajeNotFoundAdvice {
  @ExceptionHandler(GarajeNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMessage userNotFoundHandler(GarajeNotFoundException ex) {
    return new ErrorMessage(ex.getMessage());
  }

  @ExceptionHandler(GarajeNotAllowedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ErrorMessage userNotAllowedHandler(GarajeNotAllowedException ex) {
    return new ErrorMessage(ex.getMessage());
  }

  @ExceptionHandler(GarajeExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ErrorMessage userExistsHandler(GarajeExistsException ex) {
    return new ErrorMessage(ex.getMessage());
  }
}
