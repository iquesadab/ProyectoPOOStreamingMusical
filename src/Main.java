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
             * Aplicacion se encarga de validar los datos y crear
             * el objeto Administrador.
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
            System.out.println("4. Mostrar usuarios registrados");
            System.out.println("5. Agregar canción a la cola");
            System.out.println("6. Mostrar cola de reproducción");
            System.out.println("7. Reproducir siguiente canción");
            System.out.println("8. Cambiar contraseña");
            System.out.println("9. Cerrar sesión");
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
                    aplicacion.mostrarUsuariosFinales();
                    break;

                case 5:
                    agregarCancionColaAdministrador(aplicacion);
                    break;

                case 6:
                    System.out.println(
                            "\n===== COLA DEL ADMINISTRADOR =====");

                    System.out.println(
                            aplicacion.getAdministrador()
                                    .getColaReproduccion());
                    break;

                case 7:
                    aplicacion.getAdministrador()
                            .getColaReproduccion()
                            .reproducirSiguiente();
                    break;

                case 8:
                    cambiarContraseniaAdministrador(aplicacion);
                    break;

                case 9:
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

        // Aplicacion valida y agrega la canción al catálogo.
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

    // Método para agregar una canción a la cola del administrador.
    public static void agregarCancionColaAdministrador(
            Aplicacion aplicacion) throws IOException {

        Cancion cancion = buscarCancionPorNombre(aplicacion);

        if (cancion != null) {

            aplicacion.getAdministrador()
                    .agregarCancionACola(cancion);
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
         * Este método debe existir en la clase Usuario o ser heredado
         * por Administrador. Si todavía no está implementado, se
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
         * UsuarioFinal todavía utiliza arreglos de tamaño fijo.
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
            System.out.println("4. Comprar canción");
            System.out.println("5. Recargar saldo");
            System.out.println("6. Crear lista de reproducción");
            System.out.println("7. Agregar canción a la cola");
            System.out.println("8. Mostrar cola de reproducción");
            System.out.println("9. Reproducir siguiente canción");
            System.out.println("10. Cambiar contraseña");
            System.out.println("11. Cerrar sesión");
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
                    comprarCancion(aplicacion, usuario);
                    break;

                case 5:
                    recargarSaldo(usuario);
                    break;

                case 6:
                    crearListaReproduccion(usuario);
                    break;

                case 7:
                    agregarCancionColaUsuario(
                            aplicacion,
                            usuario
                    );
                    break;

                case 8:
                    System.out.println(
                            "\n===== MI COLA DE REPRODUCCIÓN =====");

                    System.out.println(
                            usuario.getColaReproduccion());
                    break;

                case 9:
                    usuario.getColaReproduccion()
                            .reproducirSiguiente();
                    break;

                case 10:
                    cambiarContraseniaUsuario(usuario);
                    break;

                case 11:
                    cerrarSesion = true;

                    System.out.println(
                            "\nSesión del usuario cerrada.");
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

    // Método para agregar una canción a la cola del usuario.
    public static void agregarCancionColaUsuario(
            Aplicacion aplicacion,
            UsuarioFinal usuario) throws IOException {

        Cancion cancion = buscarCancionPorNombre(aplicacion);

        if (cancion != null) {
            usuario.agregarCancionACola(cancion);
        }
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