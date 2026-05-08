package es.upm.sos.garajesyempleados.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.upm.sos.garajesyempleados.model.Garaje;
import es.upm.sos.garajesyempleados.repository.GarajeRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GarajeService {

    private final GarajeRepository repository;

    public boolean buscarPorNombreyDireccion(String nombre, String direccion) {
        Optional<Garaje> garaje = repository.findByNameAndDirection(nombre, direccion);
        return garaje.isPresent();
    }

    public Garaje crearGaraje(Garaje garaje) {
        return repository.save(garaje);
    }

    public Optional<Garaje> buscarPorId(int id) {
        return repository.findById(id);
    }

    public boolean existeGarajePorId(int id) {
        return repository.existsById(id);
    }

    public Page<Garaje> buscarGarajes(String startsWith, int page, int size) {
        // Crear el objeto Pageable usando el número de página, el tamaño y el campo por
        // el que se ordena (name,desc)
        Pageable paginable = PageRequest.of(page, size);
        if (startsWith != null) {
            return repository.findByNombreStartsWith(startsWith, paginable);
        } else {
            return repository.findAll(paginable);
        }
    }

    public void eliminarGaraje(int id) {
        repository.deleteById(id);
    }

}
