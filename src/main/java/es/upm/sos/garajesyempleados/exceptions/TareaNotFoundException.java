package es.upm.sos.garajesyempleados.exceptions;

public class TareaNotFoundException extends RuntimeException {

    public TareaNotFoundException(Integer id) {
        super("No se ha encontrado la tarea con id " + id);
    }
}
