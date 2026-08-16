package cr.ac.ucenfotec.bl.exceptions;

public class UsuarioYaRegistradoException extends RuntimeException {

    public UsuarioYaRegistradoException(String mensaje) {
        super(mensaje);
    }
}