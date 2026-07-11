import java.time.LocalDate;

public abstract class Usuario {
    protected String correoElectronico;
    protected String nombreUsuario;
    protected String contrasenia;
    protected ColaReproduccion colaReproduccion;

    // Constructor
    public Usuario(String correoElectronico, String nombreUsuario, String contrasenia) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;

        // La cola de reproducción se crea como parte del usuario.
        this.colaReproduccion = new ColaReproduccion();
    }

    // Getters y Setters
    public String getCorreoElectronico() { return correoElectronico; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenia() { return contrasenia; }
    public ColaReproduccion getColaReproduccion() { return colaReproduccion; }

    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public void setContrasenia(String contrasenia) { this.contrasenia = contrasenia; }

    // --- Métodos de Validación Estáticos ---

    // Valida formato de correo usando una expresión regular estándar
    public static boolean esCorreoValido(String correo) {
        if (correo == null) return false;
        String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return correo.matches(regex);
    }

    // Valida los requisitos fuertes de la contraseña
    public static boolean esContraseniaValida(String contrasenia) {

        // Verifica que la contraseña exista y tenga entre 8 y 12 caracteres.
        if (contrasenia == null || contrasenia.length() < 8 || contrasenia.length() > 12) return false;

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasenia.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (Character.isLowerCase(c)) tieneMinuscula = true;
            if (Character.isDigit(c))     tieneNumero = true;
            if (!Character.isLetterOrDigit(c)) tieneEspecial = true;
        }
        return tieneMayuscula && tieneMinuscula && tieneNumero && tieneEspecial;
    }

    // Método para cambiar la contraseña del usuario
    public boolean cambiarContrasenia(String contraseniaActual,
                                      String nuevaContrasenia,
                                      String confirmarContrasenia) {

        // Verifica que la contraseña actual sea correcta.
        if (!contrasenia.equals(contraseniaActual)) {
            System.out.println("La contraseña actual es incorrecta.");
            return false;
        }

        // Verifica que la nueva contraseña cumpla los requisitos establecidos.
        if (!esContraseniaValida(nuevaContrasenia)) {
            System.out.println("La nueva contraseña no cumple los requisitos:" +
                    "\n- Debe tener entre 8 y 12 caracteres." +
                    "\n- Debe incluir al menos una letra mayúscula." +
                    "\n- Debe incluir al menos una letra minúscula." +
                    "\n- Debe incluir al menos un número." +
                    "\n- Debe incluir al menos un carácter especial.");
            return false;
        }

        // Verifica que la nueva contraseña sea diferente de la actual.
        if (nuevaContrasenia.equals(contraseniaActual)) {
            System.out.println("La nueva contraseña no puede ser igual a la actual.");
            return false;
        }

        // Verifica que la confirmación coincida con la nueva contraseña.
        if (!nuevaContrasenia.equals(confirmarContrasenia)) {
            System.out.println("La confirmación de la nueva contraseña no coincide.");
            return false;
        }

        // Actualiza la contraseña del usuario.
        contrasenia = nuevaContrasenia;

        System.out.println("Contraseña actualizada correctamente.");
        return true;
    }

    @Override
    public abstract String toString();
}