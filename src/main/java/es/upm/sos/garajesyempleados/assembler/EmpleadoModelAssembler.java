package es.upm.sos.garajesyempleados.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.garajesyempleados.controller.EmpleadosController;
import es.upm.sos.garajesyempleados.model.Empleado;

/**
 * This class extends RepresentationModelAssemblerSupport which is required for
 * Pagination.
 * It converts the Customer Entity to the Customer Model and has the code for it
 */
@Component
public class EmpleadoModelAssembler extends RepresentationModelAssemblerSupport<Empleado, Empleado> {
    public EmpleadoModelAssembler() {
        super(EmpleadosController.class, Empleado.class);
    }

    @Override
    public Empleado toModel(Empleado entity) {

        entity.add(linkTo(methodOn(EmpleadosController.class).getEmpleado(entity.getId())).withSelfRel());

        return entity;
    }
}
