package cr.ac.ucenfotec.bl;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;

import java.util.Random;

public class Reproductor {

    /* Duración simulada de una canción (en segundos), usada únicamente
    para elegir al azar el punto donde inicia la vista previa.*/
    private static final int DURACION_SIMULADA_SEGUNDOS = 180;

    private final Random generadorAleatorio = new Random();

    public void reproducirCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("No se puede reproducir una canción vacía.");
            return;
        }

        cancion.aumentarReproduccion();
    }

    public void reproducirLista(ListaReproduccion lista) {
        if (lista == null) {
            System.out.println("No se puede reproducir una lista vacía.");
            return;
        }

        lista.reproducirLista();
    }

    // Metodo para reproducir una vista previa de 30 segundos
    public void reproducirVistaPrevia(Cancion cancion) {

        if (cancion == null) {
            System.out.println("No se puede reproducir una canción vacía.");
            return;
        }

        /* Se elige al azar el segundo en el que inicia la vista previa,
        dejando espacio suficiente para los 30 segundos de muestra.*/
        int inicioAleatorio = generadorAleatorio.nextInt(
                Math.max(1, DURACION_SIMULADA_SEGUNDOS - 30)
        );

        System.out.println("\n==================================");
        System.out.println("      VISTA PREVIA (30 SEGUNDOS)");
        System.out.println("==================================");
        System.out.println("Canción: " + cancion.getNombre());
        System.out.println("Artista: " + cancion.getArtista());
        System.out.println("\n♪ Reproduciendo desde el segundo "
                + inicioAleatorio + " hasta el " + (inicioAleatorio + 30) + "...");
        System.out.println("\n(Fin de la vista previa)");
    }
}