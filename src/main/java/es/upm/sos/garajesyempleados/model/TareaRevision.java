package es.upm.sos.garajesyempleados.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TareaRevision extends RepresentationModel<TareaRevision> {

    private Integer taskId;
    private Integer garajeId;
    private EstadoTarea estado;
    private String mensaje;
    private String resultadoUri;
}