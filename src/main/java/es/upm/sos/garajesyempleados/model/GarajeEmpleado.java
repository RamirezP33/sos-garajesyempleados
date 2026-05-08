package es.upm.sos.garajesyempleados.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Table(name = "garaje_empleado")
@NoArgsConstructor
@AllArgsConstructor
public class GarajeEmpleado {

    @EmbeddedId // Indica GarajeEmpleadoId es la clave primaria de esta entidad
    private GarajeEmpleadoId id;

    @ManyToOne // Cada instancia GarajeEmpleado va a tener un empleado
    @MapsId("empleadoId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "empleado_id")
    private Empleado empleado;

    @ManyToOne // Cada instancia GarajeEmpleado va a tener un garaje
    @MapsId("garajeId") // Asocia la clave primaria al campo correspondiente
    @JoinColumn(name = "garaje_id")
    private Garaje garaje;
}
