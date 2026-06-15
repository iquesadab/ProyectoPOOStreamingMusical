import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {

        // Creación del administrador
        Administrador administrador = new Administrador("admin@streaming.com",
                                    "adminPrincipal", "Admin123!", (byte) 5);

        // Creación de un álbum
        Album album1 = new Album("Grandes Éxitos", LocalDate.of(2020, 5, 15),
                                "caratula_grandes_exitos.jpg", (byte) 5);

        // Creación de canciones
        Cancion cancion1 = new Cancion("Luz de Luna", "Pop",
                LocalDate.of(2020, 5, 15), 1.99f, 4.8f,
                "Sofía Marín", "Carlos Méndez", album1);

        Cancion cancion2 = new Cancion("Camino Libre", "Rock",
                LocalDate.of(2021, 8, 10), 2.50f, 4.5f,
                "Grupo Horizonte", "Ana Castillo", album1);

        // Asociación: Administrador - Cancion
        administrador.registrarCancion(cancion1);
        administrador.registrarCancion(cancion2);

        // Composición: Administrador - ColaReproduccion
        administrador.agregarCancionACola(cancion1);
        administrador.agregarCancionACola(cancion2);

        // Agregación: Album - Cancion
        album1.agregarCancion(cancion1);
        album1.agregarCancion(cancion2);

        // Creación de un usuario final
        UsuarioFinal usuario1 = new UsuarioFinal(
                "María Fernanda Rojas", LocalDate.of(2001, 3, 22),
                "Costarricense", "1-1234-5678", "avatar_maria.png",
                "maria@correo.com", "mariaR", "Maria123!",
                10.00f, (byte) 10, (byte) 3);

        // Compra de canciones
        usuario1.comprarCancion(cancion1);
        usuario1.comprarCancion(cancion2);

        // Composición: UsuarioFinal - ListaReproduccion
        usuario1.crearListaReproduccion("Mis favoritas");

        ListaReproduccion lista1 = usuario1.getListasReproduccion()[0];

        // Agregación: ListaReproduccion - Cancion
        usuario1.agregarCancionALista(cancion1, lista1);
        usuario1.agregarCancionALista(cancion2, lista1);
        lista1.calcularCalificacion();

        // Composición: UsuarioFinal - ColaReproduccion
        usuario1.agregarCancionACola(cancion1);
        usuario1.agregarCancionACola(cancion2);

        // Reproducción de canciones
        cancion1.reproducir();
        cancion2.reproducir();

        // Mostrar información
        System.out.println("\n---------- ADMINISTRADOR ----------");
        System.out.println(administrador);
        System.out.println("Cola del administrador: " + administrador.getColaReproduccion());

        System.out.println("\n---------- ÁLBUM ----------");
        System.out.println(album1);

        System.out.println("\n---------- CANCIONES ----------");
        System.out.println(cancion1);
        System.out.println();
        System.out.println(cancion2);

        System.out.println("\n---------- USUARIO FINAL ----------");
        System.out.println(usuario1);
        System.out.println("Cola del usuario: " + usuario1.getColaReproduccion());

        System.out.println("\n---------- LISTA DE REPRODUCCIÓN ----------");
        System.out.println(lista1);

        // Prueba de eliminación en cola
        System.out.println("\n---------- PRUEBA DE COLA ----------");
        usuario1.getColaReproduccion().eliminarCancion();
        System.out.println("Cola del usuario después de eliminar: " + usuario1.getColaReproduccion());
    }
}
