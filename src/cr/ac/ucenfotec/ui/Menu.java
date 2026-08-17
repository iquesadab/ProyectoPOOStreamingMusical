package cr.ac.ucenfotec.ui;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;
import cr.ac.ucenfotec.tl.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class Menu {

    // Entrada de datos de la interfaz CLI.
    private final BufferedReader entrada =
            new BufferedReader(new InputStreamReader(System.in));

    // El Menu solo se comunica con el Controller.
    private final Controller controller = new Controller();

    // Inicia la aplicación y valida primero la existencia del administrador.
    public void iniciar() throws IOException {
        asegurarAdministrador();
        menuPrincipal();
    }

    // =========================================================
    // REGISTRO OBLIGATORIO DEL ADMINISTRADOR
    // =========================================================

    private void asegurarAdministrador() throws IOException {
        try {
            while (!controller.existeAdministrador()) {
                System.out.println("\nDebe registrar un administrador antes de usar el sistema.");
                registrarAdministrador();
            }
        } catch (Exception e) {
            System.out.println("Error al verificar el administrador: " + e.getMessage());
        }
    }

    private void registrarAdministrador() throws IOException {
        System.out.println("\n--- REGISTRO DEL ADMINISTRADOR ---");

        System.out.print("Correo electrónico: ");
        String correo = entrada.readLine();

        System.out.print("Nombre de usuario: ");
        String usuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        System.out.print("Confirmar contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.registrarAdministrador(correo, usuario, contrasenia, confirmar);
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
            System.out.println("\n========================================");
            System.out.println("       STREAMING MUSICAL");
            System.out.println("========================================");
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
        String usuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        try {
            controller.iniciarSesionAdministrador(usuario, contrasenia);
            System.out.println("Sesión iniciada como administrador.");
            menuAdministrador();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void registrarUsuarioFinal() throws IOException {
        System.out.println("\n--- REGISTRO DE USUARIO ---");

        System.out.print("Nombre completo: ");
        String nombreCompleto = entrada.readLine();

        LocalDate fechaNacimiento = leerFecha("Fecha de nacimiento (YYYY-MM-DD): ");

        List<String> nacionalidades = controller.getNacionalidadesPermitidas();
        System.out.println("Nacionalidades disponibles: " + nacionalidades);
        System.out.print("Nacionalidad: ");
        String nacionalidad = entrada.readLine();

        System.out.print("Cédula: ");
        String cedula = entrada.readLine();

        System.out.print("Avatar (Enter para predeterminado): ");
        String avatar = entrada.readLine();

        System.out.print("Correo electrónico: ");
        String correo = entrada.readLine();

        System.out.print("Nombre de usuario: ");
        String usuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        System.out.print("Confirmar contraseña: ");
        String confirmar = entrada.readLine();

        try {
            controller.registrarUsuario(
                    nombreCompleto, fechaNacimiento, nacionalidad, cedula,
                    avatar, correo, usuario, contrasenia, confirmar
            );
            System.out.println("Usuario registrado correctamente. Bono inicial: $4.99");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void iniciarSesionUsuarioFinal() throws IOException {
        System.out.print("Nombre de usuario: ");
        String usuario = entrada.readLine();

        System.out.print("Contraseña: ");
        String contrasenia = entrada.readLine();

        try {
            controller.iniciarSesionUsuarioFinal(usuario, contrasenia);
            System.out.println("Sesión iniciada correctamente.");
            menuUsuarioFinal();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // MENÚ ADMINISTRADOR
    // =========================================================

    private void menuAdministrador() throws IOException {
        boolean cerrarSesion = false;

        while (!cerrarSesion) {
            System.out.println("\n--- MENÚ ADMINISTRADOR ---");
            System.out.println("1. Ver catálogo");
            System.out.println("2. Registrar canción");
            System.out.println("3. Buscar canciones");
            System.out.println("4. Reproducir canción");
            System.out.println("5. Agregar canción a la cola");
            System.out.println("6. Ver todas las listas de reproducción");
            System.out.println("7. Reproducir una lista");
            System.out.println("8. Agregar una lista a la cola");
            System.out.println("9. Mostrar cola");
            System.out.println("10. Reproducir siguiente de la cola");
            System.out.println("11. Ver Top 3");
            System.out.println("12. Cambiar contraseña");
            System.out.println("13. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            String opcion = entrada.readLine();

            switch (opcion == null ? "" : opcion.trim()) {
                case "1": mostrarCatalogo(); break;
                case "2": registrarCancion(); break;
                case "3": buscarCanciones(); break;
                case "4": reproducirCancionCompleta(); break;
                case "5": agregarCancionACola(); break;
                case "6": mostrarTodasLasListas(); break;
                case "7": reproducirListaAdministrador(); break;
                case "8": agregarListaAColaAdministrador(); break;
                case "9": mostrarCola(); break;
                case "10": controller.avanzarCola(); break;
                case "11": mostrarTop3(); break;
                case "12": cambiarContraseniaAdministrador(); break;
                case "13":
                    controller.cerrarSesion();
                    cerrarSesion = true;
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    private void registrarCancion() throws IOException {
        System.out.print("Nombre: ");
        String nombre = entrada.readLine();

        System.out.print("Género: ");
        String genero = entrada.readLine();

        LocalDate fecha = leerFecha("Fecha de lanzamiento (YYYY-MM-DD): ");

        System.out.print("Precio: $");
        String textoPrecio = entrada.readLine();

        System.out.print("Artista: ");
        String artista = entrada.readLine();

        System.out.print("Compositor (Enter si es el mismo artista): ");
        String compositor = entrada.readLine();
        if (compositor == null || compositor.isBlank()) compositor = artista;

        System.out.print("Álbum (Enter si no pertenece a ninguno): ");
        String album = entrada.readLine();

        System.out.print("Carátula (Enter para predeterminada): ");
        String caratula = entrada.readLine();

        try {
            float precio = Float.parseFloat(textoPrecio.trim());
            controller.registrarCancion(
                    nombre, genero, fecha, precio, artista,
                    compositor, album, caratula
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

    private void mostrarTodasLasListas() {
        try {
            ArrayList<ListaReproduccion> listas = controller.listarTodasLasListas();
            imprimirListas(listas);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private ListaReproduccion seleccionarListaAdministrador() throws IOException {
        try {
            ArrayList<ListaReproduccion> listas = controller.listarTodasLasListas();
            imprimirListas(listas);

            if (listas.isEmpty()) return null;

            System.out.print("Id de la lista: ");
            int id = Integer.parseInt(entrada.readLine().trim());

            for (ListaReproduccion lista : listas) {
                if (lista.getId() == id) return lista;
            }

            System.out.println("No se encontró la lista.");
        } catch (NumberFormatException e) {
            System.out.println("Id inválido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

    private void reproducirListaAdministrador() throws IOException {
        ListaReproduccion lista = seleccionarListaAdministrador();
        if (lista == null) return;

        try {
            controller.reproducirLista(lista);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarListaAColaAdministrador() throws IOException {
        ListaReproduccion lista = seleccionarListaAdministrador();
        if (lista == null) return;

        try {
            controller.agregarListaACola(lista);
            System.out.println("Lista agregada a la cola.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // =========================================================
    // MENÚ USUARIO FINAL
    // =========================================================

    private void menuUsuarioFinal() throws IOException {
        boolean cerrarSesion = false;

        while (!cerrarSesion) {
            UsuarioFinal usuario = controller.getUsuarioFinalActual();

            System.out.println("\n--- USUARIO FINAL ---");
            if (usuario != null) {
                System.out.println("Saldo actual: $" + usuario.getSaldo());
            }
            mostrarListasDelUsuario();

            System.out.println("\n1. Ver catálogo");
            System.out.println("2. Buscar canciones");
            System.out.println("3. Ver canciones compradas");
            System.out.println("4. Comprar canción");
            System.out.println("5. Calificar canción");
            System.out.println("6. Vista previa de 30 segundos");
            System.out.println("7. Reproducir canción comprada");
            System.out.println("8. Agregar canción a la cola");
            System.out.println("9. Crear lista de reproducción");
            System.out.println("10. Buscar lista por nombre");
            System.out.println("11. Agregar canción a una lista");
            System.out.println("12. Eliminar canción de una lista");
            System.out.println("13. Reproducir lista");
            System.out.println("14. Agregar lista a la cola");
            System.out.println("15. Mostrar cola");
            System.out.println("16. Reproducir siguiente de la cola");
            System.out.println("17. Ver Top 3");
            System.out.println("18. Recargar saldo");
            System.out.println("19. Cambiar contraseña");
            System.out.println("20. Cerrar sesión");
            System.out.print("Seleccione una opción: ");

            String opcion = entrada.readLine();

            switch (opcion == null ? "" : opcion.trim()) {
                case "1": mostrarCatalogo(); break;
                case "2": buscarCanciones(); break;
                case "3": mostrarCancionesCompradas(); break;
                case "4": comprarCancion(); break;
                case "5": calificarCancion(); break;
                case "6": reproducirVistaPrevia(); break;
                case "7": reproducirCancionCompleta(); break;
                case "8": agregarCancionACola(); break;
                case "9": crearListaReproduccion(); break;
                case "10": buscarListaPorNombre(); break;
                case "11": agregarCancionALista(); break;
                case "12": eliminarCancionDeLista(); break;
                case "13": reproducirListaUsuario(); break;
                case "14": agregarListaAColaUsuario(); break;
                case "15": mostrarCola(); break;
                case "16": controller.avanzarCola(); break;
                case "17": mostrarTop3(); break;
                case "18": recargarSaldo(); break;
                case "19": cambiarContraseniaUsuarioFinal(); break;
                case "20":
                    controller.cerrarSesion();
                    cerrarSesion = true;
                    System.out.println("Sesión cerrada.");
                    break;
                default:
                    System.out.println("Opción inválida.");
            }
        }
    }

    // =========================================================
    // CATÁLOGO Y CANCIONES
    // =========================================================

    private void mostrarCatalogo() {
        try {
            controller.refrescarCatalogo();
            imprimirCanciones(controller.getCatalogoCompleto());
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
                case "1": resultados = controller.buscarCancionesPorNombre(texto); break;
                case "2": resultados = controller.buscarCancionesPorGenero(texto); break;
                case "3": resultados = controller.buscarCancionesPorArtista(texto); break;
                default:
                    System.out.println("Opción inválida.");
                    return;
            }

            imprimirCanciones(resultados);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Cancion seleccionarCancionPorNombre() throws IOException {
        System.out.print("Nombre exacto de la canción: ");
        String nombre = entrada.readLine();

        try {
            ArrayList<Cancion> resultados =
                    controller.buscarCancionesPorNombre(nombre == null ? "" : nombre.trim());

            for (Cancion cancion : resultados) {
                if (cancion.getNombre().equalsIgnoreCase(nombre.trim())) {
                    return cancion;
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

        System.out.println("No se encontró una canción con ese nombre.");
        return null;
    }

    private void comprarCancion() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        try {
            controller.comprarCancion(cancion);
            System.out.println("Canción comprada correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void calificarCancion() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        System.out.print("Calificación (0.0 a 5.0): ");
        try {
            float calificacion = Float.parseFloat(entrada.readLine().trim());
            float promedio = controller.calificarCancion(cancion, calificacion);
            System.out.println("Nueva calificación promedio: " + promedio);
        } catch (NumberFormatException e) {
            System.out.println("Calificación inválida.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void reproducirVistaPrevia() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion != null) controller.reproducirVistaPrevia(cancion);
    }

    private void reproducirCancionCompleta() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        try {
            controller.reproducirCancion(cancion);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarCancionACola() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        try {
            // Para usuario final, el propio modelo valida que esté comprada.
            // El administrador puede agregar cualquier canción.
            if (controller.getUsuarioFinalActual() != null &&
                    !controller.getUsuarioFinalActual().tieneCancionComprada(cancion)) {
                System.out.println("Debe comprar la canción antes de agregarla a la cola.");
                return;
            }

            controller.agregarCancionACola(cancion);
            System.out.println("Canción agregada a la cola.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void mostrarCancionesCompradas() {
        UsuarioFinal usuario = controller.getUsuarioFinalActual();
        if (usuario == null || usuario.getCantidadCancionesCompradas() == 0) {
            System.out.println("No hay canciones compradas.");
            return;
        }

        System.out.println("\n--- CANCIONES COMPRADAS ---");
        for (int i = 0; i < usuario.getCantidadCancionesCompradas(); i++) {
            Cancion cancion = usuario.getCancionesCompradas()[i];
            System.out.println("- " + cancion.getNombre() + " - " + cancion.getArtista());
        }
    }

    private void imprimirCanciones(ArrayList<Cancion> canciones) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No se encontraron canciones.");
            return;
        }

        for (Cancion cancion : canciones) {
            System.out.println(
                    "- " + cancion.getNombre() +
                            " | " + cancion.getArtista() +
                            " | " + cancion.getGenero() +
                            " | $" + cancion.getPrecio() +
                            " | Calificación: " + cancion.getCalificacion()
            );
        }
    }

    // =========================================================
    // LISTAS DE REPRODUCCIÓN
    // =========================================================

    private void mostrarListasDelUsuario() {
        ListaReproduccion[] listas = controller.listarListasDelUsuario();
        boolean hayListas = false;

        System.out.println("\n--- MIS LISTAS DE REPRODUCCIÓN ---");
        for (ListaReproduccion lista : listas) {
            if (lista != null) {
                hayListas = true;
                System.out.println(
                        "- " + lista.getNombre() +
                                " | Fecha: " + lista.getFechaCreacion() +
                                " | Calificación: " + lista.getCalificacion()
                );
            }
        }

        if (!hayListas) System.out.println("No hay listas creadas.");
    }

    private void crearListaReproduccion() throws IOException {
        System.out.print("Nombre de la nueva lista: ");
        String nombre = entrada.readLine();

        try {
            controller.crearListaReproduccion(nombre);
            System.out.println("Lista creada correctamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void buscarListaPorNombre() throws IOException {
        System.out.print("Nombre o parte del nombre: ");
        String nombre = entrada.readLine();

        ArrayList<ListaReproduccion> listas = controller.buscarListasPorNombre(nombre);
        imprimirListas(listas);
    }

    private ListaReproduccion seleccionarListaUsuario() throws IOException {
        System.out.print("Nombre exacto de la lista: ");
        String nombre = entrada.readLine();

        for (ListaReproduccion lista : controller.listarListasDelUsuario()) {
            if (lista != null && lista.getNombre().equalsIgnoreCase(nombre.trim())) {
                return lista;
            }
        }

        System.out.println("No se encontró la lista.");
        return null;
    }

    private void agregarCancionALista() throws IOException {
        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        ListaReproduccion lista = seleccionarListaUsuario();
        if (lista == null) return;

        try {
            controller.agregarCancionALista(lista, cancion);
            System.out.println("Canción agregada a la lista.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void eliminarCancionDeLista() throws IOException {
        ListaReproduccion lista = seleccionarListaUsuario();
        if (lista == null) return;

        if (lista.getCanciones().isEmpty()) {
            System.out.println("La lista no contiene canciones.");
            return;
        }

        System.out.println("Canciones de la lista:");
        for (Cancion cancion : lista.getCanciones()) {
            System.out.println("- " + cancion.getNombre());
        }

        Cancion cancion = seleccionarCancionPorNombre();
        if (cancion == null) return;

        try {
            controller.eliminarCancionDeLista(lista, cancion);
            System.out.println("Operación finalizada.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void reproducirListaUsuario() throws IOException {
        ListaReproduccion lista = seleccionarListaUsuario();
        if (lista == null) return;

        try {
            controller.reproducirLista(lista);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void agregarListaAColaUsuario() throws IOException {
        ListaReproduccion lista = seleccionarListaUsuario();
        if (lista == null) return;

        try {
            controller.agregarListaACola(lista);
            System.out.println("Lista agregada a la cola.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void imprimirListas(ArrayList<ListaReproduccion> listas) {
        if (listas == null || listas.isEmpty()) {
            System.out.println("No hay listas de reproducción.");
            return;
        }

        for (ListaReproduccion lista : listas) {
            System.out.println(
                    "- Id: " + lista.getId() +
                            " | " + lista.getNombre() +
                            " | Fecha: " + lista.getFechaCreacion() +
                            " | Calificación: " + lista.getCalificacion()
            );
        }
    }

    // =========================================================
    // COLA, TOP 3, SALDO Y CONTRASEÑA
    // =========================================================

    private void mostrarCola() {
        if (controller.getUsuarioActual() == null) {
            System.out.println("No hay una sesión activa.");
            return;
        }
        controller.getUsuarioActual().getColaReproduccion().mostrarCola();
    }

    private void mostrarTop3() {
        try {
            // Se refresca antes de consultar para obtener estadísticas actuales.
            controller.refrescarCatalogo();

            System.out.println("\nTop 3 mejor calificadas:");
            imprimirTop(controller.obtenerTop3MejorCalificadas());

            System.out.println("\nTop 3 más compradas:");
            imprimirTop(controller.obtenerTop3MasCompradas());

            System.out.println("\nTop 3 más agregadas a listas:");
            imprimirTop(controller.obtenerTop3MasAgregadasAListas());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void imprimirTop(ArrayList<Cancion> canciones) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("(sin datos)");
            return;
        }

        int posicion = 1;
        for (Cancion cancion : canciones) {
            System.out.println(
                    posicion + ". " + cancion.getNombre() +
                            " - " + cancion.getArtista() +
                            " | Calificación: " + cancion.getCalificacion() +
                            " | Compras: " + cancion.getVecesComprada() +
                            " | En listas: " + cancion.getVecesAgregadaAListas()
            );
            posicion++;
        }
    }

    private void recargarSaldo() throws IOException {
        System.out.print("Monto a recargar: $");

        try {
            float monto = Float.parseFloat(entrada.readLine().trim());
            controller.recargarSaldo(monto);
            System.out.println("Saldo actualizado: $" + controller.getUsuarioFinalActual().getSaldo());
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

    // Lee fechas hasta que el usuario ingrese el formato correcto.
    private LocalDate leerFecha(String mensaje) throws IOException {
        while (true) {
            System.out.print(mensaje);
            String texto = entrada.readLine();

            try {
                return LocalDate.parse(texto.trim());
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido. Use YYYY-MM-DD.");
            }
        }
    }
}