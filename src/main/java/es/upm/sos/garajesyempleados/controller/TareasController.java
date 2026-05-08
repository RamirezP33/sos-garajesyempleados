package es.upm.sos.garajesyempleados.controller;

import es.upm.sos.garajesyempleados.exceptions.TareaNotFoundException;
import es.upm.sos.garajesyempleados.model.EstadoTarea;
import es.upm.sos.garajesyempleados.model.TareaRevision;
import es.upm.sos.garajesyempleados.service.TareaService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;

@RestController
@RequestMapping("/tareas")
@AllArgsConstructor
public class TareasController {
    private final TareaService tareaService;

    @GetMapping("/{taskId}")
    public ResponseEntity<?> getTarea(@PathVariable Integer taskId) {

        TareaRevision tarea = tareaService.buscarPorId(taskId)
                .orElseThrow(() -> new TareaNotFoundException(taskId));

        if (tarea.getEstado() == EstadoTarea.COMPLETADA) {
            return ResponseEntity.status(303)
                    .location(URI.create(tarea.getResultadoUri()))
                    .build();
        }

        tarea.add(
                linkTo(methodOn(TareasController.class).getTarea(taskId)).withSelfRel());

        return ResponseEntity.ok(tarea);
    }
}