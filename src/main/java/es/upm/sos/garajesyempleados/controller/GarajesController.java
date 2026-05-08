package es.upm.sos.garajesyempleados.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.upm.sos.garajesyempleados.assembler.GarajeModelAssembler;
import es.upm.sos.garajesyempleados.exceptions.*;
import es.upm.sos.garajesyempleados.model.Empleado;
import es.upm.sos.garajesyempleados.model.Garaje;
import es.upm.sos.garajesyempleados.model.GarajeEmpleado;
import es.upm.sos.garajesyempleados.model.GarajeEmpleadoId;
import es.upm.sos.garajesyempleados.model.GarajeEstadisticas;
import es.upm.sos.garajesyempleados.model.TareaRevision;
import es.upm.sos.garajesyempleados.service.EmpleadoService;
import es.upm.sos.garajesyempleados.service.GarajeEmpleadoService;
import es.upm.sos.garajesyempleados.service.GarajeService;
import es.upm.sos.garajesyempleados.service.RevisionAsyncService;
import es.upm.sos.garajesyempleados.service.TareaService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@RestController // Define un controlador REST
@RequestMapping("/garajes") // Todos los endpoints empiezan por empleados
@XmlRootElement // Para indicar que soportamos xml (opcional)
@AllArgsConstructor
public class GarajesController {

        private final GarajeService service;
        private final EmpleadoService empleadoService;
        private final GarajeEmpleadoService garajeEmpleadoService;
        // Necesarios para navegabiliad
        private PagedResourcesAssembler<Garaje> pagedResourcesAssembler;
        private GarajeModelAssembler garajeModelAssembler;

        // Necesarios para tarea asincrona
        private final TareaService tareaService;
        private final RevisionAsyncService revisionAsyncService;
        /*
         * private PagedResourcesAssembler<GarajeEntity> pagedResourcesAssembler;
         * private GarajeModelAssembler garajeModelAssembler;
         */

        @PostMapping()
        public ResponseEntity<Void> nuevoGaraje(@Valid @RequestBody Garaje nuevoGaraje) {
                if (!service.buscarPorNombreyDireccion(nuevoGaraje.getNombre(), nuevoGaraje.getDireccion())) {
                        Garaje garaje = service.crearGaraje(nuevoGaraje);

                        return ResponseEntity.created(linkTo(GarajesController.class).slash(garaje.getId()).toUri())
                                        .build();
                }
                throw new GarajeExistsException(nuevoGaraje.getNombre(),
                                nuevoGaraje.getDireccion());
        }

        @GetMapping(value = "/{id}", produces = { "application/json", "application/xml" })
        public ResponseEntity<Garaje> getGaraje(@PathVariable Integer id) {

                Garaje garaje = service.buscarPorId(id)
                                .orElseThrow(() -> new GarajeNotFoundException(id));
                garaje.add(linkTo(methodOn(GarajesController.class).getGaraje(id)).withSelfRel());
                return ResponseEntity.ok(garaje);
        }

        @GetMapping(value = "/{id}/resumen", produces = { "application/json", "application/xml" })
        public ResponseEntity<Garaje> getGarajeResumen(@PathVariable Integer id) {

                Garaje garaje = service.buscarPorId(id)
                                .orElseThrow(() -> new GarajeNotFoundException(id));
                Set<EntityModel<Empleado>> lista_empleados = new HashSet<>();
                for (GarajeEmpleado garajeEmpleadoEntity : garajeEmpleadoService.buscarPorGarajeId(id)) {
                        lista_empleados.add(EntityModel.of(garajeEmpleadoEntity.getEmpleado(),
                                        linkTo(methodOn(EmpleadosController.class)
                                                        .getEmpleado(garajeEmpleadoEntity.getEmpleado().getId()))
                                                        .withSelfRel()));
                }
                garaje.setLista_empleados(lista_empleados);
                garaje.add(linkTo(methodOn(GarajesController.class).getGaraje(id)).withSelfRel());
                return ResponseEntity.ok(garaje);
        }

