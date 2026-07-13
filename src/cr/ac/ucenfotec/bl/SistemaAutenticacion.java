package cr.ac.ucenfotec.bl;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class SistemaAutenticacion {

    // Constructor
    public SistemaAutenticacion() {
    }

    // Método para registrar un nuevo usuario final
    public UsuarioFinal registrarUsuario(
            ArrayList<UsuarioFinal> usuariosFinales,
            ArrayList<String> nacionalidadesPermitidas,
            String nombreCompleto,
            LocalDate fechaNacimiento,
            String nacionalidad,
            String cedula,
            String avatar,
            String correoElectronico,
            String nombreUsuario,
            String contrasenia,
            String confirmarContrasenia,
            byte cantidadMaximaCanciones,
            byte cantidadMaximaListas) {

        // Verifica que la colección de usuarios exista.
        if (usuariosFinales == null) {
            System.out.println("No se encuentra disponible la colección de usuarios.");
            return null;
        }

        // Verifica que el nombre completo contenga únicamente letras y espacios.
        if (!esNombreValido(nombreCompleto)) {
            System.out.println(
                    "El nombre completo solo puede contener letras y espacios.");
            return null;
        }

        // Verifica que la fecha de nacimiento sea válida.
        if (fechaNacimiento == null) {
            System.out.println("La fecha de nacimiento no puede estar vacía.");
            return null;
        }

        // Verifica que el usuario sea mayor de edad.
        if (!esMayorDeEdad(fechaNacimiento)) {
            System.out.println(
                    "El usuario debe ser mayor de edad para registrarse.");
            return null;
        }

        // Verifica que la nacionalidad esté disponible en la aplicación.
        if (!esNacionalidadValida(
                nacionalidad, nacionalidadesPermitidas)) {

            System.out.println(
                    "La nacionalidad seleccionada no está disponible.");
            return null;
        }

        // Verifica que la cédula no esté vacía.
        if (cedula == null || cedula.trim().isEmpty()) {
            System.out.println("La cédula no puede estar vacía.");
            return null;
        }

        // Verifica que la cédula no esté registrada anteriormente.
        if (cedulaExiste(usuariosFinales, cedula)) {
            System.out.println("La cédula ya está registrada.");
            return null;
        }

        // Verifica que el correo tenga un formato válido.
        if (!Usuario.esCorreoValido(correoElectronico)) {
            System.out.println(
                    "El correo electrónico no tiene un formato válido.");
            return null;
        }

        // Verifica que el correo no esté registrado anteriormente.
        if (correoExiste(usuariosFinales, correoElectronico)) {
            System.out.println(
                    "El correo electrónico ya está registrado.");
            return null;
        }

        // Verifica que el nombre de usuario no esté vacío.
        if (nombreUsuario == null
                || nombreUsuario.trim().isEmpty()) {

            System.out.println(
                    "El nombre de usuario no puede estar vacío.");
            return null;
        }

        // Verifica que el nombre de usuario no esté ocupado.
        if (nombreUsuarioExiste(usuariosFinales, nombreUsuario)) {
            System.out.println(
                    "El nombre de usuario ya está en uso.");
            return null;
        }

        // Verifica que la contraseña cumpla todos los requisitos.
        if (!Usuario.esContraseniaValida(contrasenia)) {
            System.out.println(
                    "La contraseña no cumple los requisitos:" +
                            "\n- Debe tener entre 8 y 12 caracteres." +
                            "\n- Debe incluir al menos una letra mayúscula." +
                            "\n- Debe incluir al menos una letra minúscula." +
                            "\n- Debe incluir al menos un número." +
                            "\n- Debe incluir al menos un carácter especial.");

            return null;
        }

        // Verifica que la contraseña y su confirmación coincidan.
        if (!confirmarContrasenia(
                contrasenia, confirmarContrasenia)) {

            System.out.println("Las contraseñas no coinciden.");
            return null;
        }

        // Si el usuario no proporciona un avatar,
        // se asigna una imagen predeterminada.
        if (avatar == null || avatar.trim().isEmpty()) {
            avatar = "avatar_default.png";
        }

        // Crea el nuevo usuario después de completar las validaciones.
        UsuarioFinal nuevoUsuario = new UsuarioFinal(
                nombreCompleto,
                fechaNacimiento,
                nacionalidad,
                cedula,
                avatar,
                correoElectronico,
                nombreUsuario,
                contrasenia,
                cantidadMaximaCanciones,
                cantidadMaximaListas
        );

        // Agrega el usuario a la colección principal de cr.ac.ucenfotec.bl.Aplicacion.
        usuariosFinales.add(nuevoUsuario);

        System.out.println("Usuario registrado correctamente.");
        System.out.println("Bono inicial asignado: $4.99");

        return nuevoUsuario;
    }

    // Método para iniciar sesión de un usuario final
    public UsuarioFinal iniciarSesion(
            ArrayList<UsuarioFinal> usuariosFinales,
            String nombreUsuario,
            String contrasenia) {

        // Verifica que la colección de usuarios exista.
        if (usuariosFinales == null) {
            System.out.println(
                    "No se encuentra disponible la colección de usuarios.");
            return null;
        }

        // Recorre todos los usuarios registrados.
        for (int i = 0; i < usuariosFinales.size(); i++) {

            // Obtiene el usuario de la posición actual.
            UsuarioFinal usuario = usuariosFinales.get(i);

            // Verifica si el nombre de usuario coincide.
            boolean nombreUsuarioCorrecto =
                    usuario.getNombreUsuario()
                            .equalsIgnoreCase(nombreUsuario);

            // Verifica si la contraseña coincide.
            boolean contraseniaCorrecta =
                    usuario.getContrasenia()
                            .equals(contrasenia);

            // Si ambas credenciales son correctas,
            // devuelve el usuario que inició sesión.
            if (nombreUsuarioCorrecto && contraseniaCorrecta) {

                System.out.println(
                        "Inicio de sesión exitoso. Bienvenido, "
                                + usuario.getNombreCompleto() + ".");

                return usuario;
            }
        }

        // Si no encuentra credenciales válidas, devuelve null.
        System.out.println(
                "Nombre de usuario o contraseña incorrectos.");

        return null;
    }

    // Método para verificar si un correo ya está registrado
    public boolean correoExiste(
            ArrayList<UsuarioFinal> usuariosFinales,
            String correoElectronico) {

        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            if (usuario.getCorreoElectronico()
                    .equalsIgnoreCase(correoElectronico)) {

                return true;
            }
        }

        return false;
    }

    // Método para verificar si un nombre de usuario ya existe
    public boolean nombreUsuarioExiste(
            ArrayList<UsuarioFinal> usuariosFinales,
            String nombreUsuario) {

        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            if (usuario.getNombreUsuario()
                    .equalsIgnoreCase(nombreUsuario)) {

                return true;
            }
        }

        return false;
    }

    // Método para verificar si una cédula ya está registrada
    public boolean cedulaExiste(
            ArrayList<UsuarioFinal> usuariosFinales,
            String cedula) {

        for (int i = 0; i < usuariosFinales.size(); i++) {

            UsuarioFinal usuario = usuariosFinales.get(i);

            if (usuario.getCedula().equalsIgnoreCase(cedula)) {
                return true;
            }
        }

        return false;
    }

    // Método para validar que el nombre solamente contenga letras y espacios
    public boolean esNombreValido(String nombre) {

        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }

        return nombre.matches(
                "[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    // Método para validar que el usuario sea mayor de edad
    public boolean esMayorDeEdad(LocalDate fechaNacimiento) {

        if (fechaNacimiento == null) {
            return false;
        }

        // No permite fechas de nacimiento futuras.
        if (fechaNacimiento.isAfter(LocalDate.now())) {
            return false;
        }

        int edad = Period.between(
                fechaNacimiento,
                LocalDate.now()
        ).getYears();

        return edad >= 18;
    }

    // Método para validar una nacionalidad
    public boolean esNacionalidadValida(
            String nacionalidad,
            ArrayList<String> nacionalidadesPermitidas) {

        if (nacionalidad == null
                || nacionalidad.trim().isEmpty()) {

            return false;
        }

        if (nacionalidadesPermitidas == null
                || nacionalidadesPermitidas.isEmpty()) {

            return false;
        }

        // Recorre la lista de nacionalidades disponibles.
        for (int i = 0;
             i < nacionalidadesPermitidas.size();
             i++) {

            String nacionalidadPermitida =
                    nacionalidadesPermitidas.get(i);

            if (nacionalidadPermitida
                    .equalsIgnoreCase(nacionalidad)) {

                return true;
            }
        }

        return false;
    }

    // Método para verificar que las contraseñas coincidan
    public boolean confirmarContrasenia(
            String contrasenia,
            String confirmarContrasenia) {

        if (contrasenia == null
                || confirmarContrasenia == null) {

            return false;
        }

        return contrasenia.equals(confirmarContrasenia);
    }
}