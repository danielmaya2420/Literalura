package df.maya.Literalura.model;

import jakarta.persistence.*;

/**
 * Clase que representa un libro.
 */
@Entity
@Table(name = "libros")
public class Libro {
    @Id
    private Long id;                        // ID del libro
    private String titulo;                  // Título del libro
    @Enumerated(EnumType.STRING)
    private Idioma idioma;                 // Idioma del libro
    private Integer numeroDescargas;       // Número de descargas del libro
    @ManyToOne
    private Autor autor;                   // Autor del libro

    // Constructores
    public Libro() {
    }

    /**
     * Constructor para crear un objeto Libro a partir de los datos de un libro obtenidos de la API.
     *
     * @param libro Los datos del libro obtenidos de la API.
     */
    public Libro(DatosLibros libro) {
        this.id = libro.id();
        this.titulo = libro.titulo();
        this.idioma = Idioma.fromString(libro.idiomas().stream().limit(1).findFirst().orElse(""));
        this.numeroDescargas = libro.numeroDescargas();
    }

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Integer numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return "Libro: " +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", idioma=" + idioma +
                ", número descargas=" + numeroDescargas +
                ", autor=" + autor;
    }
}
