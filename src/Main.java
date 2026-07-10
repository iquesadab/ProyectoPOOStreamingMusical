import java.time.LocalDate;
import java.time.Period;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // ---------- INSTANCIACIONES DE PRUEBA INICIALES ----------
        Administrador administrador = new Administrador("admin@streaming.com",
                "adminPrincipal", "Admin123!");

        Cancion cancion1 = new Cancion("Luz de Luna", "Pop",
                LocalDate.of(2020, 5, 15), 1.99f, 4.8f,
                "Sofía Marín", "Carlos Méndez", "Grandes Éxitos", "caratula_grandes_exitos.jpg");

        Cancion cancion2 = new Cancion("Camino Libre", "Rock",
                LocalDate.of(2021, 8, 10), 2.50f, 4.5f,
                "Grupo Horizonte", "Ana Castillo", "Grandes Éxitos", "caratula_grandes_exitos.jpg");

        administrador.registrarCancion(cancion1);
        administrador.registrarCancion(cancion2);
        administrador.agregarCancionACola(cancion1);
        administrador.agregarCancionACola(cancion2);

        UsuarioFinal usuario1 = new UsuarioFinal(
                "María Fernanda Rojas", LocalDate.of(2001, 3, 22),
                "Costarricense", "1-1234-5678", "avatar_maria.png",
                "maria@correo.com", "mariaR", "Maria123!",
                (byte) 10, (byte) 3);

        usuario1.comprarCancion(cancion1);
        usuario1.comprarCancion(cancion2);

        usuario1.crearListaReproduccion("Mis favoritas");
        ListaReproduccion lista1 = usuario1.getListasReproduccion()[0];

        usuario1.agregarCancionALista(cancion1, lista1);
        usuario1.agregarCancionALista(cancion2, lista1);
        lista1.calcularCalificacion();

        usuario1.agregarCancionACola(cancion1);
        usuario1.agregarCancionACola(cancion2);

        cancion1.reproducir();
        cancion2.reproducir();


        // ---------- FLUJO DE ENTRADA CON SCANNER (FORMULARIO) ----------
        Scanner scanner = new Scanner(System.in);
        SistemaAutenticacion sistemaAuth = new SistemaAutenticacion(10);

        System.out.println("\n---------- REGISTRO DE USUARIO ----------");

        // Llamadas estrictas a los métodos auxiliares corregidos
        String nombre = leerNombreValido(scanner);
        LocalDate fecha = leerFechaMayorEdad(scanner);
        String nacionalidad = leerNacionalidadValida(scanner); // <- CORREGIDO
        String cedula = leerCedulaValida(scanner);             // <- CORREGIDO

        System.out.print("Avatar (nombre de archivo): ");
        String avatar = scanner.nextLine();

        String correo = leerCorreoDisponible(scanner, sistemaAuth);
        String nombreUsuario = leerUsuarioDisponible(scanner, sistemaAuth);
        String contrasenia = leerContraseniaValida(scanner);

        // Envío seguro de datos al sistema
        sistemaAuth.registrarUsuario(nombre, fecha, nacionalidad, cedula,
                avatar, correo, nombreUsuario, contrasenia, (byte) 10, (byte) 3);


        System.out.println("\n---------- INICIO DE SESIÓN ----------");
        UsuarioFinal usuarioSesion = null;
        while (usuarioSesion == null) {
            System.out.print("Correo electrónico: ");
            String correoLogin = scanner.nextLine();

            System.out.print("Contraseña: ");
            String contraseniaLogin = scanner.nextLine();

            usuarioSesion = sistemaAuth.iniciarSesion(correoLogin, contraseniaLogin);

            if (usuarioSesion == null) {
                System.out.println(" Credenciales incorrectas. Intentá de nuevo.\n");
            }
        }


        System.out.println("\n---------- CAMBIO DE CONTRASEÑA ----------");
        boolean contrasenaCambiada = false;
        while (!contrasenaCambiada) {
            System.out.print("Contraseña actual: ");
            String actual = scanner.nextLine();

            System.out.print("Nueva contraseña: ");
            String nueva = scanner.nextLine();

            contrasenaCambiada = usuarioSesion.cambiarContrasenia(actual, nueva);

            if (!contrasenaCambiada) {
                System.out.println("Intentá de nuevo.\n");
            }
        }

        scanner.close();
        System.out.println("\n¡Pruebas finalizadas con éxito!");
    }


    // =========================================================================
    // MÉTODOS AUXILIARES CORREGIDOS CON VALIDACIÓN ESTRICTA
    // =========================================================================

    private static String leerNombreValido(Scanner scanner) {
        while (true) {
            System.out.print("Nombre completo: ");
            String nombre = scanner.nextLine();
            if (nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && !nombre.trim().isEmpty()) {
                return nombre;
            }
            System.out.println(" El nombre solo puede contener letras y espacios.");
        }
    }

    private static LocalDate leerFechaMayorEdad(Scanner scanner) {
        while (true) {
            System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
            try {
                LocalDate fecha = LocalDate.parse(scanner.nextLine());
                if (Period.between(fecha, LocalDate.now()).getYears() >= 18) {
                    return fecha;
                }
                System.out.println(" Debés ser mayor de edad para registrarte.");
            } catch (Exception e) {
                System.out.println(" Formato inválido. Usá el formato estricto YYYY-MM-DD.");
            }
        }
    }

    private static String leerNacionalidadValida(Scanner scanner) {
        while (true) {
            System.out.print("Nacionalidad: ");
            String nacionalidad = scanner.nextLine();
            // Valida estricto: Solo letras, acentos y espacios
            if (nacionalidad.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+") && !nacionalidad.trim().isEmpty()) {
                return nacionalidad;
            }
            System.out.println(" La nacionalidad solo puede contener letras. Intentá de nuevo.");
        }
    }

    private static String leerCedulaValida(Scanner scanner) {
        while (true) {
            System.out.print("Cédula: ");
            String cedula = scanner.nextLine();
            // Valida estricto: Solo números y guiones
            if (cedula.matches("[0-9-]+") && !cedula.trim().isEmpty()) {
                return cedula;
            }
            System.out.println(" La cédula solo puede contener números y guiones. Intentá de nuevo.");
        }
    }

    private static String leerCorreoDisponible(Scanner scanner, SistemaAutenticacion sistemaAuth) {
        while (true) {
            System.out.print("Correo electrónico: ");
            String correo = scanner.nextLine();
            if (!Usuario.esCorreoValido(correo)) {
                System.out.println(" El correo electrónico no tiene un formato válido.");
            } else if (sistemaAuth.correoExiste(correo)) {
                System.out.println(" Este correo electrónico ya está registrado en el sistema.");
            } else {
                return correo;
            }
        }
    }

    private static String leerUsuarioDisponible(Scanner scanner, SistemaAutenticacion sistemaAuth) {
        while (true) {
            System.out.print("Nombre de usuario: ");
            String usuario = scanner.nextLine();

            if (usuario.trim().isEmpty()) {
                System.out.println(" El nombre de usuario no puede estar vacío.");
            } else if (usuario.matches("\\d+")) {
                System.out.println(" El nombre de usuario no puede ser solo números.");
            } else if (!usuario.matches("[a-zA-Z0-9._-]+")) {
                System.out.println(" El usuario contiene caracteres inválidos. Usá letras, números, '.' o '_'.");
            } else if (sistemaAuth.nombreUsuarioExiste(usuario)) {
                System.out.println(" Este nombre de usuario ya está en uso.");
            } else {
                return usuario;
            }
        }
    }

    private static String leerContraseniaValida(Scanner scanner) {
        while (true) {
            System.out.print("Contraseña (mín. 8 caracteres, mayúscula, número y especial): ");
            String contrasenia = scanner.nextLine();
            if (Usuario.esContraseniaValida(contrasenia)) {
                return contrasenia;
            }
            System.out.println(" La contraseña no cumple los requisitos mínimos de seguridad.");
        }
    }
}