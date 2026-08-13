package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;

import java.time.LocalDate;
import java.util.ArrayList;

public class Aplicacion {

    // Atributos
    private Administrador administrador;
    private ArrayList<UsuarioFinal> usuariosFinales;
    private ArrayList<Cancion> catalogoCanciones;

    // Clase encargada de las validaciones y la autenticación
    private SistemaAutenticacion sistemaAutenticacion;

    // Lista predeterminada de nacionalidades disponibles
    private ArrayList<String> nacionalidadesPermitidas;

    private TopCanciones topCanciones;

    // Constructor
    public Aplicacion() {
        // Al iniciar la aplicación todavía no existe un administrador.
        this.administrador = null;

        // Se crean las colecciones principales de la aplicación.
        this.usuariosFinales = new ArrayList<>();
        this.catalogoCanciones = new ArrayList<>();

        // Se crea el sistema encargado de la autenticación.
        this.sistemaAutenticacion = new SistemaAutenticacion();

        // Se crea la lista de nacionalidades disponibles.
        this.nacionalidadesPermitidas = new ArrayList<>();

        // Se agregan las nacionalidades permitidas en la aplicación.
        cargarNacionalidadesPermitidas();

        // Se crea la lista de top canciones
        this.topCanciones = new TopCanciones();
    }

    // Getters
    public Administrador getAdministrador() {
        return administrador;
    }

    public ArrayList<UsuarioFinal> getUsuariosFinales() {
        return usuariosFinales;
    }

    public ArrayList<Cancion> getCatalogoCanciones() {
        return catalogoCanciones;
    }

    public SistemaAutenticacion getSistemaAutenticacion() { return sistemaAutenticacion; }

    public ArrayList<String> getNacionalidadesPermitidas() { return nacionalidadesPermitidas; }

    public TopCanciones getTopCanciones() { return topCanciones; }

    // Métodos principales

    // Método para cargar las nacionalidades disponibles
    private void cargarNacionalidadesPermitidas() {

        nacionalidadesPermitidas.add("Costarricense");
        nacionalidadesPermitidas.add("Panameña");
        nacionalidadesPermitidas.add("Nicaragüense");
        nacionalidadesPermitidas.add("Salvadoreña");
        nacionalidadesPermitidas.add("Guatemalteca");
        nacionalidadesPermitidas.add("Hondureña");
        nacionalidadesPermitidas.add("Mexicana");
        nacionalidadesPermitidas.add("Colombiana");
    }

    // Método para verificar si ya existe un administrador
    public boolean hayAdministrador() {
        if (administrador != null) {
            return true;
        } else {
            return false;
        }
    }

    // Método para registrar obligatoriamente al administrador
    public boolean registrarAdministrador(String correoElectronico, String nombreUsuario, String contrasenia, String confirmarContrasenia) {

        // Verifica que no exista otro administrador registrado.
        if (hayAdministrador()) {
            System.out.println("Ya existe un administrador registrado.");
            return false;
        }

        // Verifica que el correo tenga un formato válido.
        if (!Usuario.esCorreoValido(correoElectronico)) {
            System.out.println(
                    "El correo electrónico no tiene un formato válido.");
            return false;
        }

        // Verifica que el nombre de usuario no esté vacío.
        if (nombreUsuario == null
                || nombreUsuario.trim().isEmpty()) {

            System.out.println(
                    "El nombre de usuario no puede estar vacío.");
            return false;
        }

        // Verifica que la contraseña cumpla los requisitos.
        if (!Usuario.esContraseniaValida(contrasenia)) {

            System.out.println(
                    "La contraseña no cumple los requisitos:" +
                            "\n- Debe tener entre 8 y 12 caracteres." +
                            "\n- Debe incluir al menos una letra mayúscula." +
                            "\n- Debe incluir al menos una letra minúscula." +
                            "\n- Debe incluir al menos un número." +
                            "\n- Debe incluir al menos un carácter especial.");

            return false;
        }

        // Verifica que ambas contraseñas coincidan.
        if (!sistemaAutenticacion.confirmarContrasenia(
                contrasenia, confirmarContrasenia)) {

            System.out.println("Las contraseñas no coinciden.");
            return false;
        }

        // Crea el administrador después de completar las validaciones.
        administrador = new Administrador(
                correoElectronico,
                nombreUsuario,
                contrasenia
        );

        System.out.println("Administrador registrado correctamente.");
        return true;
    }

