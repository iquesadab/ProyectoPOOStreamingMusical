package cr.ac.ucenfotec.bl.exceptions;

public class UsuarioMenorEdadException extends RuntimeException {

    public UsuarioMenorEdadException(String mensaje) {
        super(mensaje);
    }
}