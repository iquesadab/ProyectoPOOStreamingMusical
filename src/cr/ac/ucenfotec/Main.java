package cr.ac.ucenfotec;

import cr.ac.ucenfotec.bl.*;
import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Main {

    // Permite leer los datos ingresados por el usuario desde la consola.
    public static BufferedReader entrada =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {

        // Se crea el objeto que representa el sistema completo.
        Aplicacion aplicacion = new Aplicacion();

        /*
         * La aplicación debe tener un administrador registrado antes de
         * permitir el acceso a cualquier otra funcionalidad.
         */
        while (!aplicacion.hayAdministrador()) {

            System.out.println(
                    "\nDebe registrar un administrador antes de utilizar la aplicación.");

            registrarAdministrador(aplicacion);
        }

        // Cuando ya existe el administrador, se muestra el menú principal.
        menuPrincipal(aplicacion);
    }

    // Método que muestra el menú principal de la aplicación.
    public static void menuPrincipal(Aplicacion aplicacion)
            throws IOException {

        boolean salir = false;

        while (!salir) {

            System.out.println(
                    "\n============================================");
            System.out.println(
                    "       APLICACIÓN DE STREAMING MUSICAL");
            System.out.println(
                    "============================================");
            System.out.println("1. Iniciar sesión como administrador");
            System.out.println("2. Registrar usuario final");
            System.out.println("3. Iniciar sesión como usuario final");
            System.out.println("4. Salir");
            System.out.println(
                    "============================================");

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    iniciarSesionAdministrador(aplicacion);
                    break;

                case 2:
                    registrarUsuarioFinal(aplicacion);
                    break;

                case 3:
                    iniciarSesionUsuarioFinal(aplicacion);
                    break;

                case 4:
                    salir = true;

                    System.out.println(
                            "\nGracias por utilizar la aplicación.");
                    break;

                default:
                    System.out.println(
                            "La opción seleccionada no es válida.");
                    break;
            }
        }
    }

    // Método para registrar obligatoriamente al administrador.
    public static void registrarAdministrador(Aplicacion aplicacion)
            throws IOException {

        boolean administradorRegistrado = false;

        while (!administradorRegistrado) {

            System.out.println(
                    "\n============================================");
            System.out.println(
                    "        REGISTRO DEL ADMINISTRADOR");
            System.out.println(
                    "============================================");

            System.out.print("Correo electrónico: ");
            String correoElectronico = entrada.readLine();

            System.out.print("Nombre de usuario: ");
            String nombreUsuario = entrada.readLine();

            System.out.print("Contraseña: ");
            String contrasenia = entrada.readLine();

            System.out.print("Confirmar contraseña: ");
            String confirmarContrasenia = entrada.readLine();

            /*
             * cr.ac.ucenfotec.bl.Aplicacion se encarga de validar los datos y crear
             * el objeto cr.ac.ucenfotec.bl.entities.administrador.Administrador.
             */
            administradorRegistrado =
                    aplicacion.registrarAdministrador(
                            correoElectronico,
                            nombreUsuario,
                            contrasenia,
                            confirmarContrasenia
                    );

            if (!administradorRegistrado) {

                System.out.println(
                        "\nNo fue posible registrar el administrador.");
                System.out.println(
                        "Revise los datos e inténtelo nuevamente.");
            }
        }
    }

    // Método para iniciar sesión como administrador.
    public static void iniciarSesionAdministrador(Aplicacion aplicacion)
            throws IOException {

        System.out.println(
                "\n============================================");
        System.out.println(
                "      INICIO DE SESIÓN DEL ADMINISTRADOR");
        System.out.println(
                "============================================");

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        boolean accesoPermitido =
                aplicacion.iniciarSesionAdministrador(
                        nombreUsuario,
                        contrasenia
                );

        if (accesoPermitido) {

            System.out.println(
                    "\nInicio de sesión realizado correctamente.");

            menuAdministrador(aplicacion);

        } else {

            System.out.println(
                    "\nNombre de usuario o contraseña incorrectos.");
        }
    }

    // Método que muestra las opciones disponibles para el administrador.
    public static void menuAdministrador(Aplicacion aplicacion)
            throws IOException {

        boolean cerrarSesion = false;

        while (!cerrarSesion) {

            System.out.println(
                    "\n============================================");
            System.out.println(
                    "            MENÚ ADMINISTRADOR");
            System.out.println(
                    "============================================");
            System.out.println("1. Registrar canción");
            System.out.println("2. Mostrar catálogo de canciones");
            System.out.println("3. Buscar canción por nombre");
            System.out.println("4. Buscar canciones por género");
            System.out.println("5. Buscar canciones por artista");
            System.out.println("6. Mostrar usuarios registrados");
            System.out.println("7. Agregar canción a la cola");
            System.out.println("8. Mostrar cola de reproducción");
            System.out.println("9. Reproducir siguiente canción");
            System.out.println("10. Consultar Top 3");
            System.out.println("11. Cambiar contraseña");
            System.out.println("12. Cerrar sesión");
            System.out.println(
                    "============================================");

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    registrarCancion(aplicacion);
                    break;

                case 2:
                    aplicacion.mostrarCatalogoCanciones();
                    break;

                case 3:
                    buscarCancionPorNombre(aplicacion);
                    break;

                case 4:
                    buscarCancionesPorGenero(aplicacion);
                    break;

                case 5:
                    buscarCancionesPorArtista(aplicacion);
                    break;

                case 6:
                    aplicacion.mostrarUsuariosFinales();
                    break;

                case 7:
                    agregarCancionColaAdministrador(aplicacion);
                    break;

                case 8:
                    System.out.println(
                            "\n===== COLA DEL ADMINISTRADOR =====");

                    System.out.println(
                            aplicacion.getAdministrador()
                                    .getColaReproduccion());
                    break;

                case 9:
                    aplicacion.getAdministrador()
                            .getColaReproduccion()
                            .reproducirSiguiente();
                    break;

                case 10:
                    menuTop3(aplicacion);
                    break;

                case 11:
                    cambiarContraseniaAdministrador(aplicacion);
                    break;

                case 12:
                    cerrarSesion = true;
                    System.out.println(
                            "\nSesión del administrador cerrada.");
                    break;

                default:
                    System.out.println(
                            "La opción seleccionada no es válida.");
                    break;
            }
        }
    }

    // Método para registrar una canción en el catálogo.
    public static void registrarCancion(Aplicacion aplicacion)
            throws IOException {

        System.out.println(
                "\n============================================");
        System.out.println(
                "             REGISTRO DE CANCIÓN");
        System.out.println(
                "============================================");

        System.out.print("Nombre de la canción: ");
        String nombre = entrada.readLine();

        System.out.print("Género: ");
        String genero = entrada.readLine();

        LocalDate fechaLanzamiento =
                leerFecha("Fecha de lanzamiento (AAAA-MM-DD): ");

        float precio = leerFloat("Precio en dólares: ");

        /*
         * La canción inicia con calificación cero.
         * La calificación debe actualizarse según las valoraciones
         * realizadas posteriormente por los usuarios.
         */
        float calificacionInicial = 0.0f;

        System.out.print("Artista: ");
        String artista = entrada.readLine();

        System.out.print("Compositor: ");
        String compositor = entrada.readLine();

        System.out.print(
                "Nombre del álbum (presione Enter si no tiene): ");
        String nombreAlbum = entrada.readLine();

        System.out.print(
                "Carátula del álbum (presione Enter si no tiene): ");
        String caratulaAlbum = entrada.readLine();

        // Se crea la canción con los datos ingresados.
        Cancion nuevaCancion = new Cancion(
                nombre,
                genero,
                fechaLanzamiento,
                precio,
                calificacionInicial,
                artista,
                compositor,
                nombreAlbum,
                caratulaAlbum
        );

        // cr.ac.ucenfotec.bl.Aplicacion valida y agrega la canción al catálogo.
        aplicacion.registrarCancion(nuevaCancion);
    }

    // Método para buscar una canción por nombre.
    public static Cancion buscarCancionPorNombre(
            Aplicacion aplicacion) throws IOException {

        System.out.print("\nIngrese el nombre de la canción: ");
        String nombre = entrada.readLine();

        Cancion cancionEncontrada =
                aplicacion.buscarCancionPorNombre(nombre);

        if (cancionEncontrada == null) {

            System.out.println(
                    "No se encontró ninguna canción con ese nombre.");

        } else {

            System.out.println("\nCanción encontrada:");
            System.out.println("---------------------------");
            System.out.println(cancionEncontrada);
        }

        return cancionEncontrada;
    }

    // Método para buscar canciones por género
    public static void buscarCancionesPorGenero(
            Aplicacion aplicacion) throws IOException {

        System.out.print("\nIngrese el género que desea buscar: ");
        String genero = entrada.readLine();

        ArrayList<Cancion> cancionesEncontradas =
                aplicacion.buscarCancionesPorGenero(genero);

        aplicacion.mostrarCancionesEncontradas(
                cancionesEncontradas
        );
    }

    // Método para buscar canciones por artista
    public static void buscarCancionesPorArtista(
            Aplicacion aplicacion) throws IOException {

        System.out.print("\nIngrese el artista que desea buscar: ");
        String artista = entrada.readLine();

        ArrayList<Cancion> cancionesEncontradas =
                aplicacion.buscarCancionesPorArtista(artista);

        aplicacion.mostrarCancionesEncontradas(
                cancionesEncontradas
        );
    }

    // Método para agregar una canción a la cola del administrador.
    public static void agregarCancionColaAdministrador(
            Aplicacion aplicacion) throws IOException {

        Cancion cancion = buscarCancionPorNombre(aplicacion);

        if (cancion != null) {

            aplicacion.getAdministrador()
                    .agregarCancionACola(cancion);
        }
    }

    // Método para consultar los distintos Top 3 de canciones
    public static void menuTop3(Aplicacion aplicacion)
            throws IOException {

        boolean volver = false;

        while (!volver) {

            System.out.println("\n===== TOP 3 DE CANCIONES =====");
            System.out.println("1. Canciones mejor calificadas");
            System.out.println("2. Canciones más compradas");
            System.out.println("3. Canciones más agregadas a listas");
            System.out.println("4. Volver");

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    aplicacion.getTopCanciones()
                            .mostrarTop3MejorCalificadas(
                                    aplicacion.getCatalogoCanciones()
                            );
                    break;

                case 2:
                    aplicacion.getTopCanciones()
                            .mostrarTop3MasCompradas(
                                    aplicacion.getCatalogoCanciones()
                            );
                    break;

                case 3:
                    aplicacion.getTopCanciones()
                            .mostrarTop3MasAgregadasAListas(
                                    aplicacion.getCatalogoCanciones()
                            );
                    break;

                case 4:
                    volver = true;
                    break;

                default:
                    System.out.println(
                            "La opción seleccionada no es válida.");
                    break;
            }
        }
    }

    // Método para cambiar la contraseña del administrador.
    public static void cambiarContraseniaAdministrador(
            Aplicacion aplicacion) throws IOException {

        System.out.println(
                "\n===== CAMBIO DE CONTRASEÑA =====");

        System.out.print("Contraseña actual: ");
        String contraseniaActual = entrada.readLine();

        System.out.print("Nueva contraseña: ");
        String nuevaContrasenia = entrada.readLine();

        System.out.print("Confirmar nueva contraseña: ");
        String confirmacion = entrada.readLine();

        /*
         * Este método debe existir en la clase cr.ac.ucenfotec.bl.entities.usuario.Usuario o ser heredado
         * por cr.ac.ucenfotec.bl.entities.administrador.Administrador. Si todavía no está implementado, se
         * integrará durante la revisión final.
         */
        aplicacion.getAdministrador().cambiarContrasenia(
                contraseniaActual,
                nuevaContrasenia,
                confirmacion
        );
    }

    // Método para registrar un nuevo usuario final.
    public static void registrarUsuarioFinal(Aplicacion aplicacion)
            throws IOException {

        System.out.println(
                "\n============================================");
        System.out.println(
                "          REGISTRO DE USUARIO FINAL");
        System.out.println(
                "============================================");

        System.out.print("Nombre completo: ");
        String nombreCompleto = entrada.readLine();

        LocalDate fechaNacimiento =
                leerFecha("Fecha de nacimiento (AAAA-MM-DD): ");

        // Muestra las nacionalidades que acepta la aplicación.
        aplicacion.mostrarNacionalidadesPermitidas();

        int opcionNacionalidad =
                leerEntero("Seleccione su nacionalidad: ");

        ArrayList<String> nacionalidades =
                aplicacion.getNacionalidadesPermitidas();

        /*
         * Se valida que la posición seleccionada se encuentre
         * dentro del ArrayList.
         */
        if (opcionNacionalidad < 1
                || opcionNacionalidad > nacionalidades.size()) {

            System.out.println(
                    "La nacionalidad seleccionada no es válida.");
            return;
        }

        String nacionalidad =
                nacionalidades.get(opcionNacionalidad - 1);

        System.out.print("Cédula: ");
        String cedula = entrada.readLine();

        System.out.print(
                "Avatar (presione Enter para usar el predeterminado): ");
        String avatar = entrada.readLine();

        System.out.print("Correo electrónico: ");
        String correoElectronico = entrada.readLine();

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        System.out.print("Confirmar contraseña: ");
        String confirmarContrasenia = entrada.readLine();

        /*
         * Estos valores se conservan temporalmente porque
         * cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal todavía utiliza arreglos de tamaño fijo.
         */
        byte cantidadMaximaCanciones = 50;
        byte cantidadMaximaListas = 20;

        UsuarioFinal nuevoUsuario =
                aplicacion.registrarUsuarioFinal(
                        nombreCompleto,
                        fechaNacimiento,
                        nacionalidad,
                        cedula,
                        avatar,
                        correoElectronico,
                        nombreUsuario,
                        contrasenia,
                        confirmarContrasenia,
                        cantidadMaximaCanciones,
                        cantidadMaximaListas
                );

        if (nuevoUsuario == null) {

            System.out.println(
                    "\nNo fue posible completar el registro.");
        }
    }

    // Método para iniciar sesión como usuario final.
    public static void iniciarSesionUsuarioFinal(
            Aplicacion aplicacion) throws IOException {

        System.out.println(
                "\n============================================");
        System.out.println(
                "       INICIO DE SESIÓN DEL USUARIO");
        System.out.println(
                "============================================");

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        UsuarioFinal usuario =
                aplicacion.iniciarSesionUsuarioFinal(
                        nombreUsuario,
                        contrasenia
                );

        if (usuario != null) {

            menuUsuarioFinal(aplicacion, usuario);

        } else {

            System.out.println(
                    "\nNo fue posible iniciar sesión.");
        }
    }

    // Método que muestra las opciones disponibles para el usuario final.
    public static void menuUsuarioFinal(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        boolean cerrarSesion = false;

        while (!cerrarSesion) {

            System.out.println(
                    "\n============================================");
            System.out.println(
                    "             MENÚ USUARIO FINAL");
            System.out.println(
                    "============================================");
            System.out.println("1. Mostrar mis datos");
            System.out.println("2. Mostrar catálogo");
            System.out.println("3. Buscar canción por nombre");
            System.out.println("4. Buscar canciones por género");
            System.out.println("5. Buscar canciones por artista");
            System.out.println("6. Comprar canción");
            System.out.println("7. Recargar saldo");
            System.out.println("8. Crear lista de reproducción");
            System.out.println("9. Mostrar mis listas");
            System.out.println("10. Agregar canción a una lista");
            System.out.println("11. Reproducir una lista");
            System.out.println("12. Calificar canción");
            System.out.println("13. Agregar canción a la cola");
            System.out.println("14. Mostrar cola de reproducción");
            System.out.println("15. Reproducir siguiente canción");
            System.out.println("16. Consultar Top 3");
            System.out.println("17. Escuchar vista previa");
            System.out.println("18. Cambiar contraseña");
            System.out.println("19. Cerrar sesión");
            System.out.println(
                    "============================================");

            int opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {

                case 1:
                    System.out.println("\n" + usuario);
                    break;

                case 2:
                    aplicacion.mostrarCatalogoCanciones();
                    break;

                case 3:
                    buscarCancionPorNombre(aplicacion);
                    break;

                case 4:
                    buscarCancionesPorGenero(aplicacion);
                    break;

                case 5:
                    buscarCancionesPorArtista(aplicacion);
                    break;

                case 6:
                    comprarCancion(aplicacion, usuario);
                    break;

                case 7:
                    recargarSaldo(usuario);
                    break;

                case 8:
                    crearListaReproduccion(usuario);
                    break;

                case 9:
                    mostrarListasUsuario(usuario);
                    break;

                case 10:
                    agregarCancionALista(aplicacion, usuario);
                    break;

                case 11:
                    reproducirListaUsuario(usuario);
                    break;

                case 12:
                    calificarCancion(aplicacion, usuario);
                    break;

                case 13:
                    agregarCancionColaUsuario(aplicacion, usuario);
                    break;

                case 14:
                    System.out.println(
                            "\n===== MI COLA DE REPRODUCCIÓN =====");

                    System.out.println(usuario.getColaReproduccion());
                    break;

                case 15:
                    usuario.getColaReproduccion().reproducirSiguiente();
                    break;

                case 16:
                    menuTop3(aplicacion);
                    break;

                case 17:
                    vistaPreviaCancion(aplicacion);
                    break;

                case 18:
                    cambiarContraseniaUsuario(usuario);
                    break;

                case 19:
                    cerrarSesion = true;
                    System.out.println("\nSesión del usuario cerrada.");
                    break;

                default:
                    System.out.println(
                            "La opción seleccionada no es válida.");
                    break;
            }
        }
    }

    // Método para comprar una canción del catálogo.
    public static void comprarCancion(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        Cancion cancion = buscarCancionPorNombre(aplicacion);

        if (cancion != null) {
            usuario.comprarCancion(cancion);
        }
    }

    // Método para recargar el saldo del usuario.
    public static void recargarSaldo(UsuarioFinal usuario)
            throws IOException {

        float monto = leerFloat(
                "\nIngrese el monto de la recarga: ");

        usuario.recargarSaldo(monto);
    }

    // Método para crear una lista de reproducción.
    public static void crearListaReproduccion(
            UsuarioFinal usuario) throws IOException {

        System.out.print(
                "\nIngrese el nombre de la nueva lista: ");

        String nombreLista = entrada.readLine();

        usuario.crearListaReproduccion(nombreLista);
    }

    // Método para agregar una canción comprada a una lista de reproducción
    public static void agregarCancionALista(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        // Verifica si el usuario tiene listas creadas.
        if (usuario.getCantidadListasReproduccion() == 0) {
            System.out.println(
                    "Primero debe crear una lista de reproducción.");
            return;
        }

        System.out.println("\n===== MIS LISTAS DE REPRODUCCIÓN =====");

        ListaReproduccion[] listas =
                usuario.getListasReproduccion();

        // Muestra únicamente las listas creadas por el usuario.
        for (int i = 0;
             i < usuario.getCantidadListasReproduccion();
             i++) {

            System.out.println(
                    (i + 1) + ". " + listas[i].getNombre());
        }

        int opcionLista =
                leerEntero("Seleccione una lista: ");

        // Verifica que la opción seleccionada sea válida.
        if (opcionLista < 1
                || opcionLista >
                usuario.getCantidadListasReproduccion()) {

            System.out.println(
                    "La lista seleccionada no es válida.");
            return;
        }

        ListaReproduccion listaSeleccionada =
                listas[opcionLista - 1];

        // Busca la canción que el usuario desea agregar.
        Cancion cancion =
                buscarCancionPorNombre(aplicacion);

        if (cancion != null) {
            usuario.agregarCancionALista(
                    cancion,
                    listaSeleccionada
            );
        }
    }

    // Método para mostrar las listas de reproducción del usuario
    public static void mostrarListasUsuario(UsuarioFinal usuario) {

        // Verifica si el usuario tiene listas creadas.
        if (usuario.getCantidadListasReproduccion() == 0) {
            System.out.println(
                    "El usuario todavía no tiene listas de reproducción.");
            return;
        }

        ListaReproduccion[] listas =
                usuario.getListasReproduccion();

        System.out.println(
                "\n===== MIS LISTAS DE REPRODUCCIÓN =====");

        // Recorre solamente las posiciones ocupadas del arreglo.
        for (int i = 0;
             i < usuario.getCantidadListasReproduccion();
             i++) {

            System.out.println(
                    "\nLista número " + (i + 1));

            System.out.println("---------------------------");
            System.out.println(listas[i]);
        }
    }

    // Método para seleccionar y reproducir una lista del usuario
    public static void reproducirListaUsuario(
            UsuarioFinal usuario) throws IOException {

        // Verifica si el usuario tiene listas creadas.
        if (usuario.getCantidadListasReproduccion() == 0) {
            System.out.println(
                    "No tiene listas de reproducción para reproducir.");
            return;
        }

        ListaReproduccion[] listas =
                usuario.getListasReproduccion();

        System.out.println(
                "\n===== MIS LISTAS DE REPRODUCCIÓN =====");

        // Muestra las listas disponibles.
        for (int i = 0;
             i < usuario.getCantidadListasReproduccion();
             i++) {

            System.out.println(
                    (i + 1) + ". " + listas[i].getNombre());
        }

        int opcionLista =
                leerEntero("Seleccione la lista que desea reproducir: ");

        // Verifica que la opción esté dentro del rango permitido.
        if (opcionLista < 1
                || opcionLista >
                usuario.getCantidadListasReproduccion()) {

            System.out.println(
                    "La lista seleccionada no es válida.");
            return;
        }

        ListaReproduccion listaSeleccionada =
                listas[opcionLista - 1];

        // Reproduce todas las canciones de la lista.
        listaSeleccionada.reproducirLista();
    }

    // Método para calificar una canción comprada por el usuario
    public static void calificarCancion(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        Cancion cancion =
                buscarCancionPorNombre(aplicacion);

        if (cancion == null) {
            return;
        }

        float calificacion =
                leerFloat(
                        "Ingrese una calificación entre 0.0 y 5.0: "
                );

        /*
         * cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal verifica que la canción haya sido comprada
         * y que la calificación esté dentro del rango permitido.
         */
        usuario.calificarCancion(
                cancion,
                calificacion
        );
    }

    // Método para agregar una canción a la cola del usuario.
    public static void agregarCancionColaUsuario(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        Cancion cancion = buscarCancionPorNombre(aplicacion);

        if (cancion != null) {
            usuario.agregarCancionACola(cancion);
        }
    }

    // Método para escuchar la vista previa de una canción
    public static void vistaPreviaCancion(
            Aplicacion aplicacion) throws IOException {

        Cancion cancion =
                buscarCancionPorNombre(aplicacion);

        if (cancion == null) {
            return;
        }

        Reproductor reproductor =
                new Reproductor();

        reproductor.reproducirVistaPrevia(cancion);
    }

    // Método para cambiar la contraseña del usuario final.
    public static void cambiarContraseniaUsuario(
            UsuarioFinal usuario) throws IOException {

        System.out.println(
                "\n===== CAMBIO DE CONTRASEÑA =====");

        System.out.print("Contraseña actual: ");
        String contraseniaActual = entrada.readLine();

        System.out.print("Nueva contraseña: ");
        String nuevaContrasenia = entrada.readLine();

        System.out.print("Confirmar nueva contraseña: ");
        String confirmacion = entrada.readLine();

        usuario.cambiarContrasenia(
                contraseniaActual,
                nuevaContrasenia,
                confirmacion
        );
    }

    /*
     * Método auxiliar para leer números enteros.
     * Si el usuario escribe un valor inválido, vuelve a solicitarlo.
     */
    public static int leerEntero(String mensaje)
            throws IOException {

        boolean datoValido = false;
        int numero = 0;

        while (!datoValido) {

            try {

                System.out.print(mensaje);
                numero = Integer.parseInt(entrada.readLine());

                datoValido = true;

            } catch (NumberFormatException error) {

                System.out.println(
                        "Debe ingresar un número entero válido.");
            }
        }

        return numero;
    }

    /*
     * Método auxiliar para leer números decimales.
     * Si el usuario escribe un valor inválido, vuelve a solicitarlo.
     */
    public static float leerFloat(String mensaje)
            throws IOException {

        boolean datoValido = false;
        float numero = 0.0f;

        while (!datoValido) {

            try {

                System.out.print(mensaje);
                numero = Float.parseFloat(entrada.readLine());

                datoValido = true;

            } catch (NumberFormatException error) {

                System.out.println(
                        "Debe ingresar un número decimal válido.");
            }
        }

        return numero;
    }

    /*
     * Método auxiliar para leer fechas con el formato AAAA-MM-DD.
     * Si el formato no es correcto, vuelve a solicitar la fecha.
     */
    public static LocalDate leerFecha(String mensaje)
            throws IOException {

        boolean fechaValida = false;
        LocalDate fecha = null;

        while (!fechaValida) {

            try {

                System.out.print(mensaje);

                fecha = LocalDate.parse(
                        entrada.readLine());

                fechaValida = true;

            } catch (DateTimeParseException error) {

                System.out.println(
                        "La fecha debe utilizar el formato AAAA-MM-DD.");
            }
        }

        return fecha;
    }
}