package es.upm.sos.garajesyempleados.exceptions;

public class EmpleadoNotAllowedException extends RuntimeException {
    public EmpleadoNotAllowedException() {
        super("Error la edad del empleado tiene que ser mayor que 16.");
      }
}

