package cr.ac.ucenfotec.bl;

public class Reproductor {

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

    // Método para reproducir una vista previa de 30 segundos
    public void reproducirVistaPrevia(Cancion cancion) {

        if (cancion == null) {
            System.out.println("No se puede reproducir una canción vacía.");
            return;
        }

        System.out.println("\n==================================");
        System.out.println("      VISTA PREVIA (30 SEGUNDOS)");
        System.out.println("==================================");
        System.out.println("Canción: " + cancion.getNombre());
        System.out.println("Artista: " + cancion.getArtista());
        System.out.println("\n♪ Reproduciendo los primeros 30 segundos...");
        System.out.println("\n(Fin de la vista previa)");
    }
}