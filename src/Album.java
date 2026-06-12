import java.time.LocalDate;

public class Album {

    private String nombre;
    private LocalDate fechaLanzamiento;
    private String caratula;
    private Cancion[] canciones;

    private int cantidadCanciones;


    //metodo constructor
    public Album(String nombre, LocalDate fechaLanzamiento, String caratula, byte cantidadMaximaCanciones) {
        this.nombre = nombre;
        this.fechaLanzamiento = fechaLanzamiento;
        this.caratula = caratula;
        this.canciones = new Cancion[cantidadMaximaCanciones];
        this.cantidadCanciones = 0;
    }


    //getters
    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public String getCaratula() {
        return caratula;
    }

    public Cancion[] getCanciones() {
        return canciones;
    }



    //setters
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void setCaratula(String caratula) {
        this.caratula = caratula;
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (cantidadCanciones >= canciones.length) {
            System.out.println("El álbum ya está lleno.");
            return;
        }

        canciones[cantidadCanciones] = cancion;
        cantidadCanciones++;

        System.out.println("Canción agregada al álbum correctamente.");
    }

    public String mostrarCanciones() {
        String texto = "";

        for (int i = 0; i < cantidadCanciones; i++) {
            texto = texto + canciones[i].getnombre();

            if (i < cantidadCanciones - 1) {
                texto = texto + ", ";
            }
        }

        return texto;
    }

    public String toString() {
        return "Álbum: " + nombre +
                "\nFecha de lanzamiento: " + fechaLanzamiento +
                "\nCarátula: " + caratula +
                "\nCanciones: " + mostrarCanciones();
    }

}