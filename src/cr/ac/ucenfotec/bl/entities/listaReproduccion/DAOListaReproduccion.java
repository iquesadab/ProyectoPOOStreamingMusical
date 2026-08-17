package cr.ac.ucenfotec.bl.entities.listaReproduccion;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOListaReproduccion {

    /* =========================================================
    INSERTAR LISTA DE REPRODUCCIÓN
    =========================================================
    DBAccess solo permite parámetros del mismo tipo por llamada, así
    que se inserta la fila base con id_usuario y se completa el resto
    con actualizaciones sucesivas, igual que hace DAOUsuario.insertar(). */
    public void insertar(int idUsuario, ListaReproduccion lista) throws Exception {

        String insertarBase =
                "INSERT INTO t_listas_reproduccion (id_usuario) VALUES (?)";

        Connector.getConnection().ejecutarStatement(
                insertarBase,
                idUsuario
        );

        int id = obtenerUltimoIdInsertado(idUsuario);

        String completarNombre =
                "UPDATE t_listas_reproduccion SET nombre = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarNombre,
                lista.getNombre()
        );

        String completarFecha =
                "UPDATE t_listas_reproduccion SET fecha_creacion = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarFecha,
                lista.getFechaCreacion()
        );

        lista.setId(id);
    }

    // Busca el id de la última lista insertada para ese usuario.
    private int obtenerUltimoIdInsertado(int idUsuario) throws Exception {

        String query =
                "SELECT id FROM t_listas_reproduccion " +
                        "WHERE id_usuario = ? ORDER BY id DESC LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, idUsuario);

        if (resultado.next()) {
            return resultado.getInt("id");
        }

        throw new IllegalStateException(
                "No fue posible recuperar la lista recién creada."
        );
    }

    // =========================================================
    // LISTAR LISTAS DE UN USUARIO
    // =========================================================
    public ArrayList<ListaReproduccion> listarPorUsuario(int idUsuario) throws Exception {

        ArrayList<ListaReproduccion> listas = new ArrayList<>();

        String query =
                "SELECT * FROM t_listas_reproduccion " +
                        "WHERE id_usuario = ? ORDER BY id";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, idUsuario);

        while (resultado.next()) {
            listas.add(convertir(resultado));
        }

        return listas;
    }

    // Lista todas las playlists del sistema. Se utiliza únicamente
    // para las funciones especiales del administrador.
    public ArrayList<ListaReproduccion> listarTodas() throws Exception {

        ArrayList<ListaReproduccion> listas = new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT * FROM t_listas_reproduccion ORDER BY id"
                );

        while (resultado.next()) {
            listas.add(convertir(resultado));
        }

        return listas;
    }

    /* =========================================================
    BUSCAR LISTAS POR NOMBRE (dentro de las listas del usuario)
    =========================================================
    DBAccess no tiene un overload de (int, String) en un mismo query,
    así que se traen las listas del usuario y se filtra en memoria. */
    public ArrayList<ListaReproduccion> buscarPorNombre(
            int idUsuario, String nombre) throws Exception {

        ArrayList<ListaReproduccion> listas = listarPorUsuario(idUsuario);
        ArrayList<ListaReproduccion> coincidencias = new ArrayList<>();

        if (nombre == null) {
            return coincidencias;
        }

        String nombreBuscado = nombre.trim().toLowerCase();

        for (ListaReproduccion lista : listas) {
            if (lista.getNombre() != null &&
                    lista.getNombre().toLowerCase().contains(nombreBuscado)) {

                coincidencias.add(lista);
            }
        }

        return coincidencias;
    }

    // =========================================================
    // RELACIÓN LISTA-CANCIÓN (t_canciones_listas)
    // =========================================================

    public void agregarCancion(int idLista, int idCancion) throws Exception {

        String query =
                "INSERT INTO t_canciones_listas (id_lista, id_cancion) " +
                        "VALUES (?, ?)";

        Connector.getConnection().ejecutarStatement(
                query,
                idLista,
                idCancion
        );
    }

    public void eliminarCancion(int idLista, int idCancion) throws Exception {

        String query =
                "DELETE FROM t_canciones_listas " +
                        "WHERE id_lista = ? AND id_cancion = ?";

        Connector.getConnection().ejecutarStatement(
                query,
                idLista,
                idCancion
        );
    }

    public boolean existeRelacion(int idLista, int idCancion) throws Exception {

        String query =
                "SELECT id FROM t_canciones_listas " +
                        "WHERE id_lista = ? AND id_cancion = ?";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query, idLista, idCancion
                );

        return resultado.next();
    }

    /* Devuelve solo los ids de las canciones de una lista. La hidratación a
    objetos Cancion completos se hace en el Gestor a partir del catálogo
    que provee GestorCancion, para no duplicar la lógica de DAOCancion.*/
    public ArrayList<Integer> buscarIdsCancionesDeLista(int idLista) throws Exception {

        ArrayList<Integer> ids = new ArrayList<>();

        String query =
                "SELECT id_cancion FROM t_canciones_listas " +
                        "WHERE id_lista = ? ORDER BY id";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, idLista);

        while (resultado.next()) {
            ids.add(resultado.getInt("id_cancion"));
        }

        return ids;
    }

    // =========================================================
    // CONVERSIÓN DE ResultSet A ListaReproduccion
    // =========================================================
    private ListaReproduccion convertir(ResultSet resultado) throws Exception {

        java.sql.Date fechaSQL = resultado.getDate("fecha_creacion");

        LocalDate fechaCreacion =
                fechaSQL == null ? null : fechaSQL.toLocalDate();

        return new ListaReproduccion(
                resultado.getInt("id"),
                resultado.getString("nombre"),
                fechaCreacion
        );
    }
}