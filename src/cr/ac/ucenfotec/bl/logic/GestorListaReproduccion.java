package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.Reproductor;
import cr.ac.ucenfotec.bl.entities.administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.DAOListaReproduccion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.CancionNoCompradaException;
import cr.ac.ucenfotec.bl.exceptions.ListaNoEncontradaException;

import java.util.ArrayList;
import java.util.Comparator;

public class GestorListaReproduccion {

    private final DAOListaReproduccion daoListaReproduccion;
    private final Reproductor reproductor;

    public GestorListaReproduccion() {
        this.daoListaReproduccion = new DAOListaReproduccion();
        this.reproductor = new Reproductor();
    }

    // =========================================================
    // CREAR LISTA DE REPRODUCCIÓN
    // =========================================================
    public ListaReproduccion crearListaReproduccion(
            UsuarioFinal usuario, String nombre) throws Exception {

        if (usuario == null) {
            throw new ListaNoEncontradaException(
                    "No hay un usuario autenticado."
            );
        }

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre de la lista no puede estar vacío."
            );
        }

        int cantidadAntes = usuario.getCantidadListasReproduccion();

        // La creación y el control de capacidad del arreglo ya están
        // implementados en UsuarioFinal; el gestor se encarga de
        // persistir la lista resultante.
        usuario.crearListaReproduccion(nombre.trim());

        if (usuario.getCantidadListasReproduccion() == cantidadAntes) {
            throw new IllegalStateException(
                    "No se pudo crear la lista de reproducción."
            );
        }

        ListaReproduccion nuevaLista =
                usuario.getListasReproduccion()[cantidadAntes];

        if (usuario.getId() > 0) {
            daoListaReproduccion.insertar(usuario.getId(), nuevaLista);
        }

        return nuevaLista;
    }

    // =========================================================
    // CARGAR LAS LISTAS DE UN USUARIO DESDE LA BASE DE DATOS
    // =========================================================
    // Se llama justo después del login para reconstruir las listas del
    // usuario y sus canciones a partir del catálogo ya cargado por
    // GestorCancion. No usa agregarCancion() para no inflar contadores
    // de estadísticas (vecesAgregadaAListas) en cada inicio de sesión.
    public void cargarListasDesdeBD(
            UsuarioFinal usuario,
            ArrayList<Cancion> catalogoCompleto) throws Exception {

        if (usuario == null || usuario.getId() <= 0) {
            return;
        }

        ArrayList<ListaReproduccion> listasGuardadas =
                daoListaReproduccion.listarPorUsuario(usuario.getId());

        for (ListaReproduccion listaGuardada : listasGuardadas) {

            usuario.crearListaReproduccion(listaGuardada.getNombre());

            ListaReproduccion listaEnMemoria =
                    usuario.getListasReproduccion()[
                            usuario.getCantidadListasReproduccion() - 1
                            ];

            listaEnMemoria.setId(listaGuardada.getId());
            listaEnMemoria.setFechaCreacion(listaGuardada.getFechaCreacion());

            ArrayList<Integer> idsCanciones =
                    daoListaReproduccion.buscarIdsCancionesDeLista(
                            listaGuardada.getId()
                    );

            for (int idCancion : idsCanciones) {

                Cancion cancion =
                        buscarEnCatalogoPorId(catalogoCompleto, idCancion);

                if (cancion != null) {
                    listaEnMemoria.agregarCancionSinContar(cancion);
                }
            }
        }
    }

    // Carga todas las listas del sistema para que el administrador
    // pueda consultarlas y reproducirlas libremente.
    public ArrayList<ListaReproduccion> listarTodasLasListas(
            ArrayList<Cancion> catalogoCompleto) throws Exception {

        ArrayList<ListaReproduccion> listas = daoListaReproduccion.listarTodas();

        for (ListaReproduccion lista : listas) {

            ArrayList<Integer> idsCanciones =
                    daoListaReproduccion.buscarIdsCancionesDeLista(lista.getId());

            for (int idCancion : idsCanciones) {
                Cancion cancion = buscarEnCatalogoPorId(catalogoCompleto, idCancion);

                if (cancion != null) {
                    lista.agregarCancionSinContar(cancion);
                }
            }
        }

        return listas;
    }

    private Cancion buscarEnCatalogoPorId(
            ArrayList<Cancion> catalogo, int idCancion) {

        for (Cancion cancion : catalogo) {
            if (cancion.getId() == idCancion) {
                return cancion;
            }
        }

        return null;
    }

    // =========================================================
    // BUSCAR LISTAS (siempre dentro de las del propio usuario)
    // =========================================================
    public ListaReproduccion[] listarListasDelUsuario(UsuarioFinal usuario) {

        if (usuario == null) {
            return new ListaReproduccion[0];
        }

        return usuario.getListasReproduccion();
    }

    public ArrayList<ListaReproduccion> buscarListasPorNombre(
            UsuarioFinal usuario, String nombre) {

        ArrayList<ListaReproduccion> coincidencias = new ArrayList<>();

        if (usuario == null || nombre == null) {
            return coincidencias;
        }

        String nombreBuscado = nombre.trim().toLowerCase();

        for (int i = 0; i < usuario.getCantidadListasReproduccion(); i++) {

            ListaReproduccion lista = usuario.getListasReproduccion()[i];

            if (lista.getNombre() != null &&
                    lista.getNombre().toLowerCase().contains(nombreBuscado)) {

                coincidencias.add(lista);
            }
        }

        return coincidencias;
    }

    // =========================================================
    // AGREGAR / ELIMINAR CANCIONES DE UNA LISTA
    // =========================================================
    public void agregarCancionALista(
            UsuarioFinal usuario,
            ListaReproduccion lista,
            Cancion cancion) throws Exception {

        validarPropiedadDeLista(usuario, lista);

        if (cancion == null) {
            throw new IllegalArgumentException("La canción no existe.");
        }

        if (!usuario.tieneCancionComprada(cancion)) {
            throw new CancionNoCompradaException(
                    "Solo se pueden agregar a una lista canciones " +
                            "que ya hayan sido compradas."
            );
        }

        lista.agregarCancion(cancion);

        if (lista.getId() > 0) {
            daoListaReproduccion.agregarCancion(lista.getId(), cancion.getId());
        }
    }

    public void eliminarCancionDeLista(
            UsuarioFinal usuario,
            ListaReproduccion lista,
            Cancion cancion) throws Exception {

        validarPropiedadDeLista(usuario, lista);

        if (cancion == null) {
            throw new IllegalArgumentException("La canción no existe.");
        }

        boolean eliminada = lista.eliminarCancion(cancion);

        if (eliminada && lista.getId() > 0) {
            daoListaReproduccion.eliminarCancion(lista.getId(), cancion.getId());
        }
    }

    private void validarPropiedadDeLista(
            UsuarioFinal usuario, ListaReproduccion lista) {

        if (usuario == null) {
            throw new ListaNoEncontradaException(
                    "No hay un usuario autenticado."
            );
        }

        if (lista == null || !usuario.tieneListaReproduccion(lista)) {
            throw new ListaNoEncontradaException(
                    "La lista de reproducción no existe o no pertenece " +
                            "a este usuario."
            );
        }
    }

    // =========================================================
    // REPRODUCCIÓN
    // =========================================================
    public void reproducirCancion(
            Usuario usuario, Cancion cancion) throws Exception {

        if (cancion == null) {
            throw new IllegalArgumentException("La canción no existe.");
        }

        if (!puedeReproducirLibremente(usuario, cancion)) {
            throw new CancionNoCompradaException(
                    "Debe comprar la canción para escucharla completa. " +
                            "Puede usar la vista previa de 30 segundos."
            );
        }

        reproductor.reproducirCancion(cancion);
    }

    public void reproducirVistaPrevia(Cancion cancion) {
        reproductor.reproducirVistaPrevia(cancion);
    }

    public void reproducirLista(
            Usuario usuario, ListaReproduccion lista) throws Exception {

        if (lista == null) {
            throw new ListaNoEncontradaException(
                    "La lista de reproducción no existe."
            );
        }

        // El administrador puede reproducir cualquier lista de
        // cualquier usuario; un usuario final solo las propias.
        if (usuario instanceof UsuarioFinal usuarioFinal &&
                !usuarioFinal.tieneListaReproduccion(lista)) {

            throw new ListaNoEncontradaException(
                    "La lista de reproducción no pertenece a este usuario."
            );
        }

        reproductor.reproducirLista(lista);
    }

    private boolean puedeReproducirLibremente(
            Usuario usuario, Cancion cancion) {

        if (usuario instanceof Administrador) {
            return true;
        }

        return usuario instanceof UsuarioFinal usuarioFinal &&
                usuarioFinal.tieneCancionComprada(cancion);
    }

    // =========================================================
    // COLA DE REPRODUCCIÓN DINÁMICA
    // =========================================================
    public void agregarCancionACola(
            Usuario usuario, Cancion cancion) {

        if (usuario instanceof UsuarioFinal usuarioFinal) {
            usuarioFinal.agregarCancionACola(cancion);

        } else if (usuario instanceof Administrador administrador) {
            administrador.agregarCancionACola(cancion);
        }
    }

    public void agregarListaACola(
            Usuario usuario, ListaReproduccion lista) throws Exception {

        if (lista == null) {
            throw new ListaNoEncontradaException(
                    "La lista de reproducción no existe."
            );
        }

        for (Cancion cancion : lista.getCanciones()) {
            agregarCancionACola(usuario, cancion);
        }
    }

    public void avanzarCola(Usuario usuario) {

        if (usuario == null) {
            return;
        }

        usuario.getColaReproduccion().reproducirSiguiente();
    }

    // =========================================================
    // TOP 3 (se recalculan cada vez que se consultan)
    // =========================================================
    public ArrayList<Cancion> obtenerTop3MejorCalificadas(
            ArrayList<Cancion> catalogoCompleto) {

        return obtenerTop3(
                catalogoCompleto,
                Comparator.comparingDouble(Cancion::getCalificacion).reversed()
        );
    }

    public ArrayList<Cancion> obtenerTop3MasCompradas(
            ArrayList<Cancion> catalogoCompleto) {

        return obtenerTop3(
                catalogoCompleto,
                Comparator.comparingInt(Cancion::getVecesComprada).reversed()
        );
    }

    public ArrayList<Cancion> obtenerTop3MasAgregadasAListas(
            ArrayList<Cancion> catalogoCompleto) {

        return obtenerTop3(
                catalogoCompleto,
                Comparator.comparingInt(Cancion::getVecesAgregadaAListas).reversed()
        );
    }

    private ArrayList<Cancion> obtenerTop3(
            ArrayList<Cancion> catalogoCompleto,
            Comparator<Cancion> criterio) {

        ArrayList<Cancion> top3 = new ArrayList<>();

        if (catalogoCompleto == null || catalogoCompleto.isEmpty()) {
            return top3;
        }

        ArrayList<Cancion> copia = new ArrayList<>(catalogoCompleto);
        copia.sort(criterio);

        int limite = Math.min(3, copia.size());

        for (int i = 0; i < limite; i++) {
            top3.add(copia.get(i));
        }

        return top3;
    }
}