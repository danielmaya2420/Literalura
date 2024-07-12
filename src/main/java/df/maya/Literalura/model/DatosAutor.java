package df.maya.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Clase que representa los datos del autor obtenidos de la API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutor(
        @JsonAlias("name") String nombre,                 // Nombre del autor
        @JsonAlias("birth_year") Integer fechaNacimiento, // Año de nacimiento del autor
        @JsonAlias("death_year") Integer fechaFallecimiento // Año de fallecimiento del autor
) {
}
