import java.time.LocalDate;

public class ListaReproduccion {

    private String nombre;
    private LocalDate fechaCreacion;
    private float calificacion;
    private Cancion[] canciones;

    private int cantidadCanciones;

    public ListaReproduccion(String nombre, LocalDate fechaCreacion, float calificacion, byte cantidadMaximaCanciones) {
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.canciones = new Cancion[cantidadMaximaCanciones];
        this.cantidadCanciones = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public Cancion[] getCanciones() {
        return canciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (cantidadCanciones >= canciones.length) {
            System.out.println("La lista ya está llena.");
            return;
        }

        canciones[cantidadCanciones] = cancion;
        cantidadCanciones++;

        System.out.println("Canción agregada a la lista correctamente.");
    }

    public void calcularCalificacion() {
        if (cantidadCanciones == 0) {
            calificacion = 0;
            return;
        }

        float suma = 0;

        for (int i = 0; i < cantidadCanciones; i++) {
            suma = suma + canciones[i].getCalificacion();
        }

        calificacion = suma / cantidadCanciones;
    }

    public String mostrarCanciones() {
        String texto = "";

        for (int i = 0; i < cantidadCanciones; i++) {
            texto = texto + canciones[i].getNombre();

            if (i < cantidadCanciones - 1) {
                texto = texto + ", ";
            }
        }

        return texto;
    }

    public String toString() {
        return "Lista: " + nombre +
                "\nFecha de creación: " + fechaCreacion +
                "\nCalificación: " + calificacion +
                "\nCanciones: " + mostrarCanciones();
    }
}