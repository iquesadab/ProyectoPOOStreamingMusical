package modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TopCanciones {

    public void mostrarTop3MejorCalificadas(ArrayList<Cancion> canciones) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No hay canciones registradas.");
            return;
        }

        ArrayList<Cancion> copia = new ArrayList<>(canciones);

        Collections.sort(copia, new Comparator<Cancion>() {
            @Override
            public int compare(Cancion c1, Cancion c2) {
                return Double.compare(c2.getCalificacionPromedio(), c1.getCalificacionPromedio());
            }
        });

        System.out.println("Top 3 de canciones mejor calificadas:");
        mostrarTop3(copia);
    }

    public void mostrarTop3MasCompradas(ArrayList<Cancion> canciones) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No hay canciones registradas.");
            return;
        }

        ArrayList<Cancion> copia = new ArrayList<>(canciones);

        Collections.sort(copia, new Comparator<Cancion>() {
            @Override
            public int compare(Cancion c1, Cancion c2) {
                return Integer.compare(c2.getVecesComprada(), c1.getVecesComprada());
            }
        });

        System.out.println("Top 3 de canciones más compradas:");
        mostrarTop3(copia);
    }

    public void mostrarTop3MasAgregadasAListas(ArrayList<Cancion> canciones) {
        if (canciones == null || canciones.isEmpty()) {
            System.out.println("No hay canciones registradas.");
            return;
        }

        ArrayList<Cancion> copia = new ArrayList<>(canciones);

        Collections.sort(copia, new Comparator<Cancion>() {
            @Override
            public int compare(Cancion c1, Cancion c2) {
                return Integer.compare(c2.getVecesAgregadaAListas(), c1.getVecesAgregadaAListas());
            }
        });

        System.out.println("Top 3 de canciones más agregadas a listas:");
        mostrarTop3(copia);
    }

    private void mostrarTop3(ArrayList<Cancion> canciones) {
        int limite = Math.min(3, canciones.size());

        for (int i = 0; i < limite; i++) {
            Cancion cancion = canciones.get(i);

            System.out.println((i + 1) + ". " +
                    cancion.getNombre() + " - " +
                    cancion.getArtista() +
                    " | Calificación: " + cancion.getCalificacionPromedio() +
                    " | Compras: " + cancion.getVecesComprada() +
                    " | Agregada a listas: " + cancion.getVecesAgregadaAListas());
        }
    }
}
