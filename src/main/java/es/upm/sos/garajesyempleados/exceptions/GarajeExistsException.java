package es.upm.sos.garajesyempleados.exceptions;

public class GarajeExistsException extends RuntimeException {
  public GarajeExistsException(String nombre, String direccion) {
    super("Garaje con nombre " + nombre + "y dirección " + direccion + " ya existe.");
  }
}
