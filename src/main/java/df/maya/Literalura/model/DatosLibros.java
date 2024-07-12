package df.maya.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 * Clase que representa los datos de un libro obtenidos de la API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonAlias("id") Long id,                           // ID del libro
        @JsonAlias("title") String titulo,                  // Título del libro
        @JsonAlias("authors") List<DatosAutor> autor,       // Lista de autores del libro
        @JsonAlias("languages") List<String> idiomas,       // Lista de idiomas del libro
        @JsonAlias("download_count") Integer numeroDescargas // Número de descargas del libro
) {
}
