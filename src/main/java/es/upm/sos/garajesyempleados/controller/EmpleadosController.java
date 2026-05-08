package es.upm.sos.garajesyempleados.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import es.upm.sos.garajesyempleados.assembler.EmpleadoModelAssembler;
import es.upm.sos.garajesyempleados.exceptions.EmpleadoExistsException;
import es.upm.sos.garajesyempleados.exceptions.EmpleadoNotFoundException;
import es.upm.sos.garajesyempleados.model.Empleado;
import es.upm.sos.garajesyempleados.service.EmpleadoService;
import jakarta.validation.Valid;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;

@RestController // Define un controlador REST
@RequestMapping("/empleados") // Todos los endpoints empiezan por empleados
@XmlRootElement // Para indicar que soportamos xml (opcional)
@AllArgsConstructor // Genera un constructor con todos los argumentos
public class EmpleadosController {

    // Acceso al servicio empleado para acceder a la lógica de negocio
    private final EmpleadoService service;
    // Necesarios para navegabiliad
    private PagedResourcesAssembler<Empleado> pagedResourcesAssembler;
    private EmpleadoModelAssembler empleadoModelAssembler;

    private static final Logger logger = LoggerFactory.getLogger(EmpleadosController.class);

    // Operación POST empleado
    @PostMapping()
    public ResponseEntity<Void> nuevoEmpleado(@Valid @RequestBody Empleado nuevoEmpleado) {
        logger.info("empleado nombre: " + nuevoEmpleado.getNombre());
        // Comprobamos si existe el empleado
        logger.info("El resultado de la query es: " + service.existeEmpleado(nuevoEmpleado.getNombre()) + " id "
                + nuevoEmpleado.getId());
        if (!service.existeEmpleado(nuevoEmpleado.getNombre())) {
            logger.info("No existe, lo creamos");
            // Si no existe lo guardamos en la base de datos
            Empleado empleado = service.crearEmpleado(nuevoEmpleado);
            // Añadimos la cabecera Location con la referencia el nuevo recurso
            return ResponseEntity.created(linkTo(EmpleadosController.class).slash(empleado.getId()).toUri()).build();
        }
        logger.info("Existe lanzamos excepción");
        // Si existe lanzamos una excepción que se captura en la clase
        // EmpleadoExceptionAdvice y envía la respuesta con el código 409 - CONFLICT
        throw new EmpleadoExistsException(nuevoEmpleado.getNombre());

    }

    // Operación GET empleado
    // value para añadir /{id} al path /empleados
    // produces para indicar que produce json y xml. Por defecto json
    @GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/hal+json" })
    public ResponseEntity<Empleado> getEmpleado(@PathVariable Integer id) {
        Empleado empleado = service.buscarPorId(id)
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
        empleado.add(linkTo(methodOn(EmpleadosController.class).getEmpleado(id)).withSelfRel());
        return ResponseEntity.ok(empleado);
    }

    @GetMapping(value = "", produces = { "application/json", "application/xml" })
    public ResponseEntity<PagedModel<Empleado>> getEmpleados(
            @RequestParam(defaultValue = "", required = false) String starts_with,
            @RequestParam(defaultValue = "0", required = false) int page,
            @RequestParam(defaultValue = "2", required = false) int size) {

        Page<Empleado> empleados = service.buscarEmpleados(starts_with, page, size);

        // fetch the page object by additionally passing paginable with the filters
        return ResponseEntity.ok(pagedResourcesAssembler.toModel(empleados, empleadoModelAssembler));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> replaceEmpleado(@Valid @RequestBody Empleado newEmpleado, @PathVariable Integer id) {
        service.buscarPorId(id)
                .map(Empleado -> {
                    Empleado.setNombre(newEmpleado.getNombre());
                    return service.crearEmpleado(Empleado);
                })
                .orElseThrow(() -> new EmpleadoNotFoundException(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteEmpleado(@PathVariable Integer id) {
        if (service.existeEmpleadoPorId(id)) {
            service.eliminarEmpleado(id);
        } else {
            throw new EmpleadoNotFoundException(id);
        }
        return ResponseEntity.noContent().build();
    }

}
