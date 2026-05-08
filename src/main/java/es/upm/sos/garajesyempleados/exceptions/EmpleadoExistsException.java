package es.upm.sos.garajesyempleados.exceptions;

public class EmpleadoExistsException extends RuntimeException {
  public EmpleadoExistsException(String nombre) {
    super("Empleado con nombre " + nombre + " ya existe.");
  }
}
