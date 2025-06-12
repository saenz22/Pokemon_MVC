package excepciones;

/**
 * Se lanza cuando se intenta realizar una acción con un Pokémon debilitado (HP <= 0).
 */
public class PokemonDebilitadoException extends Exception {
    public PokemonDebilitadoException(String mensaje) {
        super(mensaje);
    }
}