        @GetMapping(value = "", produces = { "application/json", "application/xml" })
        public ResponseEntity<PagedModel<Garaje>> filtered(
                        @RequestParam(defaultValue = "", required = false) String startsWith,
                        @RequestParam(defaultValue = "0", required = false) int page,
                        @RequestParam(defaultValue = "2", required = false) int size) {

                Page<Garaje> garajes = service.buscarGarajes(startsWith, page, size);

                // fetch the page object by additionally passing paginable with the filters
                return ResponseEntity.ok(pagedResourcesAssembler.toModel(garajes, garajeModelAssembler));
        }

        @PutMapping("/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> replaceGaraje(@RequestBody Garaje newGaraje, @PathVariable Integer id) {
                service.buscarPorId(id)
                                .map(Garaje -> {
                                        Garaje.setNombre(newGaraje.getNombre());
                                        Garaje.setDireccion(newGaraje.getDireccion());
                                        Garaje.setTelefono(newGaraje.getTelefono());
                                        return service.crearGaraje(Garaje);
                                })
                                .orElseThrow(() -> new GarajeNotFoundException(id));
                return ResponseEntity.noContent().build();
        }

        @DeleteMapping(value = "/{id}")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> deleteGaraje(@PathVariable Integer id) {
                if (service.existeGarajePorId(id)) {
                        service.eliminarGaraje(id);
                } else {
                        throw new GarajeNotFoundException(id);
                }
                return ResponseEntity.noContent().build();
        }

        @PostMapping("/{id}/empleados")
        @ResponseStatus(HttpStatus.NO_CONTENT)
        public ResponseEntity<Void> addEmpleadoToGaraje(@PathVariable Integer id,
                        @RequestBody GarajeEmpleadoId nuevoEmpleadoEnGaraje) {
                // Buscar garaje y empleado en la base de datos
                Garaje garaje = service.buscarPorId(id)
                                .orElseThrow(() -> new GarajeNotFoundException(id));

                Empleado empleado = empleadoService.buscarPorId(nuevoEmpleadoEnGaraje.getEmpleadoId())
                                .orElseThrow(() -> new EmpleadoNotFoundException(
                                                nuevoEmpleadoEnGaraje.getEmpleadoId()));

                garajeEmpleadoService.contratarEmpleadoEnGaraje(nuevoEmpleadoEnGaraje, garaje, empleado);
                return ResponseEntity.noContent().build();
        }

        @GetMapping("/{id}/estadisticas")
        public ResponseEntity<GarajeEstadisticas> getEstadisticasGaraje(@PathVariable int id) {

                Garaje garaje = service.buscarPorId(id)
                                .orElseThrow(() -> new GarajeNotFoundException(id));

                int numEmpleados = garajeEmpleadoService.contarEmpleadosPorGarajeId(id);

                GarajeEstadisticas estadisticas = new GarajeEstadisticas(
                                garaje.getId(),
                                garaje.getNombre(),
                                numEmpleados);

                estadisticas.add(
                                linkTo(methodOn(GarajesController.class).getEstadisticasGaraje(id)).withSelfRel());

                return ResponseEntity.ok(estadisticas);
        }

        @PostMapping("/{id}/revision")
        public ResponseEntity<TareaRevision> iniciarRevision(@PathVariable int id) {
                Garaje garaje = service.buscarPorId(id)
                                .orElseThrow(() -> new GarajeNotFoundException(id));
                TareaRevision tarea = tareaService.crearTareaRevision(garaje.getId());
                URI location = linkTo(methodOn(TareasController.class).getTarea(tarea.getTaskId())).toUri();
                tarea.add(
                                linkTo(methodOn(TareasController.class).getTarea(tarea.getTaskId())).withSelfRel());
                revisionAsyncService.ejecutarRevision(tarea.getTaskId(), garaje.getId());

                return ResponseEntity.accepted()
                                .location(location)
                                .body(tarea);
        }
}
