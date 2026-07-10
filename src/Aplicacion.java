import java.util.ArrayList;

public class Aplicacion {

    // Atributos
    private Administrador administrador;
    private ArrayList<UsuarioFinal> usuariosFinales;
    private ArrayList<Cancion> catalogoCanciones;

    // Constructor
    public Aplicacion() {
        administrador = null;
        usuariosFinales = new ArrayList<>();
        catalogoCanciones = new ArrayList<>();
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

    // Métodos principales

    public boolean hayAdministrador() {
        if (administrador != null) {
            return true;
        } else {
            return false;
        }
    }

    public void registrarAdministrador(Administrador administrador) {
        if (administrador == null) {
            System.out.println("No se puede registrar un administrador vacío.");
            return;
        }

        if (hayAdministrador()) {
            System.out.println("Ya existe un administrador registrado.");
            return;
        }

        this.administrador = administrador;
        System.out.println("Administrador registrado correctamente.");
    }

    public void registrarUsuarioFinal(UsuarioFinal usuarioFinal) {
        if (usuarioFinal == null) {
            System.out.println("No se puede registrar un usuario vacío.");
            return;
        }

        usuariosFinales.add(usuarioFinal);
        System.out.println("Usuario registrado correctamente.");
    }

    public void registrarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("No se puede registrar una canción vacía.");
            return;
        }

        catalogoCanciones.add(cancion);
        System.out.println("Canción agregada al catálogo correctamente.");
    }

    // Metodo para buscar un usuario por su nombre de usuario
    public UsuarioFinal buscarUsuarioPorNombreUsuario(String nombreUsuario) {

        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            if (usuario.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    // Metodo para buscar una canción por su nombre
    public Cancion buscarCancionPorNombre(String nombre) {

        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            if (cancion.getNombre().equalsIgnoreCase(nombre)) {
                return cancion;
            }
        }
        return null;
    }

    // Metodo para buscar canciones por género
    public ArrayList<Cancion> buscarCancionesPorGenero(String genero) {

        ArrayList<Cancion> cancionesEncontradas = new ArrayList<>();

        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            if (cancion.getGenero().equalsIgnoreCase(genero)) {
                cancionesEncontradas.add(cancion);
            }
        }
        return cancionesEncontradas;
    }

    // Metodo para buscar canciones por artista
    public ArrayList<Cancion> buscarCancionesPorArtista(String artista) {

        ArrayList<Cancion> cancionesEncontradas = new ArrayList<>();

        for (int i = 0; i < catalogoCanciones.size(); i++) {

            Cancion cancion = catalogoCanciones.get(i);

            if (cancion.getArtista().equalsIgnoreCase(artista)) {
                cancionesEncontradas.add(cancion);
            }
        }
        return cancionesEncontradas;
    }

    // Metodo para iniciar sesión del administrador
    public boolean iniciarSesionAdministrador(String nombreUsuario, String contrasenia) {

        // Verifica que exista un administrador registrado.
        if (administrador == null) {
            return false;
        }
        // Verifica que el nombre de usuario y la contraseña sean correctos.
        if (administrador.getNombreUsuario().equals(nombreUsuario)
            && administrador.getContrasenia().equals(contrasenia)) {
            return true;
        } else {
            return false;
        }
    }

    // Metodo para iniciar sesión de un usuario final
    public UsuarioFinal iniciarSesionUsuarioFinal(String nombreUsuario, String contrasenia) {

        // Recorre la lista de usuarios finales registrados en la aplicación.
        for (int i = 0; i < usuariosFinales.size(); i++) {

            // Obtiene el usuario ubicado en la posición actual del ArrayList.
            UsuarioFinal usuario = usuariosFinales.get(i);

            // Verifica si el nombre de usuario ingresado coincide con el del usuario actual.
            boolean usuarioCorrecto = usuario.getNombreUsuario().equals(nombreUsuario);

            // Verifica si la contraseña ingresada coincide con la del usuario actual.
            boolean contraseniaCorrecta = usuario.getContrasenia().equals(contrasenia);

            // Si ambas credenciales son correctas, devuelve el usuario que inició sesión.
            if (usuarioCorrecto && contraseniaCorrecta) {
                return usuario;
            }
        }
        // Si ningún usuario coincide con las credenciales ingresadas, devuelve null.
        return null;
    }

    // Metodo para mostrar todas las canciones registradas en el catálogo
    public void mostrarCatalogoCanciones() {

        // Verifica si el catálogo está vacío.
        if (catalogoCanciones.isEmpty()) {
            System.out.println("No hay canciones registradas en el catálogo.");
            return;
        }

        System.out.println("\nCatálogo de canciones:");

        // Recorre todas las canciones almacenadas en el catálogo.
        for (int i = 0; i < catalogoCanciones.size(); i++) {

            // Obtiene la canción ubicada en la posición actual del ArrayList.
            Cancion cancion = catalogoCanciones.get(i);

            // Muestra la información de la canción.
            System.out.println("---------------------------");
            System.out.println(cancion);
        }
    }

    public void mostrarUsuariosFinales() {
        if (usuariosFinales.isEmpty()) {
            System.out.println("No hay usuarios finales registrados.");
            return;
        }

        System.out.println("\nUsuarios registrados:");

        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            System.out.println("---------------------------");
            System.out.println(usuario);
        }
    }

    // Metodo para validar que una contraseña cumpla con los requisitos establecidos
    public boolean validarContrasenia(String contrasenia) {

        // Verifica que la contraseña no sea nula.
        if (contrasenia == null) {
            return false;
        }

        // Verifica que la longitud esté entre 8 y 12 caracteres.
        if (contrasenia.length() < 8 || contrasenia.length() > 12) {
            return false;
        }

        // Variables para verificar si la contraseña contiene cada uno de los requisitos.
        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        // Recorre todos los caracteres de la contraseña.
        for (int i = 0; i < contrasenia.length(); i++) {

            // Obtiene el carácter ubicado en la posición actual.
            char caracter = contrasenia.charAt(i);

            // Verifica si el carácter es una letra mayúscula.
            if (Character.isUpperCase(caracter)) {
                tieneMayuscula = true;

                // Verifica si el carácter es una letra minúscula.
            } else if (Character.isLowerCase(caracter)) {
                tieneMinuscula = true;

                // Verifica si el carácter es un número.
            } else if (Character.isDigit(caracter)) {
                tieneNumero = true;

                // Si no cumple ninguna de las condiciones anteriores,
                // se considera un carácter especial.
            } else {
                tieneEspecial = true;
            }
        }
        // Devuelve true únicamente si la contraseña cumple con todos los requisitos.
        if (tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial){
            return true;
        } else {
            return false;
        }
    }

    // Metodo para verificar la confirmacion de la contraseña
    public boolean confirmarContrasenia(String contrasenia, String confirmacion) {
        if (contrasenia == null || confirmacion == null) {
            return false;
        }

        if (contrasenia.equals(confirmacion)){
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        String estadoAdministrador;

        if (administrador != null) {
            estadoAdministrador = "Sí";
        } else {
            estadoAdministrador = "No";
        }

        return "Aplicación de Streaming Musical" +
                "\nAdministrador registrado: " + estadoAdministrador +
                "\nCantidad de usuarios finales: " + usuariosFinales.size() +
                "\nCantidad de canciones en catálogo: " + catalogoCanciones.size();
    }
}
