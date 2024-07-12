package df.maya.Literalura.model;

import jakarta.persistence.*;
import java.util.List;

/**
 * Clase que representa un autor de libros.
 */
@Entity
@Table(name = "datos_autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                            // ID del autor
    private String nombre;                      // Nombre del autor
    private Integer fechaNacimiento;           // Año de nacimiento del autor
    private Integer fechaFallecimiento;         // Año de fallecimiento del autor

    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;                 // Lista de libros escritos por el autor

    // Constructores
    public Autor() {
    }

    /**
     * Constructor para crear un objeto Autor a partir de un nombre.
     * @param nombre El nombre del autor.
     */
    public Autor(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Constructor para crear un objeto Autor a partir de los datos de un autor obtenidos de la API.
     * @param datosAutor Los datos del autor obtenidos de la API.
     */
    public Autor(DatosAutor datosAutor) {
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaNacimiento();
        this.fechaFallecimiento = datosAutor.fechaFallecimiento();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Integer getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        return "Autor: " +
                "id=" + id +
                ", Nombre='" + nombre + '\n' +
                ", Fecha nacimiento=" + fechaNacimiento +
                ", Fecha Fallecimiento=" + fechaFallecimiento;
    }
}
