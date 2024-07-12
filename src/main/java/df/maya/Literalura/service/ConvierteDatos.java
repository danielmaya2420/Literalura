package df.maya.Literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Esta clase proporciona métodos para convertir datos en objetos de una clase específica.
 */
@Service
public class ConvierteDatos implements IConvierteDatos {

    private final ObjectMapper objectMapper;

    /**
     * Constructor de la clase ConvierteDatos.
     *
     * @param objectMapper ObjectMapper utilizado para la conversión de JSON a objetos.
     */
    @Autowired
    public ConvierteDatos(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /**
     * Convierte un objeto JSON en una instancia de la clase especificada.
     *
     * @param json  Representación en formato JSON de los datos a convertir.
     * @param clase La clase a la que se desea convertir el JSON.
     * @param <T>   El tipo de la clase a la que se desea convertir el JSON.
     * @return Una instancia de la clase especificada.
     * @throws DataConversionException Si hay un error al convertir el JSON a la clase especificada.
     */
    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new DataConversionException("Error al convertir JSON a " + clase.getName(), e);
        }
    }
}

/**
 * Excepción personalizada para errores durante la conversión de datos.
 */
class DataConversionException extends RuntimeException {

    /**
     * Constructor de la excepción DataConversionException.
     *
     * @param message Mensaje de error.
     * @param cause   Causa del error.
     */
    public DataConversionException(String message, Throwable cause) {
        super(message, cause);
    }
}
