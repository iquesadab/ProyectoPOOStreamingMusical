package modelo;

import java.util.ArrayList;

public class ListaReproduccion {
    private String nombre;
    private ArrayList<Cancion> canciones;

    public ListaReproduccion(String nombre) {
        this.nombre = nombre;
        this.canciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Cancion> getCanciones() {
        return canciones;
    }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("No se puede agregar una canción vacía.");
            return;
        }

        canciones.add(cancion);
        cancion.aumentarVecesAgregadaAListas();

        System.out.println("Canción agregada a la lista: " + cancion.getNombre());
    }

    public void mostrarLista() {
        if (canciones.isEmpty()) {
            System.out.println("La lista de reproducción está vacía.");
            return;
        }

        System.out.println("Lista de reproducción: " + nombre);

        for (int i = 0; i < canciones.size(); i++) {
            System.out.print((i + 1) + ". ");
            canciones.get(i).mostrarInformacion();
        }
    }

    public Cancion buscarCancionPorNombre(String nombreCancion) {
        for (Cancion cancion : canciones) {
            if (cancion.getNombre().equalsIgnoreCase(nombreCancion)) {
                return cancion;
            }
        }

        return null;
    }

    public void reproducirLista() {
        if (canciones.isEmpty()) {
            System.out.println("No hay canciones para reproducir en esta lista.");
            return;
        }

        System.out.println("Reproduciendo lista: " + nombre);

        for (Cancion cancion : canciones) {
            cancion.aumentarReproduccion();
            System.out.println("Reproduciendo: " +
                    cancion.getNombre() + " - " + cancion.getArtista());
        }
    }
}
