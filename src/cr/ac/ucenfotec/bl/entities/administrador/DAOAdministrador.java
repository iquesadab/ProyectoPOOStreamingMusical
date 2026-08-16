package cr.ac.ucenfotec.bl.entities.administrador;

import cr.ac.ucenfotec.dl.Connector;

import java.sql.ResultSet;

public class DAOAdministrador {

    public void insertar(
            Administrador administrador
    ) throws Exception {

        String query =
                "INSERT INTO t_administradores " +
                        "(correo_electronico, nombre_usuario, contrasenia) " +
                        "VALUES (?, ?, ?)";

        Connector.getConnection().ejecutarStatement(
                query,
                administrador.getCorreoElectronico(),
                administrador.getNombreUsuario(),
                administrador.getContrasenia()
        );

        Administrador guardado =
                buscarPorNombreUsuario(
                        administrador.getNombreUsuario()
                );

        if (guardado != null) {
            administrador.setId(
                    guardado.getId()
            );
        }
    }

    public boolean existeAdministrador()
            throws Exception {

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT id FROM t_administradores LIMIT 1"
                );

        return resultado.next();
    }

    public Administrador buscarAdministrador()
            throws Exception {

        ResultSet resultado =
                Connector.getConnection().ejecutarQuery(
                        "SELECT * FROM t_administradores ORDER BY id LIMIT 1"
                );

        if (resultado.next()) {
            return convertir(resultado);
        }

        return null;
    }

    public Administrador buscarPorNombreUsuario(
            String nombreUsuario
    ) throws Exception {

        String query =
                "SELECT * FROM t_administradores " +
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

    public void actualizarContrasenia(
            String nombreUsuario,
            String nuevaContrasenia
    ) throws Exception {

        String query =
                "UPDATE t_administradores " +
                        "SET contrasenia = ? " +
                        "WHERE nombre_usuario = ?";

        Connector.getConnection().ejecutarStatement(
                query,
                nuevaContrasenia,
                nombreUsuario
        );
    }

    private Administrador convertir(
            ResultSet resultado
    ) throws Exception {

        return new Administrador(
                resultado.getInt("id"),
                resultado.getString("correo_electronico"),
                resultado.getString("nombre_usuario"),
                resultado.getString("contrasenia")
        );
    }
}