package es.upm.sos.garajesyempleados.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;

import org.springframework.hateoas.RepresentationModel;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "empleados") // Necesario para indicar el nombre de la tabla en la base de datos
@Data // Indica a Lombok crear los métodosgetter, setter, equals(), hashCode(), y
// toString()
@NoArgsConstructor // Crea un constructor vacío
@AllArgsConstructor // Crea un constructor con todos los campos
@JsonIgnoreProperties(ignoreUnknown = true) // Permite validar el json recibido de tal manera que
											// tenga los mismo campos que aparecen en el recurso
public class Empleado extends RepresentationModel<Empleado> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // valor generado por la base de datos
	@Schema(description = "Id del empleado", required = false, example = "1")
	private int id;
	@Schema(description = "Nombre del empleado", required = true, example = "Marcos")
	@NotNull(message = "El nombre es obligatorio y no puede ser null")
	private String nombre;

}
