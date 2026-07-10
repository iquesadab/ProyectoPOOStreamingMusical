public class ColaReproduccion {
    private Cancion[] canciones;
    private int cantidadCanciones;

    public ColaReproduccion(byte cantidadMaximaCanciones) {
        this.canciones = new Cancion[cantidadMaximaCanciones];
        this.cantidadCanciones = 0;
    }

    public Cancion[] getCanciones() { return canciones; }

    public void agregarCancion(Cancion cancion) {
        if (cancion == null) {
            System.out.println("La canción no existe.");
            return;
        }

        if (cantidadCanciones >= canciones.length) {
            System.out.println("La cola ya está llena.");
            return;
        }

        canciones[cantidadCanciones] = cancion;
        cantidadCanciones++;
        System.out.println("Canción agregada a la cola correctamente.");
    }

    public void eliminarCancion() {
        if (cantidadCanciones == 0) {
            System.out.println("La cola está vacía.");
            return;
        }

        System.out.println("Se eliminó la canción: " + canciones[0].getNombre());

        for (int i = 0; i < cantidadCanciones - 1; i++) {
            canciones[i] = canciones[i + 1];
        }

        canciones[cantidadCanciones - 1] = null;
        cantidadCanciones--;
    }

    public String mostrarCanciones() {
        String texto = "";
        for (int i = 0; i < cantidadCanciones; i++) {
            texto = texto + canciones[i].getNombre();
            if (i < cantidadCanciones - 1) {
                texto = texto + ", ";
            }
        }
        return texto;
    }

    @Override
    public String toString() { return mostrarCanciones(); }
}