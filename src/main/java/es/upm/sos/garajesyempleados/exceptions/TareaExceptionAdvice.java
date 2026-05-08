package es.upm.sos.garajesyempleados.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class TareaExceptionAdvice {

    @ResponseBody
    @ExceptionHandler(TareaNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorMessage tareaNotFoundHandler(TareaNotFoundException ex) {
        return new ErrorMessage(ex.getMessage());
    }
}
