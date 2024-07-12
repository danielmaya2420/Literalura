package df.maya.Literalura.service;

/**
 * Esta interfaz define un contrato para convertir datos en objetos de una clase específica.
 */
public interface IConvierteDatos {

    /**
     * Convierte un objeto JSON en una instancia de la clase especificada.
     *
     * @param json  Representación en formato JSON de los datos a convertir.
     * @param clase La clase a la que se desea convertir el JSON.
     * @param <T>   El tipo de la clase a la que se desea convertir el JSON.
     * @return Una instancia de la clase especificada.
     */
    <T> T obtenerDatos(String json, Class<T> clase);
}
