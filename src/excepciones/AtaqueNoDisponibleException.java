package excepciones;

/**
 * Se lanza al intentar seleccionar un ataque que no está en la lista de opciones.
 */
public class AtaqueNoDisponibleException extends Exception {
    public AtaqueNoDisponibleException(String mensaje) {
        super(mensaje);
    }
}