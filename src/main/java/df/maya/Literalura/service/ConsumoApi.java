package df.maya.Literalura.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Esta clase proporciona métodos para realizar solicitudes HTTP a una API.
 */
@Service
public class ConsumoApi {

    // Instancia única de HttpClient para reutilización y mejor rendimiento.
    private final HttpClient client;

    /**
     * Constructor de la clase ConsumoApi.
     * Inicializa el cliente HTTP.
     */
    public ConsumoApi() {
        this.client = HttpClient.newHttpClient();
    }

    /**
     * Realiza una solicitud GET a la URL especificada y devuelve la respuesta en formato de cadena.
     *
     * @param url La URL de la API a la que se realizará la solicitud.
     * @return La respuesta de la API en formato de cadena.
     * @throws ApiException Si ocurre un error al realizar la solicitud HTTP.
     */
    public String obtenerDatos(String url) {
        // Construimos la solicitud HTTP.
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        // Variable para almacenar la respuesta.
        HttpResponse<String> response;
        try {
            // Enviamos la solicitud y obtenemos la respuesta.
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            // Lanzamos una excepción personalizada en caso de error.
            throw new ApiException("Error al obtener datos de la URL: " + url, e);
        }

        // Devolvemos el cuerpo de la respuesta.
        return response.body();
    }
}

/**
 * Excepción personalizada para errores durante la comunicación con la API.
 */
class ApiException extends RuntimeException {

    /**
     * Constructor de la excepción ApiException.
     *
     * @param message Mensaje de error.
     * @param cause   Causa del error.
     */
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
