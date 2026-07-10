package modelo;

import java.util.LinkedList;
import java.util.Queue;

public class ColaReproduccion {
    private Queue<Cancion> cola;

    public ColaReproduccion() {
        this.cola = new LinkedList<>();
    }

    public void agregarACola(Cancion cancion) {
        if (cancion == null) {
            System.out.println("No se puede agregar una canción vacía a la cola.");
            return;
        }

        cola.add(cancion);
        System.out.println("Canción agregada a la cola: " + cancion.getNombre());
    }

    public void reproducirSiguiente() {
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        Cancion cancion = cola.poll();
        cancion.aumentarReproduccion();

        System.out.println("Reproduciendo siguiente canción: " +
                cancion.getNombre() + " - " + cancion.getArtista());
    }

    public void mostrarCola() {
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        System.out.println("Cola de reproducción:");

        int contador = 1;

        for (Cancion cancion : cola) {
            System.out.println(contador + ". " +
                    cancion.getNombre() + " - " + cancion.getArtista());
            contador++;
        }
    }

    public boolean estaVacia() {
        return cola.isEmpty();
    }
}