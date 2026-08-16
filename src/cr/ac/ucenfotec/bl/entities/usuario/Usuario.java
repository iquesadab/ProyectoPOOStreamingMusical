package cr.ac.ucenfotec.bl.entities.usuario;

import cr.ac.ucenfotec.bl.entities.colaReproduccion.ColaReproduccion;

public abstract class Usuario {

    private int id;
    private String correoElectronico;
    private String nombreUsuario;
    private String contrasenia;
    private ColaReproduccion colaReproduccion;

    public Usuario(String correoElectronico,
                   String nombreUsuario,
                   String contrasenia) {

        this(
                0,
                correoElectronico,
                nombreUsuario,
                contrasenia
        );
    }

    public Usuario(int id,
                   String correoElectronico,
                   String nombreUsuario,
                   String contrasenia) {

        this.id = id;
        this.correoElectronico = correoElectronico;
        this.nombreUsuario = nombreUsuario;
        this.contrasenia = contrasenia;
        this.colaReproduccion = new ColaReproduccion();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public ColaReproduccion getColaReproduccion() {
        return colaReproduccion;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public static boolean esCorreoValido(String correo) {

        if (correo == null || correo.isBlank()) {
            return false;
        }

        String regex =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

        return correo.matches(regex);
    }

    public static boolean esContraseniaValida(String contrasenia) {

        if (contrasenia == null
                || contrasenia.length() < 8
                || contrasenia.length() > 12) {

            return false;
        }

        boolean tieneMayuscula = false;
        boolean tieneMinuscula = false;
        boolean tieneNumero = false;
        boolean tieneEspecial = false;

        for (char c : contrasenia.toCharArray()) {

            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;

            } else if (Character.isLowerCase(c)) {
                tieneMinuscula = true;

            } else if (Character.isDigit(c)) {
                tieneNumero = true;

            } else if (!Character.isWhitespace(c)) {
                tieneEspecial = true;
            }
        }

        return tieneMayuscula
                && tieneMinuscula
                && tieneNumero
                && tieneEspecial;
    }

    public boolean cambiarContrasenia(
            String contraseniaActual,
            String nuevaContrasenia,
            String confirmarContrasenia) {

        if (!contrasenia.equals(contraseniaActual)) {
            return false;
        }

        if (!esContraseniaValida(nuevaContrasenia)) {
            return false;
        }

        if (nuevaContrasenia.equals(contraseniaActual)) {
            return false;
        }

        if (!nuevaContrasenia.equals(confirmarContrasenia)) {
            return false;
        }

        contrasenia = nuevaContrasenia;

        return true;
    }

    @Override
    public abstract String toString();
}