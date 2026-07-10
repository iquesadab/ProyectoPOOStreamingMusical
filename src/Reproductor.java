public class Reproductor {

    public void reproducirCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("No se puede reproducir una canción vacía.");
            return;
        }

        cancion.aumentarReproduccion();

        System.out.println("Reproduciendo canción: " +
                cancion.getNombre() + " - " + cancion.getArtista());
    }

    public void reproducirLista(ListaReproduccion lista) {
        if (lista == null) {
            System.out.println("No se puede reproducir una lista vacía.");
            return;
        }

        lista.reproducirLista();
    }
}