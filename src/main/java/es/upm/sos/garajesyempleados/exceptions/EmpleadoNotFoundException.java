package es.upm.sos.garajesyempleados.exceptions;

public class EmpleadoNotFoundException extends RuntimeException {
    public EmpleadoNotFoundException(Integer id) {
        super("Empleado con id "+id+" no encontrado.");
      }
}

