import java.time.LocalDate;
import java.time.Period;

public class SistemaAutenticacion {

    private UsuarioFinal[] usuarios;
    private int cantidadUsuarios;

    public SistemaAutenticacion(int capacidadMaxima) {
        this.usuarios = new UsuarioFinal[capacidadMaxima];
        this.cantidadUsuarios = 0;
    }

    public boolean registrarUsuario(String nombreCompleto, LocalDate fechaNacimiento,
                                    String nacionalidad, String cedula, String avatar,
                                    String correoElectronico, String nombreUsuario,
                                    String contrasenia, byte cantidadMaximaCanciones,
                                    byte cantidadMaximaListas) {

        if (cantidadUsuarios >= usuarios.length) {
            System.out.println("No se pueden registrar más usuarios.");
            return false;
        }

        if (!esNombreValido(nombreCompleto)) {
            System.out.println("El nombre solo puede contener letras. Intentá de nuevo.");
            return false;
        }

        if (Period.between(fechaNacimiento, LocalDate.now()).getYears() < 18) {
            System.out.println("El usuario debe ser mayor de edad para registrarse.");
            return false;
        }

        if (nacionalidad == null || nacionalidad.trim().isEmpty()) {
            System.out.println("La nacionalidad no puede estar vacía.");
            return false;
        }

        // Llamada a la validación en la clase Usuario
        if (!Usuario.esCorreoValido(correoElectronico)) {
            System.out.println("El correo electrónico no tiene un formato válido.");
            return false;
        }

        if (correoExiste(correoElectronico)) {
            System.out.println("El correo electrónico ya está registrado.");
            return false;
        }

        if (nombreUsuarioExiste(nombreUsuario)) {
            System.out.println("El nombre de usuario ya está en uso.");
            return false;
        }

        // Llamada a la validación en la clase Usuario
        if (!Usuario.esContraseniaValida(contrasenia)) {
            System.out.println("La contraseña no cumple los requisitos:" +
                    "\n- Mínimo 8 caracteres." +
                    "\n- Al menos una mayúscula." +
                    "\n- Al menos un número." +
                    "\n- Al menos un carácter especial.");
            return false;
        }

        usuarios[cantidadUsuarios] = new UsuarioFinal(nombreCompleto, fechaNacimiento,
                nacionalidad, cedula, avatar, correoElectronico, nombreUsuario,
                contrasenia, cantidadMaximaCanciones, cantidadMaximaListas);
        cantidadUsuarios++;

        System.out.println("Usuario registrado correctamente." +
                "\nBono inicial asignado: $4.99");
        return true;
    }

    public UsuarioFinal iniciarSesion(String correoElectronico, String contrasenia) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getCorreoElectronico().equals(correoElectronico) &&
                    usuarios[i].getContrasenia().equals(contrasenia)) {
                System.out.println("Inicio de sesión exitoso. Bienvenido, " +
                        usuarios[i].getNombreCompleto() + ".");
                return usuarios[i];
            }
        }
        System.out.println("Correo electrónico o contraseña incorrectos.");
        return null;
    }

    boolean correoExiste(String correoElectronico) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getCorreoElectronico().equals(correoElectronico)) {
                return true;
            }
        }
        return false;
    }

    boolean nombreUsuarioExiste(String nombreUsuario) {
        for (int i = 0; i < cantidadUsuarios; i++) {
            if (usuarios[i].getNombreUsuario().equals(nombreUsuario)) {
                return true;
            }
        }
        return false;
    }

    private boolean esNombreValido(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return false;
        }
        return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
    }

    public int getCantidadUsuarios() {
        return cantidadUsuarios;
    }
}