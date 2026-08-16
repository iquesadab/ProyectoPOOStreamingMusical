package cr.ac.ucenfotec.bl.logic;

import cr.ac.ucenfotec.bl.entities.usuario.DAOUsuario;
import cr.ac.ucenfotec.bl.entities.usuario.Usuario;
import cr.ac.ucenfotec.bl.entities.usuario.UsuarioFinal;
import cr.ac.ucenfotec.bl.exceptions.ContraseniaInvalidaException;
import cr.ac.ucenfotec.bl.exceptions.CredencialesInvalidasException;
import cr.ac.ucenfotec.bl.exceptions.SaldoInvalidoException;
import cr.ac.ucenfotec.bl.exceptions.UsuarioMenorEdadException;
import cr.ac.ucenfotec.bl.exceptions.UsuarioYaRegistradoException;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuario {

    private static final byte MAX_CANCIONES = 50;
    private static final byte MAX_LISTAS = 20;

    private static final List<String> NACIONALIDADES_PERMITIDAS = List.of(
            "Costarricense",
            "Venezolana",
            "Panameña",
            "Nicaragüense",
            "Salvadoreña",
            "Guatemalteca",
            "Hondureña",
            "Mexicana",
            "Colombiana"
    );

    private final DAOUsuario daoUsuario;

    public GestorUsuario() {
        this.daoUsuario = new DAOUsuario();
    }

    // =========================================================
    // REGISTRAR USUARIO
    // =========================================================

    public UsuarioFinal registrarUsuario(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia
    ) throws Exception {

        validarDatosRegistro(
                nombreCompleto,
                fechaNacimiento,
                nacionalidad,
                cedula,
                correoElectronico,
                nombreUsuario,
                contrasenia,
                confirmarContrasenia
        );

        if (daoUsuario.existeCedula(cedula)) {
            throw new UsuarioYaRegistradoException(
                    "La cédula ya está registrada."
            );
        }

        if (daoUsuario.existeCorreo(correoElectronico)) {
            throw new UsuarioYaRegistradoException(
                    "El correo electrónico ya está registrado."
            );
        }

        if (daoUsuario.existeNombreUsuario(nombreUsuario)) {
            throw new UsuarioYaRegistradoException(
                    "El nombre de usuario ya está en uso."
            );
        }

        UsuarioFinal usuario = new UsuarioFinal(
                nombreCompleto.trim(),
                fechaNacimiento,
                nacionalidad,
                cedula.trim(),
                avatar,
                correoElectronico.trim(),
                nombreUsuario.trim(),
                contrasenia,
                MAX_CANCIONES,
                MAX_LISTAS
        );

        daoUsuario.insertar(usuario);

        return usuario;
    }

    // =========================================================
    // INICIAR SESIÓN
    // =========================================================

    public UsuarioFinal iniciarSesion(
            String nombreUsuario,
            String contrasenia
    ) throws Exception {

        UsuarioFinal usuario =
                daoUsuario.buscarPorNombreUsuario(nombreUsuario);

        if (usuario == null ||
                !usuario.getContrasenia().equals(contrasenia)) {

            throw new CredencialesInvalidasException(
                    "Nombre de usuario o contraseña incorrectos."
            );
        }

        return usuario;
    }

    // =========================================================
    // CAMBIAR CONTRASEÑA
    // =========================================================

    public void cambiarContrasenia(
            UsuarioFinal usuario,
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia
    ) throws Exception {

        if (usuario == null) {
            throw new CredencialesInvalidasException(
                    "No hay un usuario autenticado."
            );
        }

        if (!usuario.getContrasenia().equals(contraseniaActual)) {
            throw new CredencialesInvalidasException(
                    "La contraseña actual es incorrecta."
            );
        }

        validarNuevaContrasenia(
                contraseniaActual,
                nuevaContrasenia,
                confirmarContrasenia
        );

        daoUsuario.actualizarContrasenia(
                usuario.getNombreUsuario(),
                nuevaContrasenia
        );

        usuario.setContrasenia(nuevaContrasenia);
    }

    // =========================================================
    // RECARGAR SALDO
    // =========================================================

    public void recargarSaldo(
            UsuarioFinal usuario,
            float monto
    ) throws Exception {

        if (usuario == null) {
            throw new CredencialesInvalidasException(
                    "No hay un usuario autenticado."
            );
        }

        if (monto <= 0) {
            throw new SaldoInvalidoException(
                    "El monto de la recarga debe ser mayor que cero."
            );
        }

        float nuevoSaldo =
                Math.round(
                        (usuario.getSaldo() + monto) * 100f
                ) / 100f;

        usuario.setSaldo(nuevoSaldo);

        daoUsuario.actualizarSaldo(usuario);
    }

    // =========================================================
    // BUSCAR USUARIO
    // =========================================================

    public UsuarioFinal buscarPorNombreUsuario(
            String nombreUsuario
    ) throws Exception {

        return daoUsuario.buscarPorNombreUsuario(
                nombreUsuario
        );
    }

    // =========================================================
    // LISTAR USUARIOS
    // =========================================================

    public ArrayList<UsuarioFinal> listarUsuarios()
            throws Exception {

        return daoUsuario.listarTodos();
    }

    // =========================================================
    // NACIONALIDADES PERMITIDAS
    // =========================================================

    public List<String> getNacionalidadesPermitidas() {
        return NACIONALIDADES_PERMITIDAS;
    }

    // =========================================================
    // VALIDACIONES DEL REGISTRO
    // =========================================================

    private void validarDatosRegistro(
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia
    ) {

        if (nombreCompleto == null ||
                !nombreCompleto.trim().matches("[\\p{L} ]+")) {

            throw new IllegalArgumentException(
                    "El nombre completo solo puede contener letras y espacios."
            );
        }

        validarEdad(fechaNacimiento);

        if (nacionalidad == null ||
                NACIONALIDADES_PERMITIDAS.stream()
                        .noneMatch(
                                n -> n.equalsIgnoreCase(
                                        nacionalidad.trim()
                                )
                        )) {

            throw new IllegalArgumentException(
                    "La nacionalidad seleccionada no está disponible."
            );
        }

        if (cedula == null || cedula.isBlank()) {
            throw new IllegalArgumentException(
                    "La cédula no puede estar vacía."
            );
        }

        if (!Usuario.esCorreoValido(correoElectronico)) {
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

        if (!contrasenia.equals(confirmarContrasenia)) {
            throw new ContraseniaInvalidaException(
                    "Las contraseñas no coinciden."
            );
        }
    }

    // =========================================================
    // VALIDAR EDAD
    // =========================================================

    private void validarEdad(
            LocalDate fechaNacimiento
    ) {

        if (fechaNacimiento == null ||
                fechaNacimiento.isAfter(LocalDate.now())) {

            throw new UsuarioMenorEdadException(
                    "La fecha de nacimiento no es válida."
            );
        }

        if (Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears() < 18) {

            throw new UsuarioMenorEdadException(
                    "El usuario debe ser mayor de edad."
            );
        }
    }

    // =========================================================
    // VALIDAR CONTRASEÑA
    // =========================================================

    private void validarContrasenia(
            String contrasenia
    ) {

        if (!Usuario.esContraseniaValida(contrasenia)) {

            throw new ContraseniaInvalidaException(
                    "La contraseña debe tener entre 8 y 12 caracteres " +
                            "e incluir mayúscula, minúscula, número " +
                            "y carácter especial."
            );
        }
    }

    // =========================================================
    // VALIDAR NUEVA CONTRASEÑA
    // =========================================================

    private void validarNuevaContrasenia(
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia
    ) {

        validarContrasenia(nuevaContrasenia);

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
    }
}