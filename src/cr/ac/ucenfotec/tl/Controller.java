package cr.ac.ucenfotec.tl;

import cr.ac.ucenfotec.bl.entities.administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.listaReproduccion.ListaReproduccion;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.CredencialesInvalidasException;
import cr.ac.ucenfotec.bl.logic.GestorAdministrador;
import cr.ac.ucenfotec.bl.logic.GestorCancion;
import cr.ac.ucenfotec.bl.logic.GestorListaReproduccion;
import cr.ac.ucenfotec.bl.logic.GestorUsuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/*
 Capa de integración (patrón MVC): coordina los Gestores y mantiene el
 estado de la sesión activa. El Menu (ui) solo habla con este Controller,
 nunca directamente con los Gestores ni con los DAO.
 */
public class Controller {

    private final GestorUsuario gestorUsuario;
    private final GestorAdministrador gestorAdministrador;
    private final GestorListaReproduccion gestorListaReproduccion;
    private final GestorCancion gestorCancion;

    /*Caché en memoria del catálogo completo, usada por las playlists y
    los Top 3. Se refresca con refrescarCatalogo().*/
    private final ArrayList<Cancion> catalogoCompleto;

    private Usuario usuarioActual;

    public Controller() {
        this.gestorUsuario = new GestorUsuario();
        this.gestorAdministrador = new GestorAdministrador();
        this.gestorListaReproduccion = new GestorListaReproduccion();
        this.gestorCancion = new GestorCancion();
        this.catalogoCompleto = new ArrayList<>();
        this.usuarioActual = null;
    }

    // =========================================================
    // SESIÓN
    // =========================================================

    public Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public UsuarioFinal getUsuarioFinalActual() {

        if (usuarioActual instanceof UsuarioFinal usuarioFinal) {
            return usuarioFinal;
        }

        return null;
    }

    public boolean haySesionActiva() {
        return usuarioActual != null;
    }

    public void cerrarSesion() {
        usuarioActual = null;
    }

    // =========================================================
    // ADMINISTRADOR
    // =========================================================

    public boolean existeAdministrador() throws Exception {
        return gestorAdministrador.existeAdministrador();
    }

    public void registrarAdministrador(
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia) throws Exception {

        gestorAdministrador.registrarAdministrador(
                correoElectronico,
                nombreUsuario,
                contrasenia,
                confirmarContrasenia
        );
    }

    public void iniciarSesionAdministrador(
            String nombreUsuario, String contrasenia) throws Exception {

        Administrador administrador =
                gestorAdministrador.iniciarSesion(nombreUsuario, contrasenia);

        this.usuarioActual = administrador;
        refrescarCatalogo();
    }

    public void cambiarContraseniaAdministrador(
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia) throws Exception {

        if (!(usuarioActual instanceof Administrador administrador)) {
            throw new CredencialesInvalidasException(
                    "No hay un administrador autenticado."
            );
        }

        gestorAdministrador.cambiarContrasenia(
                administrador,
                contraseniaActual,
                nuevaContrasenia,
                confirmarContrasenia
        );
    }

    // =========================================================
    // USUARIO FINAL
    // =========================================================

    public List<String> getNacionalidadesPermitidas() {
        return gestorUsuario.getNacionalidadesPermitidas();
    }

    public void registrarUsuario(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia) throws Exception {

        gestorUsuario.registrarUsuario(
                nombreCompleto,
                fechaNacimiento,
                nacionalidad,
                cedula,
                avatar,
                correoElectronico,
                nombreUsuario,
                contrasenia,
                confirmarContrasenia
        );
    }

    public void iniciarSesionUsuarioFinal(
            String nombreUsuario, String contrasenia) throws Exception {

        UsuarioFinal usuarioFinal =
                gestorUsuario.iniciarSesion(nombreUsuario, contrasenia);

        /* El catálogo se refresca antes de reconstruir las playlists,
        para que puedan emparejar sus canciones contra él.*/
        refrescarCatalogo();

        gestorListaReproduccion.cargarListasDesdeBD(
                usuarioFinal, catalogoCompleto
        );

        this.usuarioActual = usuarioFinal;
    }

    public void cambiarContraseniaUsuarioFinal(
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia) throws Exception {

        UsuarioFinal usuarioFinal = getUsuarioFinalActual();

        gestorUsuario.cambiarContrasenia(
                usuarioFinal,
                contraseniaActual,
                nuevaContrasenia,
                confirmarContrasenia
        );
    }

