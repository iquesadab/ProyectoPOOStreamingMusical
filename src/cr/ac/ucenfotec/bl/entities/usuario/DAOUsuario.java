package cr.ac.ucenfotec.bl.entities.usuario;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOUsuario {

    private static final byte MAX_CANCIONES = 50;
    private static final byte MAX_LISTAS = 20;

    public void insertar(UsuarioFinal usuario) throws Exception {

        String insertarBase =
                "INSERT INTO t_usuarios " +
                        "(correo_electronico, nombre_usuario, contrasenia) " +
                        "VALUES (?, ?, ?)";

        Connector.getConnection().ejecutarStatement(
                insertarBase,
                usuario.getCorreoElectronico(),
                usuario.getNombreUsuario(),
                usuario.getContrasenia()
        );

        UsuarioFinal baseGuardada =
                buscarPorNombreUsuario(
                        usuario.getNombreUsuario()
                );

        if (baseGuardada == null) {
            throw new IllegalStateException(
                    "No fue posible recuperar el usuario recién registrado."
            );
        }

        int id = baseGuardada.getId();

        String completarDatosPersonales =
                "UPDATE t_usuarios " +
                        "SET nombre_completo = ?, nacionalidad = ?, cedula = ? " +
                        "WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarDatosPersonales,
                usuario.getNombreCompleto(),
                usuario.getNacionalidad(),
                usuario.getCedula()
        );

        String completarFechaAvatar =
                "UPDATE t_usuarios " +
                        "SET fecha_nacimiento = STR_TO_DATE(?, '%Y-%m-%d'), avatar = ? " +
                        "WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarFechaAvatar,
                usuario.getFechaNacimiento().toString(),
                usuario.getAvatar()
        );

        String completarSaldo =
                "UPDATE t_usuarios SET saldo = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                completarSaldo,
                usuario.getSaldo()
        );

        usuario.setId(id);
    }

    public UsuarioFinal buscarPorNombreUsuario(
            String nombreUsuario
    ) throws Exception {

        String query =
                "SELECT * FROM t_usuarios " +
                        "WHERE nombre_usuario = ? LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        nombreUsuario
                );

        if (resultado.next()) {
            return convertir(resultado);
        }

        return null;
    }

    public UsuarioFinal buscarPorCorreo(
            String correoElectronico
    ) throws Exception {

        String query =
                "SELECT * FROM t_usuarios " +
                        "WHERE correo_electronico = ? LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        correoElectronico
                );

        if (resultado.next()) {
            return convertir(resultado);
        }

        return null;
    }

    public UsuarioFinal buscarPorCedula(
            String cedula
    ) throws Exception {

        String query =
                "SELECT * FROM t_usuarios " +
                        "WHERE cedula = ? LIMIT 1";

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        query,
                        cedula
                );

        if (resultado.next()) {
            return convertir(resultado);
        }

        return null;
    }

    public ArrayList<UsuarioFinal> listarTodos()
            throws Exception {

        ArrayList<UsuarioFinal> usuarios =
                new ArrayList<>();

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT * FROM t_usuarios ORDER BY id"
                );

        while (resultado.next()) {
            usuarios.add(
                    convertir(resultado)
            );
        }

        return usuarios;
    }

    public boolean existeNombreUsuario(
            String nombreUsuario
    ) throws Exception {

        return buscarPorNombreUsuario(
                nombreUsuario
        ) != null;
    }

    public boolean existeCorreo(
            String correoElectronico
    ) throws Exception {

        return buscarPorCorreo(
                correoElectronico
        ) != null;
    }

    public boolean existeCedula(
            String cedula
    ) throws Exception {

        return buscarPorCedula(
                cedula
        ) != null;
    }

    public void actualizarContrasenia(
            String nombreUsuario,
            String nuevaContrasenia
    ) throws Exception {

        String query =
                "UPDATE t_usuarios " +
                        "SET contrasenia = ? " +
                        "WHERE nombre_usuario = ?";

        Connector.getConnection().ejecutarStatement(
                query,
                nuevaContrasenia,
                nombreUsuario
        );
    }

    public void actualizarSaldo(
            UsuarioFinal usuario
    ) throws Exception {

        int id = usuario.getId();

        if (id <= 0) {

            UsuarioFinal guardado =
                    buscarPorNombreUsuario(
                            usuario.getNombreUsuario()
                    );

            if (guardado == null) {
                throw new IllegalStateException(
                        "El usuario no existe en la base de datos."
                );
            }

            id = guardado.getId();
            usuario.setId(id);
        }

        String query =
                "UPDATE t_usuarios SET saldo = ? WHERE id = " + id;

        Connector.getConnection().ejecutarStatement(
                query,
                usuario.getSaldo()
        );
    }

    private UsuarioFinal convertir(
            ResultSet resultado
    ) throws Exception {

        java.sql.Date fechaSQL =
                resultado.getDate(
                        "fecha_nacimiento"
                );

        LocalDate fechaNacimiento =
                fechaSQL == null
                        ? null
                        : fechaSQL.toLocalDate();

        return new UsuarioFinal(
                resultado.getInt("id"),
                resultado.getString("nombre_completo"),
                fechaNacimiento,
                resultado.getString("nacionalidad"),
                resultado.getString("cedula"),
                resultado.getString("avatar"),
                resultado.getFloat("saldo"),
                resultado.getString("correo_electronico"),
                resultado.getString("nombre_usuario"),
                resultado.getString("contrasenia"),
                MAX_CANCIONES,
                MAX_LISTAS
        );
    }
}