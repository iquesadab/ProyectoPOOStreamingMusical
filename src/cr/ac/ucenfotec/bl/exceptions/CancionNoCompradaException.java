package cr.ac.ucenfotec.bl.exceptions;

public class CancionNoCompradaException extends RuntimeException {

    public CancionNoCompradaException(String mensaje) {
        super(mensaje);
    }
}