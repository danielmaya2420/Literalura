package df.maya.Literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

/**
 * Clase que representa los datos obtenidos de la API.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record Datos(
        @JsonAlias("count") Integer total,  // El número total de resultados obtenidos
        @JsonAlias("results") List<DatosLibros> libros  // La lista de libros obtenidos
) {
}
