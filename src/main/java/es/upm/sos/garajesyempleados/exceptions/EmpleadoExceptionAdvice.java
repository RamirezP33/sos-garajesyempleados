package es.upm.sos.garajesyempleados.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmpleadoExceptionAdvice {
  @ExceptionHandler(EmpleadoNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  ErrorMessage userNotFoundHandler(EmpleadoNotFoundException ex) {
    return new ErrorMessage(ex.getMessage());
  }

  @ExceptionHandler(EmpleadoNotAllowedException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ErrorMessage userNotAllowedHandler(EmpleadoNotAllowedException ex) {
    return new ErrorMessage(ex.getMessage());
  }

  @ExceptionHandler(EmpleadoExistsException.class)
  @ResponseStatus(HttpStatus.CONFLICT)
  ErrorMessage userExistsHandler(EmpleadoExistsException ex) {
    return new ErrorMessage(ex.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorMessage handleValidationExceptions1(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    ex.getBindingResult().getAllErrors().forEach((error) -> {
      String fieldName = ((FieldError) error).getField();
      String errorMessage = error.getDefaultMessage();
      errors.put(fieldName, errorMessage);
    });
    return new ErrorMessage(errors.toString());
  }

}