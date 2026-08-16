package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;
import cr.ac.ucenfotec.tl.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/* Interfaz de línea de comandos (CLI). Solo se comunica con Controller;
 no conoce Gestores, DAO ni la base de datos directamente. */
public class Menu {

    private final BufferedReader entrada =
            new BufferedReader(new InputStreamReader(System.in));

    private final Controller controller = new Controller();

    public void iniciar() throws IOException {

        asegurarAdministrador();
        menuPrincipal();
    }

    // =========================================================
    // ADMINISTRADOR OBLIGATORIO ANTES DE USAR EL SISTEMA
    // =========================================================

    private void asegurarAdministrador() throws IOException {

        while (true) {

            try {
                if (controller.existeAdministrador()) {
                    return;
                }
            } catch (Exception e) {
                System.out.println(
                        "Error verificando el administrador: " + e.getMessage()
                );
            }

            System.out.println(
                    "\nDebe registrar un administrador antes de utilizar la aplicación."
            );

            registrarAdministrador();
        }
    }

    private void registrarAdministrador() throws IOException {

        System.out.print("Correo electrónico: ");
        String correo = entrada.readLine();

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        System.out.print("Confirmar contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.registrarAdministrador(
                    correo, nombreUsuario, contrasenia, confirmar
            );

            System.out.println("Administrador registrado correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // MENÚ PRINCIPAL
    // =========================================================

    private void menuPrincipal() throws IOException {

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
            System.out.print("Seleccione una opción: ");

            String opcion = entrada.readLine();

            switch (opcion == null ? "" : opcion.trim()) {

                case "1":
                    iniciarSesionAdministrador();
                    break;

                case "2":
                    registrarUsuarioFinal();
                    break;

                case "3":
                    iniciarSesionUsuarioFinal();
                    break;

                case "4":
                    salir = true;
                    System.out.println("¡Hasta pronto!");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // =========================================================
    // AUTENTICACIÓN
    // =========================================================

    private void iniciarSesionAdministrador() throws IOException {

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        try {
            controller.iniciarSesionAdministrador(nombreUsuario, contrasenia);
            System.out.println("Sesión iniciada como administrador.");
            menuAdministrador();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarUsuarioFinal() throws IOException {

        System.out.print("Nombre completo: ");
        String nombreCompleto = entrada.readLine();

        LocalDate fechaNacimiento = leerFecha(
                "Fecha de nacimiento (YYYY-MM-DD): "
        );

        List<String> nacionalidades = controller.getNacionalidadesPermitidas();
        System.out.println("Nacionalidades disponibles: " + nacionalidades);
        System.out.print("Nacionalidad: ");
        String nacionalidad = entrada.readLine();

        System.out.print("Cédula: ");
        String cedula = entrada.readLine();

        System.out.print("Avatar (Enter para usar el predeterminado): ");
        String avatar = entrada.readLine();

        System.out.print("Correo electrónico: ");
        String correo = entrada.readLine();

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        System.out.print("Confirmar contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.registrarUsuario(
                    nombreCompleto,
                    fechaNacimiento,
                    nacionalidad,
                    cedula,
                    avatar,
                    correo,
                    nombreUsuario,
                    contrasenia,
                    confirmar
            );

            System.out.println(
                    "Usuario registrado correctamente. Bono inicial: $4.99"
            );

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void iniciarSesionUsuarioFinal() throws IOException {

        System.out.print("Nombre de usuario: ");
        String nombreUsuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        try {
            controller.iniciarSesionUsuarioFinal(nombreUsuario, contrasenia);
            System.out.println("Sesión iniciada correctamente.");
            menuUsuarioFinal();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private LocalDate leerFecha(String mensaje) throws IOException {

        while (true) {

            System.out.print(mensaje);
            String texto = entrada.readLine();

            try {
                return LocalDate.parse(texto.trim());

            } catch (DateTimeParseException e) {
                System.out.println(
                        "Formato inválido. Use el formato YYYY-MM-DD."
                );
            }
        }
    }

    // =========================================================
    // MENÚ ADMINISTRADOR
    // =========================================================

    private void menuAdministrador() throws IOException {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Ver catálogo de canciones");
            System.out.println("2. Registrar nueva canción");
            System.out.println("3. Buscar canciones");
            System.out.println("4. Ver Top 3");
            System.out.println("5. Cambiar contraseña");
            System.out.println("6. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            String opcion = entrada.readLine();

            switch (opcion == null ? "" : opcion.trim()) {

                case "1":
                    mostrarCatalogo();
                    break;

                case "2":
                    registrarCancion();
                    break;

                case "3":
                    buscarCanciones();
                    break;

                case "4":
                    mostrarTop3();
                    break;

                case "5":
                    cambiarContraseniaAdministrador();
                    break;

                case "6":
                    controller.cerrarSesion();
                    salir = true;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarCatalogo() throws IOException {

        try {
            controller.refrescarCatalogo();
            imprimirCanciones(controller.getCatalogoCompleto());

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarCancion() throws IOException {

        System.out.print("Nombre: ");
        String nombre = entrada.readLine();

        System.out.print("Género: ");
        String genero = entrada.readLine();

        LocalDate fechaLanzamiento = leerFecha(
                "Fecha de lanzamiento (YYYY-MM-DD): "
        );

        System.out.print("Precio: $");
        String textoPrecio = entrada.readLine();

        System.out.print("Artista: ");
        String artista = entrada.readLine();

        System.out.print("Compositor (Enter si es el mismo artista): ");
        String compositor = entrada.readLine();

        System.out.print("Álbum (Enter si no pertenece a ninguno): ");
        String nombreAlbum = entrada.readLine();

        System.out.print("Carátula (Enter para usar la predeterminada): ");
        String caratulaAlbum = entrada.readLine();

        try {
            float precio = Float.parseFloat(textoPrecio.trim());

            controller.registrarCancion(
                    nombre,
                    genero,
                    fechaLanzamiento,
                    precio,
                    artista,
                    compositor,
                    nombreAlbum,
                    caratulaAlbum
            );

            System.out.println("Canción registrada correctamente.");

        } catch (NumberFormatException e) {
            System.out.println("El precio no es válido.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarContraseniaAdministrador() throws IOException {

        System.out.print("Contraseña actual: ");
        String actual = entrada.readLine();

        System.out.print("Nueva contraseña: ");
        String nueva = entrada.readLine();

        System.out.print("Confirmar nueva contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.cambiarContraseniaAdministrador(actual, nueva, confirmar);
            System.out.println("Contraseña actualizada correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // MENÚ USUARIO FINAL
    // =========================================================

    private void menuUsuarioFinal() throws IOException {

        boolean salir = false;

        while (!salir) {

            System.out.println("\n--- MIS LISTAS DE REPRODUCCIÓN ---");
            mostrarListasDelUsuario();

            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Crear lista de reproducción");
            System.out.println("2. Buscar lista por nombre");
            System.out.println("3. Reproducir una lista");
            System.out.println("4. Agregar lista a la cola");
            System.out.println("5. Avanzar cola de reproducción");
            System.out.println("6. Ver Top 3");
            System.out.println("7. Recargar saldo");
            System.out.println("8. Cambiar contraseña");
            System.out.println("9. Buscar canciones en el catálogo");
            System.out.println("10. Comprar una canción");
            System.out.println("11. Calificar una canción");
            System.out.println("12. Escuchar vista previa (30s)");
            System.out.println("13. Agregar canción comprada a una lista");
            System.out.println("14. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            String opcion = entrada.readLine();

            switch (opcion == null ? "" : opcion.trim()) {

                case "1":
                    crearListaReproduccion();
                    break;

                case "2":
                    buscarListaPorNombre();
                    break;

                case "3":
                    reproducirLista();
                    break;

                case "4":
                    agregarListaACola();
                    break;

                case "5":
                    controller.avanzarCola();
                    System.out.println("Se avanzó la cola de reproducción.");
                    break;

                case "6":
                    mostrarTop3();
                    break;

                case "7":
                    recargarSaldo();
                    break;

                case "8":
                    cambiarContraseniaUsuarioFinal();
                    break;

                case "9":
                    buscarCanciones();
                    break;

                case "10":
                    comprarCancion();
                    break;

                case "11":
                    calificarCancion();
                    break;

                case "12":
                    reproducirVistaPrevia();
                    break;

                case "13":
                    agregarCancionAListaDesdeCompradas();
                    break;

                case "14":
                    controller.cerrarSesion();
                    salir = true;
                    System.out.println("Sesión cerrada.");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void mostrarListasDelUsuario() {

        ListaReproduccion[] listas = controller.listarListasDelUsuario();
        boolean hayListas = false;

        for (ListaReproduccion lista : listas) {

            if (lista == null) {
                continue;
            }

            hayListas = true;

            System.out.println(
                    "- " + lista.getNombre()
                            + " (creada: " + lista.getFechaCreacion()
                            + ", calificación: " + lista.getCalificacion() + ")"
            );
        }

        if (!hayListas) {
            System.out.println("Todavía no tiene listas de reproducción.");
        }
    }

    private void crearListaReproduccion() throws IOException {

        System.out.print("Nombre de la nueva lista: ");
        String nombre = entrada.readLine();

        try {
            controller.crearListaReproduccion(nombre);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarListaPorNombre() throws IOException {

        System.out.print("Nombre (o parte del nombre) a buscar: ");
        String nombre = entrada.readLine();

        ArrayList<ListaReproduccion> coincidencias =
                controller.buscarListasPorNombre(nombre);

        if (coincidencias.isEmpty()) {
            System.out.println("No se encontraron listas con ese nombre.");
            return;
        }

        for (ListaReproduccion lista : coincidencias) {
            System.out.println("- " + lista.getNombre());
        }
    }

    private ListaReproduccion seleccionarListaPorNombre() throws IOException {

        System.out.print("Nombre exacto de la lista: ");
        String nombre = entrada.readLine();

        ListaReproduccion[] listas = controller.listarListasDelUsuario();

        for (ListaReproduccion lista : listas) {

            if (lista != null &&
                    lista.getNombre() != null &&
                    lista.getNombre().equalsIgnoreCase(
                            nombre == null ? "" : nombre.trim()
                    )) {

                return lista;
            }
        }

        System.out.println("No se encontró una lista con ese nombre.");
        return null;
    }

    private void reproducirLista() throws IOException {

        ListaReproduccion lista = seleccionarListaPorNombre();

        if (lista == null) {
            return;
        }

        try {
            controller.reproducirLista(lista);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarListaACola() throws IOException {

        ListaReproduccion lista = seleccionarListaPorNombre();

        if (lista == null) {
            return;
        }

        try {
            controller.agregarListaACola(lista);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void recargarSaldo() throws IOException {

        System.out.print("Monto a recargar: $");
        String texto = entrada.readLine();

        try {
            float monto = Float.parseFloat(texto.trim());
            controller.recargarSaldo(monto);

        } catch (NumberFormatException e) {
            System.out.println("Monto inválido.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void cambiarContraseniaUsuarioFinal() throws IOException {

        System.out.print("Contraseña actual: ");
        String actual = entrada.readLine();

        System.out.print("Nueva contraseña: ");
        String nueva = entrada.readLine();

        System.out.print("Confirmar nueva contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.cambiarContraseniaUsuarioFinal(actual, nueva, confirmar);
            System.out.println("Contraseña actualizada correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarCanciones() throws IOException {

        System.out.println("Buscar por: 1) Nombre  2) Género  3) Artista");
        System.out.print("Opción: ");
        String tipo = entrada.readLine();

        System.out.print("Texto a buscar: ");
        String texto = entrada.readLine();

        try {
            ArrayList<Cancion> resultados;

            switch (tipo == null ? "" : tipo.trim()) {

                case "1":
                    resultados = controller.buscarCancionesPorNombre(texto);
                    break;

                case "2":
                    resultados = controller.buscarCancionesPorGenero(texto);
                    break;

                case "3":
                    resultados = controller.buscarCancionesPorArtista(texto);
                    break;

                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            imprimirCanciones(resultados);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void imprimirCanciones(ArrayList<Cancion> canciones) {

        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No se encontraron canciones.");
            return;
        }

        for (Cancion cancion : canciones) {
            System.out.println(
                    "- " + cancion.getNombre()
                            + " | " + cancion.getArtista()
                            + " | " + cancion.getGenero()
                            + " | $" + cancion.getPrecio()
                            + " | calificación: " + cancion.getCalificacion()
            );
        }
    }

    /* Selecciona una canción del catálogo completo por nombre exacto
    (suficiente para esta CLI; el catálogo se busca antes con
    buscarCanciones() si el nombre exacto no se conoce).*/
    private Cancion seleccionarCancionPorNombre() throws IOException {

        System.out.print("Nombre exacto de la canción: ");
        String nombre = entrada.readLine();

        try {
            ArrayList<Cancion> resultados =
                    controller.buscarCancionesPorNombre(
                            nombre == null ? "" : nombre.trim()
                    );

            for (Cancion cancion : resultados) {
                if (cancion.getNombre().equalsIgnoreCase(
                        nombre == null ? "" : nombre.trim())) {

                    return cancion;
                }
            }

            System.out.println("No se encontró una canción con ese nombre.");
            return null;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    private void comprarCancion() throws IOException {

        Cancion cancion = seleccionarCancionPorNombre();

        if (cancion == null) {
            return;
        }

        try {
            controller.comprarCancion(cancion);
            System.out.println("Canción comprada correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void calificarCancion() throws IOException {

        Cancion cancion = seleccionarCancionPorNombre();

        if (cancion == null) {
            return;
        }

        System.out.print("Calificación (0.0 a 5.0): ");
        String texto = entrada.readLine();

        try {
            float calificacion = Float.parseFloat(texto.trim());
            float promedio = controller.calificarCancion(cancion, calificacion);

            System.out.println(
                    "Canción calificada. Nueva calificación promedio: " + promedio
            );

        } catch (NumberFormatException e) {
            System.out.println("La calificación no es válida.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void reproducirVistaPrevia() throws IOException {

        Cancion cancion = seleccionarCancionPorNombre();

        if (cancion == null) {
            return;
        }

        controller.reproducirVistaPrevia(cancion);
    }

    private void agregarCancionAListaDesdeCompradas() throws IOException {

        Cancion cancion = seleccionarCancionPorNombre();

        if (cancion == null) {
            return;
        }

        ListaReproduccion lista = seleccionarListaPorNombre();

        if (lista == null) {
            return;
        }

        try {
            controller.agregarCancionALista(lista, cancion);
            System.out.println("Canción agregada a la lista correctamente.");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // TOP 3 (compartido entre administrador y usuario final)
    // =========================================================

    private void mostrarTop3() {

        ArrayList<Cancion> mejorCalificadas =
                controller.obtenerTop3MejorCalificadas();

        ArrayList<Cancion> masCompradas =
                controller.obtenerTop3MasCompradas();

        ArrayList<Cancion> masAgregadas =
                controller.obtenerTop3MasAgregadasAListas();

        if (mejorCalificadas.isEmpty()
                && masCompradas.isEmpty()
                && masAgregadas.isEmpty()) {

            System.out.println(
                    "El catálogo todavía está vacío " +
                            "(pendiente de que se carguen canciones)."
            );
            return;
        }

        System.out.println("\nTop 3 mejor calificadas:");
        imprimirTop(mejorCalificadas);

        System.out.println("\nTop 3 más compradas:");
        imprimirTop(masCompradas);

        System.out.println("\nTop 3 más agregadas a listas:");
        imprimirTop(masAgregadas);
    }

    private void imprimirTop(ArrayList<Cancion> canciones) {

        if (canciones.isEmpty()) {
            System.out.println("  (sin datos todavía)");
            return;
        }

        int posicion = 1;

        for (Cancion cancion : canciones) {
            System.out.println(
                    "  " + posicion + ". " + cancion.getNombre()
                            + " - " + cancion.getArtista()
            );

            posicion++;
        }
    }
}