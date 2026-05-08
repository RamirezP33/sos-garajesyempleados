package es.upm.sos.garajesyempleados.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.upm.sos.garajesyempleados.model.GarajeEmpleado;
import es.upm.sos.garajesyempleados.model.GarajeEmpleadoId;

public interface GarajeEmpleadoRepository extends JpaRepository<GarajeEmpleado, GarajeEmpleadoId> {

    // Buscar todos los garajes donde trabaja un empleado específico
    List<GarajeEmpleado> findByGaraje_Id(int garajeId);

}
