import java.time.LocalDate;

public class Cancion {

    // Atributos con la información de la canción
    private String nombre;
    private String genero;
    private LocalDate fechaLanzamiento;
    private float precio;
    private float calificacion;
    private String artista;
    private String compositor;
    private String nombreAlbum;
    private String caratulaAlbum;

    // Contadores utilizados para generar estadísticas y Top 3
    private int vecesComprada;
    private int vecesAgregadaAListas;
    private int vecesReproducida;

    // Constructor
    public Cancion(String nombre, String genero, LocalDate fechaLanzamiento,
                   float precio, float calificacion, String artista,
                   String compositor, String nombreAlbum,
                   String caratulaAlbum) {

        this.nombre = nombre;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precio = precio;
        this.calificacion = calificacion;
        this.artista = artista;
        this.compositor = compositor;

        // Si la canción no pertenece a un álbum, se asigna un valor predeterminado.
        if (nombreAlbum == null || nombreAlbum.trim().isEmpty()) {
            this.nombreAlbum = "Sin álbum";
        } else {
            this.nombreAlbum = nombreAlbum;
        }

        // Si el álbum no tiene carátula, se asigna una imagen predeterminada.
        if (caratulaAlbum == null || caratulaAlbum.trim().isEmpty()) {
            this.caratulaAlbum = "default.jpg";
        } else {
            this.caratulaAlbum = caratulaAlbum;
        }

        // Los contadores comienzan en cero cuando se registra la canción.
        this.vecesComprada = 0;
        this.vecesAgregadaAListas = 0;
        this.vecesReproducida = 0;
    }

    // Getters

    public String getNombre() {
        return nombre;
    }

    public String getGenero() {
        return genero;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public float getPrecio() {
        return precio;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public String getArtista() {
        return artista;
    }

    public String getCompositor() {
        return compositor;
    }

    public String getNombreAlbum() {
        return nombreAlbum;
    }

    public String getCaratulaAlbum() {
        return caratulaAlbum;
    }

    public int getVecesComprada() {
        return vecesComprada;
    }

    public int getVecesAgregadaAListas() {
        return vecesAgregadaAListas;
    }

    public int getVecesReproducida() {
        return vecesReproducida;
    }

    // Setters

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public void setCalificacion(float calificacion) {
        this.calificacion = calificacion;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public void setCompositor(String compositor) {
        this.compositor = compositor;
    }

    public void setNombreAlbum(String nombreAlbum) {
        this.nombreAlbum = nombreAlbum;
    }

    public void setCaratulaAlbum(String caratulaAlbum) {
        this.caratulaAlbum = caratulaAlbum;
    }

    // Métodos

    // Aumenta el contador cada vez que un usuario compra la canción.
    public void aumentarVecesComprada() {
        vecesComprada++;
    }

    // Aumenta el contador cada vez que la canción se agrega a una lista.
    public void aumentarVecesAgregadaAListas() {
        vecesAgregadaAListas++;
    }

    // Reproduce la canción y aumenta su contador de reproducciones.
    public void reproducir() {
        vecesReproducida++;

        System.out.println("Reproduciendo: " + nombre + " - " + artista);
    }

    @Override
    public String toString() {
        return "Canción: " + nombre +
                "\nArtista: " + artista +
                "\nCompositor: " + compositor +
                "\nGénero: " + genero +
                "\nFecha de lanzamiento: " + fechaLanzamiento +
                "\nPrecio: $" + precio +
                "\nCalificación: " + calificacion +
                "\nÁlbum: " + nombreAlbum +
                "\nCarátula: " + caratulaAlbum +
                "\nVeces comprada: " + vecesComprada +
                "\nVeces agregada a listas: " + vecesAgregadaAListas +
                "\nVeces reproducida: " + vecesReproducida;
    }
}