package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.administrador.Administrador;
import cr.ac.ucenfotec.bl.entities.administrador.DAOAdministrador;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.bl.exceptions.ContraseniaInvalidaException;
import cr.ac.ucenfotec.bl.exceptions.CredencialesInvalidasException;
import cr.ac.ucenfotec.bl.exceptions.UsuarioYaRegistradoException;

public class GestorAdministrador {

    private final DAOAdministrador daoAdministrador;

    public GestorAdministrador() {
        this.daoAdministrador =
                new DAOAdministrador();
    }

    // =========================================================
    // VERIFICAR ADMINISTRADOR
    // =========================================================

    public boolean existeAdministrador()
            throws Exception {

        return daoAdministrador
                .existeAdministrador();
    }

    // =========================================================
    // REGISTRAR ADMINISTRADOR
    // =========================================================

    public Administrador registrarAdministrador(
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia
    ) throws Exception {

        if (daoAdministrador.existeAdministrador()) {

            throw new UsuarioYaRegistradoException(
                    "Ya existe un administrador registrado."
            );
        }

        if (!Usuario.esCorreoValido(
                correoElectronico
        )) {

            throw new IllegalArgumentException(
                    "El correo electrónico no tiene un formato válido."
            );
        }

        if (nombreUsuario == null ||
                nombreUsuario.isBlank()) {

            throw new IllegalArgumentException(
                    "El nombre de usuario no puede estar vacío."
            );
        }

        validarContrasenia(contrasenia);

        if (!contrasenia.equals(
                confirmarContrasenia
        )) {

            throw new ContraseniaInvalidaException(
                    "Las contraseñas no coinciden."
            );
        }

        Administrador administrador =
                new Administrador(
                        correoElectronico.trim(),
                        nombreUsuario.trim(),
                        contrasenia
                );

        daoAdministrador.insertar(
                administrador
        );

        return administrador;
    }

    // =========================================================
    // INICIAR SESIÓN
    // =========================================================

    public Administrador iniciarSesion(
            String nombreUsuario,
            String contrasenia
    ) throws Exception {

        Administrador administrador =
                daoAdministrador
                        .buscarPorNombreUsuario(
                                nombreUsuario
                        );

        if (administrador == null ||
                !administrador
                        .getContrasenia()
                        .equals(contrasenia)) {

            throw new CredencialesInvalidasException(
                    "Nombre de usuario o contraseña incorrectos."
            );
        }

        return administrador;
    }

    // =========================================================
    // CAMBIAR CONTRASEÑA
    // =========================================================

    public void cambiarContrasenia(
            Administrador administrador,
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia
    ) throws Exception {

        if (administrador == null) {

            throw new CredencialesInvalidasException(
                    "No hay un administrador autenticado."
            );
        }

        if (!administrador
                .getContrasenia()
                .equals(contraseniaActual)) {

            throw new CredencialesInvalidasException(
                    "La contraseña actual es incorrecta."
            );
        }

        validarContrasenia(
                nuevaContrasenia
        );

        if (nuevaContrasenia.equals(
                contraseniaActual
        )) {

            throw new ContraseniaInvalidaException(
                    "La nueva contraseña debe ser diferente a la actual."
            );
        }

        if (!nuevaContrasenia.equals(
                confirmarContrasenia
        )) {

            throw new ContraseniaInvalidaException(
                    "La confirmación de la nueva contraseña no coincide."
            );
        }

        daoAdministrador
                .actualizarContrasenia(
                        administrador
                                .getNombreUsuario(),
                        nuevaContrasenia
                );

        administrador.setContrasenia(
                nuevaContrasenia
        );
    }

    // =========================================================
    // OBTENER ADMINISTRADOR
    // =========================================================

    public Administrador obtenerAdministrador()
            throws Exception {

        return daoAdministrador
                .buscarAdministrador();
    }

    // =========================================================
    // VALIDAR CONTRASEÑA
    // =========================================================

    private void validarContrasenia(
            String contrasenia
    ) {

        if (!Usuario.esContraseniaValida(
                contrasenia
        )) {

            throw new ContraseniaInvalidaException(
                    "La contraseña debe tener entre 8 y 12 caracteres " +
                            "e incluir mayúscula, minúscula, número " +
                            "y carácter especial."
            );
        }
    }
}