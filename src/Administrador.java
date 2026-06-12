public class Administrador {
    // Atributos
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private ColaReproduccion colaReproduccion;

    // Constructor
    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia, byte cantidadCancionesCola) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;

        // Composición
        colaReproduccion = new ColaReproduccion(cantidadCancionesCola);
    }

    // Getters

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    // Setters

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    // Métodos

    public void registrarCancion(Cancion cancion) {
        System.out.println("El administrador " + nombreUsuario +
                " registró la canción " + cancion.getNombre());
    }

    public void agregarCancionACola(Cancion cancion) {
        colaReproduccion.agregarCancion(cancion);

        System.out.println("La canción " + cancion.getNombre() +
                " fue agregada a la cola de reproducción.");
    }

    @Override
    public String toString() {
        return "\nInformación del Administrador\n" +
                "Correo electrónico: " + correoElectronico + "\n" +
                "Usuario: " + nombreUsuario;
    }
}
