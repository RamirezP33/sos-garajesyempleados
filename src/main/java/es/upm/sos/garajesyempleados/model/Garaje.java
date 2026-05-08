package es.upm.sos.garajesyempleados.model;

import java.util.Set;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "garajes")
@Data // Indica a Lombok crear los métodosgetter, setter, equals(), hashCode(), y
// toString()
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor // Crea un constructor con todos los campos
@EqualsAndHashCode(callSuper = false)
public class Garaje extends RepresentationModel<Garaje> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "El nombre es obligatorio y no puede ser null")
    private String nombre;
    private String direccion;
    @NotNull(message = "El teléfono es obligatorio y no puede ser null")
    private int telefono;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL) // Solo mostrar si no es null
    private Set<EntityModel<Empleado>> lista_empleados;
}
