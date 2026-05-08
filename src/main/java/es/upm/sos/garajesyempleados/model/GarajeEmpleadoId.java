package es.upm.sos.garajesyempleados.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable // Indica que esta clase no es una entidad, sino que se incrusta en otra entidad
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarajeEmpleadoId {
    @NotNull(message = "El empleadoId es obligatorio y no puede ser null")
    private int empleadoId;
    private int garajeId;
}
