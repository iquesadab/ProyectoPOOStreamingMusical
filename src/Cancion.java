import java.time.LocalDate;

public class Cancion {

    private String nombre;
    private String genero;
    private LocalDate fechaLanzamiento;
    private float precio;
    private float calificacion;
    private String artista;
    private String compositor;
    private Album album;


    //metodo constructor
    public Cancion(String nombre, String genero, LocalDate fechaLanzamiento,
                   float precio, float calificacion, String artista,
                   String compositor, Album album) {
        this.nombre = nombre;
        this.genero = genero;
        this.fechaLanzamiento = fechaLanzamiento;
        this.precio = precio;
        this.calificacion = calificacion;
        this.artista = artista;
        this.compositor = compositor;
        this.album = album;
    }

    //getters
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

    public Album getAlbum() {
        return album;
    }



    //setters
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

    public void setAlbum(Album album) {
        this.album = album;
    }

    public void reproducir() {
        System.out.println("Reproduciendo: " + nombre + " - " + artista);
    }
    //metodo tostring
    public String toString() {
        return "Canción: " + nombre +
                "\nArtista: " + artista +
                "\nCompositor: " + compositor +
                "\nGénero: " + genero +
                "\nFecha de lanzamiento: " + fechaLanzamiento +
                "\nPrecio: " + precio +
                "\nCalificación: " + calificacion +
                "\nAlbum: " + (album != null ? album.getNombre() : "Sin album");
    }

}