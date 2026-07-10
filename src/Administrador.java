public class Administrador extends Usuario {
    private ColaReproduccion colaReproduccion;

    // Constructor
    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        // Invoca al constructor de la clase padre (Usuario)
        super(correoElectronico, nombreUsuario, contrasenia);
        this.colaReproduccion = new ColaReproduccion();
    }

    // Getter
    public ColaReproduccion getColaReproduccion() { return colaReproduccion; }

    // Métodos
    public void registrarCancion(Cancion cancion) {
        System.out.println("El administrador " + nombreUsuario + " registró la canción " + cancion.getNombre());
    }

    public void agregarCancionACola(Cancion cancion) {
        colaReproduccion.agregarCancion(cancion);
        System.out.println("La canción " + cancion.getNombre() + " fue agregada a la cola de reproducción.");
    }

    @Override
    public String toString() {
        return "\nInformación del Administrador\n" +
                "Correo electrónico: " + correoElectronico + "\n" +
                "Usuario: " + nombreUsuario;
    }
}