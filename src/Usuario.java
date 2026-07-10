import java.time.LocalDate;

public abstract class Usuario {
    protected String correoElectronico;
    protected String nombreUsuario;
    protected String contrasenia;

    // Constructor
    public Usuario(String correoElectronico, String nombreUsuario, String contrasenia) {
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
    }

    // Getters y Setters
    public String getCorreoElectronico() { return correoElectronico; }
    public String getNombreUsuario() { return nombreUsuario; }
    public String getContrasenia() { return contrasenia; }

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
        if (contrasenia == null || contrasenia.length() < 8) return false;

        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasenia.toCharArray()) {
            if (Character.isUpperCase(c)) tieneMayuscula = true;
            if (Character.isDigit(c))     tieneNumero = true;
            if (!Character.isLetterOrDigit(c)) tieneEspecial = true;
        }
        return tieneMayuscula && tieneNumero && tieneEspecial;
    }

    @Override
    public abstract String toString();
}