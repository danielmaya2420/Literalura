package df.maya.Literalura.principal;

import df.maya.Literalura.model.*;
import df.maya.Literalura.repository.IAutorRepository;
import df.maya.Literalura.repository.ILibrosRepository;
import df.maya.Literalura.service.ConsumoApi;
import df.maya.Literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Component
public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private final ILibrosRepository repository;
    private final IAutorRepository autorRepo;
    private final ConsumoApi consumoApi;
    private final ConvierteDatos conversor;
    private final Scanner teclado = new Scanner(System.in);

    @Autowired
    public Principal(ILibrosRepository repository, IAutorRepository autorRepo, ConsumoApi consumoApi, ConvierteDatos conversor) {
        this.repository = repository;
        this.autorRepo = autorRepo;
        this.consumoApi = consumoApi;
        this.conversor = conversor;
    }

    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    ============== MENÚ =================
                    |1 - Buscar libro                   |
                    |2 - Buscar autor por nombre        |
                    |3 - Lista libros registrados       |
                    |4 - Lista autores registrados      |
                    |5 - Lista Autores Vivos            |
                    |6 - Lista Libros por Idioma        |
                    |7 - Lista Autores por Año          |
                    |8 - Top 10 Libros más Buscados     |
                    |9 - Generar Estadísticas           |
                    |0 - Salir                          |
                    =====================================
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion) {
                case 1 -> buscarLibroPorTitulo();
                case 2 -> buscarAutor();
                case 3 -> listaLibrosRegistrados();
                case 4 -> listaAutoresRegistrados();
                case 5 -> listaAutoresVivos();
                case 6 -> listarLibrosPorIdioma();
                case 7 -> listaAutoresPorAnio();
                case 8 -> top10Libros();
                case 9 -> generarEstadisticas();
                case 0 -> System.out.println("Cerrando la aplicación...");
                default -> System.out.println("Opción inválida");
            }
        }
    }

    public void buscarLibroPorTitulo() {
        System.out.println("Introduzca el nombre del libro que desea buscar:");
        var nombre = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + "?search=" + nombre.replace(" ", "+").toLowerCase());

        if (json.isEmpty() || !json.contains("\"count\":0,\"next\":null,\"previous\":null,\"results\":[]")) {
            var datos = conversor.obtenerDatos(json, Datos.class);

            Optional<DatosLibros> libroBuscado = datos.libros().stream().findFirst();
            if (libroBuscado.isPresent()) {
                System.out.println(
                        "\n------------- LIBRO --------------" +
                                "\nTítulo: " + libroBuscado.get().titulo() +
                                "\nAutor: " + libroBuscado.get().autor().stream()
                                .map(DatosAutor::nombre).limit(1).collect(Collectors.joining()) +
                                "\nIdioma: " + libroBuscado.get().idiomas().stream().collect(Collectors.joining()) +
                                "\nNúmero de descargas: " + libroBuscado.get().numeroDescargas() +
                                "\n--------------------------------------\n"
                );

                try {
                    List<DatosLibros> datosLibro = datos.libros();
                    for (DatosLibros datosLibros : datosLibro){
                        Libro libroEncontrado = convertirAEntidadLibro(datosLibros);
                    }
                } catch (Exception e) {
                    System.out.println("Warning! " + e.getMessage());
                }
            } else {
                System.out.println("Libro no encontrado!");
            }
        }
    }

    private void buscarAutor() {
        System.out.println("Escribe el nombre del autor a buscar");
        var autorIngresado = teclado.nextLine();
        Optional<Autor> autor = repository.buscarAutorPorNombre(autorIngresado);
        if (autor.isPresent()) {
            System.out.println(
                    "\nAutor: " + autor.get().getNombre() +
                            "\nFecha de nacimiento: " + autor.get().getFechaNacimiento() +
                            "\nFecha de fallecimiento: " + autor.get().getFechaFallecimiento() +
                            "\nLibros: " + autor.get().getLibros().stream()
                            .map(Libro::getTitulo).collect(Collectors.toList()) + "\n"
            );
        } else {
            System.out.println("Lo sentimos no hemos encontrado el autor que buscas");
        }
    }
    public void listaLibrosRegistrados() {
        List<Libro> libros = repository.buscarTodosLosLibros();
        libros.forEach(l -> System.out.println(
                "-------------- LIBRO ----------------" +
                        "\nTítulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nIdioma: " + l.getIdioma().getIdioma() +
                        "\nNúmero de descargas: " + l.getNumeroDescargas() +
                        "\n----------------------------------------\n"
        ));
    }

    public void listaAutoresRegistrados() {
        List<Autor> autores = autorRepo.findAll();
        System.out.println();
        autores.forEach(l -> System.out.println(
                "Autor: " + l.getNombre() +
                        "\nFecha de Nacimiento: " + l.getFechaNacimiento() +
                        "\nFecha de Fallecimiento: " + l.getFechaFallecimiento() +
                        "\nLibros: " + l.getLibros().stream()
                        .map(Libro::getTitulo).collect(Collectors.toList()) + "\n"
        ));
    }

    public void listaAutoresVivos() {
        System.out.println("Introduce el año a consultar:");
        try {
            var fecha = Integer.valueOf(teclado.nextLine());
            List<Autor> autores = repository.buscarAutoresVivos(fecha);
            if (!autores.isEmpty()) {
                System.out.println();
                autores.forEach(a -> System.out.println(
                        "Autor: " + a.getNombre() +
                                "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                                "\nFecha de Fallecimiento: " + a.getFechaFallecimiento() +
                                "\nLibros: " + a.getLibros().stream()
                                .map(Libro::getTitulo).collect(Collectors.toList()) + "\n"
                ));
            } else {
                System.out.println("No hay autores vivos en el año registrado");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ingresa un año válido " + e.getMessage());
        }
    }

    public void listarLibrosPorIdioma() {
        var menu = """
                ---------------------------------------------------
                Seleccione el idioma del libro que deseas buscar:
                1 - Español
                2 - Francés
                3 - Inglés
                4 - Portugués
                ----------------------------------------------------
                """;
        System.out.println(menu);

        try {
            var opcion = Integer.parseInt(teclado.nextLine());

            switch (opcion) {
                case 1 -> buscarLibrosPorIdioma("es");
                case 2 -> buscarLibrosPorIdioma("fr");
                case 3 -> buscarLibrosPorIdioma("en");
                case 4 -> buscarLibrosPorIdioma("pt");
                default -> System.out.println("Opción no valida");
            }
        } catch (NumberFormatException e) {
            System.out.println("La opción no es valida: " + e.getMessage());
        }
    }

    private void buscarLibrosPorIdioma(String idioma) {
        try {
            Idioma idiomaEnum = Idioma.valueOf(idioma.toUpperCase());
            List<Libro> libros = repository.buscarLibrosPorIdioma(idiomaEnum);
            if (libros.isEmpty()) {
                System.out.println("No hay libros registrados en ese idioma");
            } else {
                System.out.println();
                libros.forEach(l -> System.out.println(
                        "----------- LIBRO --------------" +
                                "\nTítulo: " + l.getTitulo() +
                                "\nAutor: " + l.getAutor().getNombre() +
                                "\nIdioma: " + l.getIdioma().getIdioma() +
                                "\nNúmero de descargas: " + l.getNumeroDescargas()
                ));
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Lo sentimos el idioma que busca no se encuentra disponible");
        }
    }

    public void listaAutoresPorAnio() {
        var menu = """
                ------------------------------------------
                Ingresa una opción para listar los autores:
                                    
                1 - Lista autor por Año de Nacimiento
                2 - Lista autor por año de Fallecimiento
                -------------------------------------------
                """;
        System.out.println(menu);
        try {
            var opcion = Integer.parseInt(teclado.nextLine());
            switch (opcion) {
                case 1 -> ListarAutoresPorNacimiento();
                case 2 -> ListarAutoresPorFallecimiento();
                default -> System.out.println("Opción no valida");
            }
        } catch (NumberFormatException e) {
            System.out.println("La opción no es valida: " + e.getMessage());
        }
    }

    public void ListarAutoresPorNacimiento() {
        System.out.println("Introduzca el año de nacimiento del autor que desea buscar:");
        try {
            var nacimiento = Integer.valueOf(teclado.nextLine());
            List<Autor> autores = repository.listarAutoresPorNacimiento(nacimiento);
            if (autores.isEmpty()) {
                System.out.println("No existen autores con año de nacimiento igual a " + nacimiento);
            } else {
                System.out.println();
                autores.forEach(a -> System.out.println(
                        "Autor: " + a.getNombre() +
                                "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                                "\nFecha de Fallecimiento: " + a.getFechaFallecimiento() +
                                "\nLibros: " + a.getLibros().stream().map(Libro::getTitulo).collect(Collectors.toList()) + "\n"
                ));
            }
        } catch (NumberFormatException e) {
            System.out.println("El año ingresado no es válido: " + e.getMessage());
        }
    }

    public void ListarAutoresPorFallecimiento() {
        System.out.println("Introduzca el año de fallecimiento del autor que desea buscar:");
        try {
            var fallecimiento = Integer.valueOf(teclado.nextLine());
            List<Autor> autores = repository.listarAutoresPorFallecimiento(fallecimiento);
            if (autores.isEmpty()) {
                System.out.println("No existen autores con año de fallecimiento igual a " + fallecimiento);
            } else {
                System.out.println();
                autores.forEach(a -> System.out.println(
                        "Autor: " + a.getNombre() +
                                "\nFecha de Nacimiento: " + a.getFechaNacimiento() +
                                "\nFecha de Fallecimeinto: " + a.getFechaFallecimiento() +
                                "\nLibros: " + a.getLibros().stream().map(Libro::getTitulo).collect(Collectors.toList()) + "\n"
                ));
            }
        } catch (NumberFormatException e) {
            System.out.println("Opción no válida: " + e.getMessage());
        }
    }

    public void top10Libros() {
        List<Libro> libros = repository.top10Libros();
        System.out.println();
        libros.forEach(l -> System.out.println(
                "----------------- LIBRO ----------------" +
                        "\nTítulo: " + l.getTitulo() +
                        "\nAutor: " + l.getAutor().getNombre() +
                        "\nIdioma: " + l.getIdioma().getIdioma() +
                        "\nNúmero de descargas: " + l.getNumeroDescargas() +
                        "\n-------------------------------------------\n"
        ));
    }

    public void generarEstadisticas() {
        var json = consumoApi.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json, Datos.class);
        IntSummaryStatistics est = datos.libros().stream()
                .filter(l -> l.numeroDescargas() > 0)
                .collect(Collectors.summarizingInt(DatosLibros::numeroDescargas));
        int media = (int) est.getAverage();
        System.out.println("\n--------- ESTADÍSTICAS ------------");
        System.out.println("Media de descargas: " + media);
        System.out.println("Máxima de descargas: " + est.getMax());
        System.out.println("Mínima de descargas: " + est.getMin());
        System.out.println("Total registros para calcular las estadísticas: " + est.getCount());
    }

    public Libro convertirAEntidadLibro(DatosLibros datosLibro) {
        if (datosLibro == null) {
            return null; // Retorna null si datosLibro es nulo
        }

        Libro libro = new Libro();

        Optional<Libro> libroEncontrado = repository.findById(datosLibro.id());
        if (libroEncontrado.isPresent()) {
            libro = libroEncontrado.get();
        } else {
            libro.setId(datosLibro.id());
            libro.setTitulo(datosLibro.titulo());

            // Establecer idioma
            if (datosLibro.idiomas() != null && !datosLibro.idiomas().isEmpty()) {
                Idioma enumLenguaje = null;
                for (String lenguaje : datosLibro.idiomas()) {
                    enumLenguaje = Idioma.fromString(lenguaje);
                    libro.setIdioma(enumLenguaje);
                }
                if (enumLenguaje == null) {
                    libro.setIdioma(Idioma.OTRO);
                }
            }

            libro.setNumeroDescargas(datosLibro.numeroDescargas());

            // Establecer autores
            List<DatosAutor> datosAutores = datosLibro.autor();
            for (DatosAutor datosAutor : datosAutores) {
                Autor autor = autorRepo.findByNombreAndFechaNacimientoAndFechaFallecimiento(
                        datosAutor.nombre(), datosAutor.fechaNacimiento(), datosAutor.fechaFallecimiento());

                if (autor == null) {
                    autor = new Autor();
                    autor.setNombre(datosAutor.nombre());
                    autor.setFechaNacimiento(datosAutor.fechaNacimiento());
                    autor.setFechaFallecimiento(datosAutor.fechaFallecimiento());
                    autorRepo.save(autor);
                }
                libro.setAutor(autor);
            }

            // Guardar el libro en el repositorio
            repository.save(libro);
        }

        return libro;
    }
}