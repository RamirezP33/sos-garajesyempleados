package es.upm.sos.garajesyempleados.exceptions;

public class GarajeNotAllowedException extends RuntimeException {
  public GarajeNotAllowedException() {
    super("Error la edad del empleado tiene que ser mayor que 16.");
  }
}
