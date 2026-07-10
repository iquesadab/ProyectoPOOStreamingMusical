import modelo.Cancion;
import modelo.ListaReproduccion;
import modelo.ColaReproduccion;
import modelo.Reproductor;
import modelo.TopCanciones;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        ArrayList<Cancion> catalogo = new ArrayList<>();

        Cancion c1 = new Cancion("Ojitos Lindos", "Bad Bunny", "Reguetón", 4.8, 15);
        Cancion c2 = new Cancion("Luna", "Feid", "Urbano", 4.6, 20);
        Cancion c3 = new Cancion("Caracas en el 2000", "Danny Ocean", "Pop", 4.9, 12);
        Cancion c4 = new Cancion("La Bachata", "Manuel Turizo", "Bachata", 4.7, 18);

        catalogo.add(c1);
        catalogo.add(c2);
        catalogo.add(c3);
        catalogo.add(c4);

        ListaReproduccion lista1 = new ListaReproduccion("Mis favoritas");

        lista1.agregarCancion(c1);
        lista1.agregarCancion(c2);
        lista1.agregarCancion(c3);

        System.out.println();

        lista1.mostrarLista();

        System.out.println();

        Cancion cancionBuscada = lista1.buscarCancionPorNombre("Luna");

        if (cancionBuscada != null) {
            System.out.println("Canción encontrada en la lista: " + cancionBuscada.getNombre());
        } else {
            System.out.println("La canción no se encontró en la lista.");
        }

        System.out.println();

        Reproductor reproductor = new Reproductor();

        reproductor.reproducirCancion(c1);

        System.out.println();

        reproductor.reproducirLista(lista1);

        System.out.println();

        ColaReproduccion cola = new ColaReproduccion();

        cola.agregarACola(c4);
        cola.agregarACola(c2);
        cola.agregarACola(c1);

        System.out.println();

        cola.mostrarCola();

        System.out.println();

        cola.reproducirSiguiente();
        cola.reproducirSiguiente();

        System.out.println();

        TopCanciones top = new TopCanciones();

        top.mostrarTop3MejorCalificadas(catalogo);

        System.out.println();

        top.mostrarTop3MasCompradas(catalogo);

        System.out.println();

        top.mostrarTop3MasAgregadasAListas(catalogo);
    }
}
