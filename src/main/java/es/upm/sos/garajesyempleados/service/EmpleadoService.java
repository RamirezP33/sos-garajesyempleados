package es.upm.sos.garajesyempleados.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.upm.sos.garajesyempleados.model.Empleado;
import es.upm.sos.garajesyempleados.repository.EmpleadoRepository;
import lombok.AllArgsConstructor;

@Service // Marcamos la clase compo componente de servicio
@AllArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository repository;

    public boolean existeEmpleadoPorId(int id) {
        return repository.existsById(id);
    }

    public boolean existeEmpleado(String nombre) {
        return repository.existsByNombre(nombre);
    }

    public Empleado crearEmpleado(Empleado empleado) {
        return repository.save(empleado);
    }

    public Optional<Empleado> buscarPorId(int id) {
        return repository.findById(id);
    }

    public Page<Empleado> buscarEmpleados(String starts_with, int page, int size) {
        // Crear el objeto Pageable usando el número de página, el tamaño y el campo por
        // el que se ordena (name,desc)
        Pageable paginable = PageRequest.of(page, size);
        if (starts_with != null) {
            return repository.findByNombreStartsWith(starts_with, paginable);
        } else {
            return repository.findAll(paginable);
        }
    }

    public void eliminarEmpleado(int id) {
        repository.deleteById(id);
    }

}
