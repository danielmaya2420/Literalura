package df.maya.Literalura.model;

/**
 * Enumeración que representa los diferentes idiomas disponibles.
 */
public enum Idioma {
    ES("es"),  // Español
    FR("fr"),  // Francés
    EN("en"),  // Inglés
    PT("pt"),  // Portugués
    OTRO("otro");  // Otro idioma

    // Atributo para almacenar el código del idioma
    private final String idioma;

    /**
     * Constructor privado de la enumeración Idioma.
     *
     * @param idioma El código del idioma.
     */
    Idioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * Método para obtener el código del idioma.
     *
     * @return El código del idioma.
     */
    public String getIdioma() {
        return this.idioma;
    }

    /**
     * Método estático para obtener un valor de la enumeración a partir de su representación en texto.
     *
     * @param text La representación en texto del idioma.
     * @return El objeto Idioma correspondiente.
     * @throws IllegalArgumentException si no se encuentra ningún idioma con el texto dado.
     */
    public static Idioma fromString(String text) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.idioma.equalsIgnoreCase(text)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("No se encontró ninguna constante con el texto " + text);
    }
}