    // Método para registrar un usuario final
    public UsuarioFinal registrarUsuarioFinal(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia,
            byte cantidadMaximaCanciones,
            byte cantidadMaximaListas) {

        // cr.ac.ucenfotec.bl.SistemaAutenticacion valida, crea y agrega el usuario
        // al ArrayList principal de la aplicación.
        return sistemaAutenticacion.registrarUsuario(
                usuariosFinales,
                nacionalidadesPermitidas,
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
    }

    // Método para iniciar sesión como administrador
    public boolean iniciarSesionAdministrador(String nombreUsuario, String contrasenia) {

        // Verifica que exista un administrador.
        if (administrador == null) {
            return false;
        }

        // Verifica el nombre de usuario.
        boolean usuarioCorrecto =
                administrador.getNombreUsuario().equalsIgnoreCase(nombreUsuario);;

        // Verifica la contraseña.
        boolean contraseniaCorrecta =
                administrador.getContrasenia()
                        .equals(contrasenia);

        // Devuelve true solamente si ambas credenciales coinciden.
        if (usuarioCorrecto && contraseniaCorrecta) {
            return true;
        }

        return false;
    }

    // Método para iniciar sesión como usuario final
    public UsuarioFinal iniciarSesionUsuarioFinal(String nombreUsuario, String contrasenia) {

        return sistemaAutenticacion.iniciarSesion(
                usuariosFinales,
                nombreUsuario,
                contrasenia
        );
    }

    // Método para registrar una canción en el catálogo
    public boolean registrarCancion(Cancion cancion) {

        // Verifica que la canción recibida exista.
        if (cancion == null) {
            System.out.println("No se puede registrar una canción vacía.");
            return false;
        }

        // Verifica que no exista una canción con el mismo nombre.
        if (buscarCancionPorNombre(cancion.getNombre()) != null) {
            System.out.println(
                    "Ya existe una canción con ese nombre en el catálogo.");
            return false;
        }

        // Agrega la canción al catálogo principal.
        catalogoCanciones.add(cancion);

        System.out.println(
                "Canción agregada al catálogo correctamente.");

        return true;
    }

    // Metodo para buscar un usuario por su nombre de usuario
    public UsuarioFinal buscarUsuarioPorNombreUsuario(String nombreUsuario) {

        // Recorre todos los usuarios registrados.
        for (int i = 0; i < usuariosFinales.size(); i++) {

            // Obtiene el usuario ubicado en la posición actual.
            UsuarioFinal usuario = usuariosFinales.get(i);

            // Verifica si el nombre coincide.
            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        // Devuelve null si no encuentra el usuario.
        return null;
    }

    // Metodo para buscar una canción por su nombre
    public Cancion buscarCancionPorNombre(String nombre) {

        // Verifica que el nombre recibido sea válido.
        if (nombre == null || nombre.trim().isEmpty()) {
            return null;
        }

        // Recorre todo el catálogo.
        for (int i = 0; i < catalogoCanciones.size(); i++) {

            // Obtiene la canción ubicada en la posición actual.
            Cancion cancion = catalogoCanciones.get(i);

            // Verifica si el nombre coincide.
            if (cancion.getNombre().equalsIgnoreCase(nombre)) {
                return cancion;
            }
        }
        // Devuelve null si no encuentra la canción.
        return null;
    }

    // Metodo para buscar canciones por nombre (coincidencia parcial).
    // A diferencia de buscarCancionPorNombre(), que se usa para validar
    // duplicados y ubicar una canción exacta (por ejemplo al comprarla),
    // este método sirve para la función de búsqueda general y devuelve
    // todas las canciones cuyo nombre contenga el texto ingresado.
    public ArrayList<Cancion> buscarCancionesPorNombre(String nombre) {

        ArrayList<Cancion> cancionesEncontradas = new ArrayList<>();

        if (nombre == null || nombre.trim().isEmpty()) {
            return cancionesEncontradas;
        }

        String nombreBuscado = nombre.toLowerCase();

        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            if (cancion.getNombre().toLowerCase().contains(nombreBuscado)) {
                cancionesEncontradas.add(cancion);
            }
        }
        return cancionesEncontradas;
    }

    // Metodo para buscar canciones por género
    public ArrayList<Cancion> buscarCancionesPorGenero(String genero) {

        // Lista donde se guardan las canciones encontradas.
        ArrayList<Cancion> cancionesEncontradas = new ArrayList<>();

        // Recorre el catálogo completo.
        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            // Agrega la canción si el género coincide.
            if (cancion.getGenero().equalsIgnoreCase(genero)) {
                cancionesEncontradas.add(cancion);
            }
        }
        return cancionesEncontradas;
    }

