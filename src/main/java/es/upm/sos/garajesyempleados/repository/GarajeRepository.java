package es.upm.sos.garajesyempleados.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.upm.sos.garajesyempleados.model.Garaje;

public interface GarajeRepository extends JpaRepository<Garaje, Integer> {

    @Query(value = "SELECT * FROM garajes WHERE nombre = ?1 and direccion = ?2", nativeQuery = true)
    Optional<Garaje> findByNameAndDirection(@Param("nombre") String nombre,
            @Param("direccion") String direccion);

    // Buscar los garaje cuyo nombre empiece por...
    Page<Garaje> findByNombreStartsWith(@Param("starts_with") String starts_with, Pageable pageable);

}
