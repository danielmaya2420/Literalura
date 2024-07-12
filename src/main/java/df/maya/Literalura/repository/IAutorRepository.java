package df.maya.Literalura.repository;

import df.maya.Literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Interfaz de repositorio para la entidad Autor.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos para la entidad Autor.
 */
public interface IAutorRepository extends JpaRepository<Autor, Long> {

    /**
     * Busca un autor por su nombre y fechas de nacimiento y fallecimiento.
     *
     * @param nombre              El nombre del autor a buscar.
     * @param fechaNacimiento     La fecha de nacimiento del autor a buscar.
     * @param fechaFallecimiento  La fecha de fallecimiento del autor a buscar.
     * @return                    El autor encontrado que coincide con los parámetros proporcionados.
     */
    Autor findByNombreAndFechaNacimientoAndFechaFallecimiento(String nombre, Integer fechaNacimiento,
                                                              Integer fechaFallecimiento);
}
