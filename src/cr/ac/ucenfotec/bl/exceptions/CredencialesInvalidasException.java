package cr.ac.ucenfotec.bl.exceptions;

// Excepción utilizada cuando el usuario ingresa
// credenciales incorrectas al iniciar sesión.
public class CredencialesInvalidasException extends RuntimeException {

    public CredencialesInvalidasException(String mensaje) {
        super(mensaje);
    }
}