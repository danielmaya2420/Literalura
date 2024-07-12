package df.maya.Literalura.repository;

import df.maya.Literalura.model.Libro;
import df.maya.Literalura.model.Autor;
import df.maya.Literalura.model.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz de repositorio para la entidad Libro.
 * Proporciona métodos para realizar operaciones CRUD en la base de datos para la entidad Libro.
 */
public interface ILibrosRepository extends JpaRepository<Libro, Long> {

    /**
     * Busca un libro por su título.
     *
     * @param nombre El título del libro a buscar.
     * @return El libro encontrado que coincide con el título proporcionado.
     */
    @Query("SELECT l FROM Libro l JOIN l.autor a WHERE l.titulo LIKE %:nombre%")
    Optional<Libro> buscarLibroPorNombre(@Param("nombre") String nombre);

    /**
     * Busca un autor por su nombre.
     *
     * @param nombre El nombre del autor a buscar.
     * @return El autor encontrado que coincide con el nombre proporcionado.
     */
    @Query("SELECT a FROM Libro l JOIN l.autor a WHERE a.nombre LIKE %:nombre%")
    Optional<Autor> buscarAutorPorNombre(@Param("nombre") String nombre);

    /**
     * Busca todos los libros registrados.
     *
     * @return La lista de todos los libros registrados.
     */
    @Query("SELECT l FROM Autor a JOIN a.libros l")
    List<Libro> buscarTodosLosLibros();

    /**
     * Busca autores que estén vivos en una fecha dada.
     *
     * @param fecha La fecha para buscar autores vivos.
     * @return La lista de autores vivos en la fecha proporcionada.
     */
    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento > :fecha")
    List<Autor> buscarAutoresVivos(@Param("fecha") Integer fecha);

    /**
     * Busca libros por idioma.
     *
     * @param idioma El idioma para buscar libros.
     * @return La lista de libros en el idioma especificado.
     */
    @Query("SELECT l FROM Autor a JOIN a.libros l WHERE l.idioma = :idioma")
    List<Libro> buscarLibrosPorIdioma(@Param("idioma") Idioma idioma);

    /**
     * Lista autores por año de nacimiento.
     *
     * @param fecha El año de nacimiento para buscar autores.
     * @return La lista de autores nacidos en el año proporcionado.
     */
    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento = :fecha")
    List<Autor> listarAutoresPorNacimiento(@Param("fecha") Integer fecha);

    /**
     * Lista autores por año de fallecimiento.
     *
     * @param fecha El año de fallecimiento para buscar autores.
     * @return La lista de autores fallecidos en el año proporcionado.
     */
    @Query("SELECT a FROM Autor a WHERE a.fechaFallecimiento = :fecha")
    List<Autor> listarAutoresPorFallecimiento(@Param("fecha") Integer fecha);

    /**
     * Obtiene los 10 libros más buscados.
     *
     * @return La lista de los 10 libros más buscados.
     */
    @Query("SELECT l FROM Autor a JOIN a.libros l ORDER BY l.numeroDescargas DESC")
    List<Libro> top10Libros();
}
