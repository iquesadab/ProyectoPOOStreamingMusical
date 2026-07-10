package modelo;

public class Cancion {
    private String nombre;
    private String artista;
    private String genero;
    private double calificacionPromedio;
    private int vecesComprada;
    private int vecesAgregadaAListas;
    private int vecesReproducida;

    public Cancion(String nombre, String artista, String genero, double calificacionPromedio, int vecesComprada) {
        this.nombre = nombre;
        this.artista = artista;
        this.genero = genero;
        this.calificacionPromedio = calificacionPromedio;
        this.vecesComprada = vecesComprada;
        this.vecesAgregadaAListas = 0;
        this.vecesReproducida = 0;
    }

    public String getNombre() {
        return nombre;
    }

    public String getArtista() {
        return artista;
    }

    public String getGenero() {
        return genero;
    }

    public double getCalificacionPromedio() {
        return calificacionPromedio;
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

    public void aumentarVecesAgregadaAListas() {
        vecesAgregadaAListas++;
    }

    public void aumentarReproduccion() {
        vecesReproducida++;
    }

    public void mostrarInformacion() {
        System.out.println(nombre + " - " + artista +
                " | Género: " + genero +
                " | Calificación: " + calificacionPromedio);
    }
}