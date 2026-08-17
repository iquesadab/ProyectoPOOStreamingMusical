package cr.ac.ucenfotec.bl.exceptions;

// Excepción utilizada cuando se intenta realizar una operación
// con un saldo o monto que no es válido.
public class SaldoInvalidoException extends RuntimeException {

    public SaldoInvalidoException(String mensaje) {
        super(mensaje);
    }
}