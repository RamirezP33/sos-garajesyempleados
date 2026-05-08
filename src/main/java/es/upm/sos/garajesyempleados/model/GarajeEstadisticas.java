package es.upm.sos.garajesyempleados.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarajeEstadisticas extends RepresentationModel<GarajeEstadisticas> {

    private Integer garajeId;
    private String nombreGaraje;
    private int numEmpleados;
}
