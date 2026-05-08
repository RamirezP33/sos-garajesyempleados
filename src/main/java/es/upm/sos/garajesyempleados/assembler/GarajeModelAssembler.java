package es.upm.sos.garajesyempleados.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import es.upm.sos.garajesyempleados.controller.GarajesController;
import es.upm.sos.garajesyempleados.model.Garaje;

/**
 * This class extends RepresentationModelAssemblerSupport which is required for
 * Pagination.
 * It converts the Customer Entity to the Customer Model and has the code for it
 */
@Component
public class GarajeModelAssembler extends RepresentationModelAssemblerSupport<Garaje, Garaje> {
    public GarajeModelAssembler() {
        super(GarajesController.class, Garaje.class);
    }

    @Override
    public Garaje toModel(Garaje entity) {

        entity.add(linkTo(methodOn(GarajesController.class).getGaraje(entity.getId())).withSelfRel());

        return entity;
    }
}
