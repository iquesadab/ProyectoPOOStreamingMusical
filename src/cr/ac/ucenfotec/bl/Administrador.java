package cr.ac.ucenfotec.bl;

public class Administrador extends Usuario {

    // Constructor
    public Administrador(String correoElectronico, String nombreUsuario, String contrasenia) {
        // Invoca al constructor de la clase padre (cr.ac.ucenfotec.bl.Usuario)
        super(correoElectronico, nombreUsuario, contrasenia);
    }

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
        return "\nInformación del cr.ac.ucenfotec.bl.Administrador\n" +
                "Correo electrónico: " + correoElectronico + "\n" +
                "cr.ac.ucenfotec.bl.Usuario: " + nombreUsuario;
    }
}