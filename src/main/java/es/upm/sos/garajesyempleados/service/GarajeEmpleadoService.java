package es.upm.sos.garajesyempleados.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.upm.sos.garajesyempleados.model.Empleado;
import es.upm.sos.garajesyempleados.model.Garaje;
import es.upm.sos.garajesyempleados.model.GarajeEmpleado;
import es.upm.sos.garajesyempleados.model.GarajeEmpleadoId;
import es.upm.sos.garajesyempleados.repository.GarajeEmpleadoRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GarajeEmpleadoService {

    private final GarajeEmpleadoRepository repository;

    public List<GarajeEmpleado> buscarPorGarajeId(int id) {
        return repository.findByGaraje_Id(id);
    }

    public void contratarEmpleadoEnGaraje(GarajeEmpleadoId geId, Garaje garaje, Empleado empleado) {
        // Crear la clave primaria compuesta
        geId.setGarajeId(garaje.getId());

        // Crear la relación
        GarajeEmpleado relacion = new GarajeEmpleado();
        relacion.setId(geId);
        relacion.setGaraje(garaje);
        relacion.setEmpleado(empleado);

        // Guardar en la base de datos
        repository.save(relacion);
    }

    public int contarEmpleadosPorGarajeId(int garajeId) {
        return repository.findByGaraje_Id(garajeId).size();
    }

}