    public void recargarSaldo(float monto) throws Exception {
        gestorUsuario.recargarSaldo(getUsuarioFinalActual(), monto);
    }

    // =========================================================
    // LISTAS DE REPRODUCCIÓN / COLA / TOP 3
    // =========================================================

    public ListaReproduccion crearListaReproduccion(String nombre) throws Exception {
        return gestorListaReproduccion.crearListaReproduccion(
                getUsuarioFinalActual(), nombre
        );
    }

    public ListaReproduccion[] listarListasDelUsuario() {
        return gestorListaReproduccion.listarListasDelUsuario(
                getUsuarioFinalActual()
        );
    }

    public ArrayList<ListaReproduccion> buscarListasPorNombre(String nombre) {
        return gestorListaReproduccion.buscarListasPorNombre(
                getUsuarioFinalActual(), nombre
        );
    }

    public void agregarCancionALista(
            ListaReproduccion lista, Cancion cancion) throws Exception {

        gestorListaReproduccion.agregarCancionALista(
                getUsuarioFinalActual(), lista, cancion
        );
    }

    public void eliminarCancionDeLista(
            ListaReproduccion lista, Cancion cancion) throws Exception {

        gestorListaReproduccion.eliminarCancionDeLista(
                getUsuarioFinalActual(), lista, cancion
        );
    }

    public void reproducirCancion(Cancion cancion) throws Exception {
        gestorListaReproduccion.reproducirCancion(usuarioActual, cancion);
    }

    public void reproducirVistaPrevia(Cancion cancion) {
        gestorListaReproduccion.reproducirVistaPrevia(cancion);
    }

    public void reproducirLista(ListaReproduccion lista) throws Exception {
        gestorListaReproduccion.reproducirLista(usuarioActual, lista);
    }

    public void agregarCancionACola(Cancion cancion) {
        gestorListaReproduccion.agregarCancionACola(usuarioActual, cancion);
    }

    public void agregarListaACola(ListaReproduccion lista) throws Exception {
        gestorListaReproduccion.agregarListaACola(usuarioActual, lista);
    }

    public void avanzarCola() {
        gestorListaReproduccion.avanzarCola(usuarioActual);
    }

    public ArrayList<Cancion> obtenerTop3MejorCalificadas() {
        return gestorListaReproduccion.obtenerTop3MejorCalificadas(catalogoCompleto);
    }

    public ArrayList<Cancion> obtenerTop3MasCompradas() {
        return gestorListaReproduccion.obtenerTop3MasCompradas(catalogoCompleto);
    }

    public ArrayList<Cancion> obtenerTop3MasAgregadasAListas() {
        return gestorListaReproduccion.obtenerTop3MasAgregadasAListas(catalogoCompleto);
    }

    // =========================================================
    // CATÁLOGO
    // =========================================================

    public void refrescarCatalogo() throws Exception {
        catalogoCompleto.clear();
        catalogoCompleto.addAll(gestorCancion.mostrarCatalogo());
    }

    public ArrayList<Cancion> getCatalogoCompleto() {
        return catalogoCompleto;
    }

    public Cancion registrarCancion(
            String nombre,
            String genero,
            LocalDate fechaLanzamiento,
            float precio,
            String artista,
            String compositor,
            String nombreAlbum,
            String caratulaAlbum) throws Exception {

        Cancion cancion = gestorCancion.registrarCancion(
                nombre,
                genero,
                fechaLanzamiento,
                precio,
                artista,
                compositor,
                nombreAlbum,
                caratulaAlbum
        );

        refrescarCatalogo();

        return cancion;
    }

    public ArrayList<Cancion> buscarCancionesPorNombre(String nombre) throws Exception {
        return gestorCancion.buscarPorNombre(nombre);
    }

    public ArrayList<Cancion> buscarCancionesPorGenero(String genero) throws Exception {
        return gestorCancion.buscarPorGenero(genero);
    }

    public ArrayList<Cancion> buscarCancionesPorArtista(String artista) throws Exception {
        return gestorCancion.buscarPorArtista(artista);
    }

    public void comprarCancion(Cancion cancion) throws Exception {
        gestorCancion.comprarCancion(getUsuarioFinalActual(), cancion);
    }

    public float calificarCancion(
            Cancion cancion, float calificacion) throws Exception {

        return gestorCancion.calificarCancion(
                getUsuarioFinalActual(), cancion, calificacion
        );
    }
}