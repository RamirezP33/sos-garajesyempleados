package es.upm.sos.garajesyempleados.exceptions;

public class GarajeNotFoundException extends RuntimeException {
  public GarajeNotFoundException(Integer id) {
    super("Garaje con id " + id + " no encontrado.");
  }
}
