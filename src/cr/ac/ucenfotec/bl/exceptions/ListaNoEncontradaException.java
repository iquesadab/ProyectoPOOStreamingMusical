package cr.ac.ucenfotec.bl.exceptions;

public class ListaNoEncontradaException extends RuntimeException {

    public ListaNoEncontradaException(String mensaje) {
        super(mensaje);
    }
}