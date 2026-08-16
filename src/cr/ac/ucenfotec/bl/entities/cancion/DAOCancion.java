package cr.ac.ucenfotec.bl.entities.cancion;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOCancion {

    // =========================================================
    // REGISTRAR CANCIÓN
    // =========================================================

    public void insertar(Cancion cancion) throws Exception {

        String insertarBase =
                "INSERT INTO t_canciones (nombre, genero, artista) " +
                        "VALUES (?, ?, ?)";

        Connector.getConnection().ejecutarStatement(
                insertarBase,
                cancion.getNombre(),
                cancion.getGenero(),
                cancion.getArtista()
        );

        ResultSet idGenerado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT LAST_INSERT_ID() AS id"
                );

        if (!idGenerado.next()) {
            throw new IllegalStateException(
                    "No fue posible recuperar el id de la canción recién registrada."
            );
        }

        int id = idGenerado.getInt("id");

        String completarDatosAlbum =
                "UPDATE t_canciones " +
                        "SET compositor = ?, nombre_album = ?, caratula_album = ? " +
                        "WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarDatosAlbum,
                cancion.getCompositor(),
                cancion.getNombreAlbum(),
                cancion.getCaratulaAlbum()
        );

        String completarPrecio =
                "UPDATE t_canciones SET precio = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarPrecio,
                cancion.getPrecio()
        );

        String completarFecha =
                "UPDATE t_canciones SET fecha_lanzamiento = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarFecha,
                cancion.getFechaLanzamiento()
        );

        cancion.setId(id);
    }

    // =========================================================
    // CATÁLOGO Y BÚSQUEDAS
    // =========================================================

    public Cancion buscarPorId(int id) throws Exception {

        String query = "SELECT * FROM t_canciones WHERE id = ? LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, id);

        if (resultado.next()) {
            return convertir(resultado);
        }

        return null;
    }

    public ArrayList<Cancion> listarTodos() throws Exception {

        ArrayList<Cancion> canciones = new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT * FROM t_canciones ORDER BY id"
                );

        while (resultado.next()) {
            canciones.add(convertir(resultado));
        }

        return canciones;
    }

    public ArrayList<Cancion> buscarPorNombre(String nombre) throws Exception {

        String query =
                "SELECT * FROM t_canciones WHERE nombre LIKE ? ORDER BY nombre";

        ArrayList<Cancion> canciones = new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        "%" + nombre + "%"
                );

        while (resultado.next()) {
            canciones.add(convertir(resultado));
        }

        return canciones;
    }

    public ArrayList<Cancion> buscarPorGenero(String genero) throws Exception {

        String query =
                "SELECT * FROM t_canciones WHERE genero LIKE ? ORDER BY nombre";

        ArrayList<Cancion> canciones = new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        "%" + genero + "%"
                );

        while (resultado.next()) {
            canciones.add(convertir(resultado));
        }

        return canciones;
    }

    public ArrayList<Cancion> buscarPorArtista(String artista) throws Exception {

        String query =
                "SELECT * FROM t_canciones WHERE artista LIKE ? ORDER BY nombre";

        ArrayList<Cancion> canciones = new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        "%" + artista + "%"
                );

        while (resultado.next()) {
            canciones.add(convertir(resultado));
        }

        return canciones;
    }

    // =========================================================
    // COMPRAS
    // =========================================================

    public boolean existeCompra(int idUsuario, int idCancion) throws Exception {

        String query =
                "SELECT * FROM t_compras " +
                        "WHERE id_usuario = ? AND id_cancion = ? LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        idUsuario,
                        idCancion
                );

        return resultado.next();
    }

    public void registrarCompra(int idUsuario, int idCancion) throws Exception {

        String query =
                "INSERT INTO t_compras (id_usuario, id_cancion) VALUES (?, ?)";

        Connector.getConnection().ejecutarStatement(
                query,
                idUsuario,
                idCancion
        );
    }

    // =========================================================
    // CALIFICACIONES
    // =========================================================

    public void registrarCalificacion(
            int idUsuario,
            int idCancion,
            float calificacion
    ) throws Exception {

        // DBAccess no tiene un overload (int, int, float), así que los
        // ids se concatenan directo en el query (mismo patrón que usa
        // DAOUsuario con "WHERE id = " + id) y solo la calificación
        // queda como parámetro preparado.
        String query =
                "INSERT INTO t_calificaciones (id_usuario, id_cancion, calificacion) " +
                        "VALUES (" + idUsuario + ", " + idCancion + ", ?)";

        Connector.getConnection().ejecutarStatement(query, calificacion);
    }

    public float obtenerPromedioCalificacion(int idCancion) throws Exception {

        String query =
                "SELECT AVG(calificacion) AS promedio FROM t_calificaciones " +
                        "WHERE id_cancion = ?";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, idCancion);

        if (resultado.next()) {
            return resultado.getFloat("promedio");
        }

        return 0f;
    }

    public int contarCalificaciones(int idCancion) throws Exception {

        String query =
                "SELECT COUNT(*) AS total FROM t_calificaciones " +
                        "WHERE id_cancion = ?";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(query, idCancion);

        if (resultado.next()) {
            return resultado.getInt("total");
        }

        return 0;
    }

    // =========================================================
    // CONVERSIÓN DE RESULTSET A CANCION
    // =========================================================

    private Cancion convertir(ResultSet resultado) throws Exception {

        int id = resultado.getInt("id");

        java.sql.Date fechaSQL = resultado.getDate("fecha_lanzamiento");

        LocalDate fechaLanzamiento =
                fechaSQL == null ? null : fechaSQL.toLocalDate();

        // La calificación no vive en t_canciones, así que se calcula
        // en tiempo real a partir de t_calificaciones.
        Cancion cancion = new Cancion(
                resultado.getString("nombre"),
                resultado.getString("genero"),
                fechaLanzamiento,
                resultado.getFloat("precio"),
                obtenerPromedioCalificacion(id),
                resultado.getString("artista"),
                resultado.getString("compositor"),
                resultado.getString("nombre_album"),
                resultado.getString("caratula_album")
        );

        cancion.setId(id);

        return cancion;
    }
}