    // Metodo para buscar canciones por artista
    public ArrayList<Cancion> buscarCancionesPorArtista(String artista) {

        // Lista donde se guardan las canciones encontradas.
        ArrayList<Cancion> cancionesEncontradas = new ArrayList<>();

        // Recorre el catálogo completo.
        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            // Agrega la canción si el artista coincide.
            if (cancion.getArtista().equalsIgnoreCase(artista)) {
                cancionesEncontradas.add(cancion);
            }
        }
        return cancionesEncontradas;
    }

    // Metodo para mostrar el resultado de una búsqueda de canciones.
    public void mostrarCancionesEncontradas(ArrayList<Cancion> canciones) {

        if (canciones.isEmpty()) {
            System.out.println("No se encontraron canciones con ese criterio.");
            return;
        }

        System.out.println("\n===== CANCIONES ENCONTRADAS =====");

        for (int i = 0; i < canciones.size(); i++) {

            Cancion cancion = canciones.get(i);

            System.out.println("\nCanción número " + (i + 1));
            System.out.println("---------------------------");
            System.out.println(cancion);
        }
    }

    // Metodo para mostrar todas las canciones registradas en el catálogo
    public void mostrarCatalogoCanciones() {

        // Verifica si el catálogo está vacío.
        if (catalogoCanciones.isEmpty()) {
            System.out.println("No hay canciones registradas en el catálogo.");
            return;
        }

        System.out.println("\n===== CATÁLOGO DE CANCIONES =====");

        // Recorre todas las canciones almacenadas en el catálogo.
        for (int i = 0; i < catalogoCanciones.size(); i++) {

            // Obtiene la canción ubicada en la posición actual del ArrayList.
            Cancion cancion = catalogoCanciones.get(i);

            System.out.println(
                    "\nCanción número " + (i + 1));

            // Muestra la información de la canción.
            System.out.println("---------------------------");
            System.out.println(cancion);
        }
    }

    // Método para mostrar los usuarios registrados
    public void mostrarUsuariosFinales() {

        // Verifica si existen usuarios.
        if (usuariosFinales.isEmpty()) {
            System.out.println("No hay usuarios finales registrados.");
            return;
        }

        System.out.println("\n===== USUARIOS REGISTRADOS =====");

        // Recorre todos los usuarios registrados.
        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            System.out.println(
                    "\ncr.ac.ucenfotec.bl.entities.usuario.Usuario número " + (i + 1));

            System.out.println("---------------------------");
            System.out.println(usuario);
        }
    }

    // Método para mostrar las nacionalidades disponibles
    public void mostrarNacionalidadesPermitidas() {

        System.out.println("\nNacionalidades disponibles:");

        for (int i = 0;
             i < nacionalidadesPermitidas.size();
             i++) {

            System.out.println(
                    (i + 1) + ". "
                            + nacionalidadesPermitidas.get(i));
        }
    }

    @Override
    public String toString() {

        String estadoAdministrador;

        // Determina si existe un administrador registrado.
        if (administrador != null) {
            estadoAdministrador = "Sí";
        } else {
            estadoAdministrador = "No";
        }

        return "Aplicación de Streaming Musical" +
                "\ncr.ac.ucenfotec.bl.entities.administrador.Administrador registrado: " + estadoAdministrador +
                "\nCantidad de usuarios finales: " + usuariosFinales.size() +
                "\nCantidad de canciones en catálogo: " + catalogoCanciones.size();
    }
}