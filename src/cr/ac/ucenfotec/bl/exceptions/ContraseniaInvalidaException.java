package cr.ac.ucenfotec.bl.exceptions;

public class ContraseniaInvalidaException extends RuntimeException {

    public ContraseniaInvalidaException(String mensaje) {
        super(mensaje);
    }
}