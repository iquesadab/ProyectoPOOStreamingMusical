package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.cancion.Cancion;
import cr.ac.ucenfotec.bl.entities.cancion.DAOCancion;
import cr.ac.ucenfotec.bl.entities.usuario.DAOUsuario;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.SaldoInvalidoException;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorCancion {

    private final DAOCancion daoCancion;
    private final DAOUsuario daoUsuario;

    public GestorCancion() {
        this.daoCancion = new DAOCancion();
        this.daoUsuario = new DAOUsuario();
    }

    // =========================================================
    // REGISTRAR CANCIÓN
    // =========================================================

    public Cancion registrarCancion(
            String nombre,
            String genero,
            LocalDate fechaLanzamiento,
            float precio,
            String artista,
            String compositor,
            String nombreAlbum,
            String caratulaAlbum
    ) throws Exception {

        validarDatosCancion(
                nombre,
                genero,
                fechaLanzamiento,
                precio,
                artista
        );

        Cancion cancion = new Cancion(
                nombre.trim(),
                genero.trim(),
                fechaLanzamiento,
                precio,
                0f,
                artista.trim(),
                compositor,
                nombreAlbum,
                caratulaAlbum
        );

        daoCancion.insertar(cancion);

        return cancion;
    }

    // =========================================================
    // MOSTRAR CATÁLOGO
    // =========================================================

    public ArrayList<Cancion> mostrarCatalogo() throws Exception {
        return daoCancion.listarTodos();
    }

    public Cancion buscarPorId(int id) throws Exception {
        return daoCancion.buscarPorId(id);
    }

    // =========================================================
    // BÚSQUEDAS
    // =========================================================

    public ArrayList<Cancion> buscarPorNombre(String nombre) throws Exception {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre a buscar no puede estar vacío."
            );
        }

        return daoCancion.buscarPorNombre(nombre.trim());
    }

    public ArrayList<Cancion> buscarPorGenero(String genero) throws Exception {

        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException(
                    "El género a buscar no puede estar vacío."
            );
        }

        return daoCancion.buscarPorGenero(genero.trim());
    }

    public ArrayList<Cancion> buscarPorArtista(String artista) throws Exception {

        if (artista == null || artista.isBlank()) {
            throw new IllegalArgumentException(
                    "El artista a buscar no puede estar vacío."
            );
        }

        return daoCancion.buscarPorArtista(artista.trim());
    }

    // =========================================================
    // COMPRAR CANCIÓN
    // =========================================================

    public void comprarCancion(
            UsuarioFinal usuario,
            Cancion cancion
    ) throws Exception {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "No hay un usuario autenticado."
            );
        }

        if (cancion == null) {
            throw new IllegalArgumentException(
                    "La canción no existe."
            );
        }

        if (daoCancion.existeCompra(usuario.getId(), cancion.getId())) {
            throw new IllegalStateException(
                    "La canción ya fue comprada anteriormente."
            );
        }

        validarSaldoSuficiente(usuario, cancion);

        float nuevoSaldo =
                Math.round(
                        (usuario.getSaldo() - cancion.getPrecio()) * 100f
                ) / 100f;

        usuario.setSaldo(nuevoSaldo);
        daoUsuario.actualizarSaldo(usuario);

        daoCancion.registrarCompra(usuario.getId(), cancion.getId());

        cancion.aumentarVecesComprada();
    }

    // =========================================================
    // VALIDAR SALDO SUFICIENTE
    // =========================================================

    public void validarSaldoSuficiente(
            UsuarioFinal usuario,
            Cancion cancion
    ) {

        if (usuario.getSaldo() < cancion.getPrecio()) {
            throw new SaldoInvalidoException(
                    "Saldo insuficiente para comprar la canción."
            );
        }
    }

    // =========================================================
    // CALIFICAR CANCIÓN
    // =========================================================

    public float calificarCancion(
            UsuarioFinal usuario,
            Cancion cancion,
            float calificacion
    ) throws Exception {

        if (usuario == null) {
            throw new IllegalArgumentException(
                    "No hay un usuario autenticado."
            );
        }

        if (cancion == null) {
            throw new IllegalArgumentException(
                    "La canción no existe."
            );
        }

        if (calificacion < 0.0f || calificacion > 5.0f) {
            throw new IllegalArgumentException(
                    "La calificación debe estar entre 0.0 y 5.0."
            );
        }

        if (!daoCancion.existeCompra(usuario.getId(), cancion.getId())) {
            throw new IllegalStateException(
                    "Solo se pueden calificar canciones compradas."
            );
        }

        daoCancion.registrarCalificacion(
                usuario.getId(),
                cancion.getId(),
                calificacion
        );

        float promedio =
                daoCancion.obtenerPromedioCalificacion(cancion.getId());

        cancion.setCalificacion(promedio);

        return promedio;
    }

    // =========================================================
    // CALCULAR PROMEDIO DE CALIFICACIONES
    // =========================================================

    public float calcularPromedioCalificaciones(Cancion cancion) throws Exception {

        if (cancion == null) {
            throw new IllegalArgumentException(
                    "La canción no existe."
            );
        }

        float promedio =
                daoCancion.obtenerPromedioCalificacion(cancion.getId());

        cancion.setCalificacion(promedio);

        return promedio;
    }

    // =========================================================
    // VALIDACIONES DE REGISTRO
    // =========================================================

    private void validarDatosCancion(
            String nombre,
            String genero,
            LocalDate fechaLanzamiento,
            float precio,
            String artista
    ) {

        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre de la canción no puede estar vacío."
            );
        }

        if (genero == null || genero.isBlank()) {
            throw new IllegalArgumentException(
                    "El género no puede estar vacío."
            );
        }

        if (artista == null || artista.isBlank()) {
            throw new IllegalArgumentException(
                    "El artista no puede estar vacío."
            );
        }

        if (fechaLanzamiento == null ||
                fechaLanzamiento.isAfter(LocalDate.now())) {

            throw new IllegalArgumentException(
                    "La fecha de lanzamiento no es válida."
            );
        }

        if (precio <= 0) {
            throw new IllegalArgumentException(
                    "El precio debe ser mayor que cero."
            );
        }
    }
}