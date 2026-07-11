import java.util.LinkedList;
import java.util.Queue;

public class ColaReproduccion {

    // Atributo que almacena las canciones en el orden en que fueron agregadas.
    private Queue<Cancion> cola;

    // Constructor
    public ColaReproduccion() {
        this.cola = new LinkedList<>();
    }

    // Getter
    public Queue<Cancion> getCola() {
        return cola;
    }

    // Método para agregar una canción al final de la cola.
    public void agregarCancion(Cancion cancion) {

        // Verifica que la canción recibida exista.
        if (cancion == null) {
            System.out.println("No se puede agregar una canción vacía a la cola.");
            return;
        }

        // Agrega la canción al final de la cola.
        cola.add(cancion);

        System.out.println("Canción agregada a la cola: "
                + cancion.getNombre());
    }

    // Método para reproducir y eliminar la primera canción de la cola.
    public void reproducirSiguiente() {

        // Verifica si la cola está vacía.
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        // poll() obtiene y elimina la primera canción de la cola.
        Cancion cancion = cola.poll();

        // El método reproducir también incrementa el contador de reproducciones.
        cancion.aumentarReproduccion();
    }

    // Método para eliminar la primera canción sin reproducirla.
    public void eliminarCancion() {

        // Verifica si la cola está vacía.
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        // poll() obtiene y elimina la primera canción.
        Cancion cancionEliminada = cola.poll();

        System.out.println("Se eliminó la canción: "
                + cancionEliminada.getNombre());
    }

    // Método para mostrar todas las canciones de la cola.
    public void mostrarCola() {

        // Verifica si la cola está vacía.
        if (cola.isEmpty()) {
            System.out.println("La cola de reproducción está vacía.");
            return;
        }

        System.out.println("\nCola de reproducción:");

        int contador = 1;

        for (Cancion cancion : cola) {
            System.out.println(contador + ". "
                    + cancion.getNombre() + " - "
                    + cancion.getArtista());

            contador++;
        }
    }

    // Método para verificar si la cola está vacía.
    public boolean estaVacia() {
        return cola.isEmpty();
    }

    @Override
    public String toString() {

        // Si la cola está vacía, devuelve un mensaje.
        if (cola.isEmpty()) {
            return "La cola de reproducción está vacía.";
        }

        String texto = "";

        int contador = 1;

        for (Cancion cancion : cola) {
            texto = texto + contador + ". "
                    + cancion.getNombre() + " - "
                    + cancion.getArtista() + "\n";

            contador++;
        }

        return texto;
    }
}