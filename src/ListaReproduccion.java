import java.time.LocalDate;
import java.util.ArrayList;

public class ListaReproduccion {

    // Atributos
    private String nombre;
    private LocalDate fechaCreacion;
    private float calificacion;
    private ArrayList<Cancion> canciones;

    // Constructor
    public ListaReproduccion(String nombre, LocalDate fechaCreacion) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = 0;
        this.canciones = new ArrayList<>();
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    // Métodos

    // Método para agregar una canción a la lista de reproducción
    public void agregarCancion(Cancion cancion) {

        // Verifica que la canción recibida exista.
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        // Agrega la canción al ArrayList.
        canciones.add(cancion);

        // Aumenta el contador utilizado para el Top 3.
        cancion.aumentarVecesAgregadaAListas();

        // Actualiza la calificación promedio de la lista.
        calcularCalificacion();

        System.out.println("Canción agregada a la lista correctamente.");
    }

    // Método para calcular el promedio de las calificaciones de las canciones
    public void calcularCalificacion() {

        // Si la lista está vacía, la calificación es cero.
        if (canciones.isEmpty()) {
            calificacion = 0;
            return;
        }

        float sumaCalificaciones = 0;

        // Recorre todas las canciones de la lista.
        for (int i = 0; i < canciones.size(); i++) {

            Cancion cancion = canciones.get(i);

            sumaCalificaciones =
                    sumaCalificaciones + cancion.getCalificacion();
        }

        // Calcula el promedio.
        calificacion = sumaCalificaciones / canciones.size();
    }

    // Método para buscar una canción por su nombre dentro de la lista
    public Cancion buscarCancionPorNombre(String nombreCancion) {

        // Recorre todas las canciones de la lista.
        for (int i = 0; i < canciones.size(); i++) {

            Cancion cancion = canciones.get(i);

            // Compara el nombre de la canción sin importar mayúsculas o minúsculas.
            if (cancion.getNombre().equalsIgnoreCase(nombreCancion)) {
                return cancion;
            }
        }

        // Devuelve null si no se encuentra la canción.
        return null;
    }

    // Método para reproducir todas las canciones de la lista
    public void reproducirLista() {

        // Verifica si la lista está vacía.
        if (canciones.isEmpty()) {
            System.out.println(
                    "No hay canciones para reproducir en esta lista.");
            return;
        }

        System.out.println("\nReproduciendo lista: " + nombre);

        // Recorre y reproduce cada canción.
        for (int i = 0; i < canciones.size(); i++) {

            Cancion cancion = canciones.get(i);

            cancion.reproducir();
        }
    }

    // Método que devuelve los nombres de las canciones de la lista
    public String mostrarCanciones() {

        if (canciones.isEmpty()) {
            return "No hay canciones.";
        }

        String texto = "";

        for (int i = 0; i < canciones.size(); i++) {

            texto = texto + canciones.get(i).getNombre();

            if (i < canciones.size() - 1) {
                texto = texto + ", ";
            }
        }
        return texto;
    }

    @Override
    public String toString() {
        return "Lista: " + nombre +
                "\nFecha de creación: " + fechaCreacion +
                "\nCalificación: " + calificacion +
                "\nCanciones: " + mostrarCanciones();
    }
}