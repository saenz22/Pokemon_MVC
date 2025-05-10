public interface VistaPokemon {
    void bienvenida();
    void crearEntrenadores();
    void crearPokemon();
    void mostrarPokemon(String nombre, String tipo, String ataques, String hp, String velocidad, String nivel, String df, String dfEs, String atk, String atkEs);
    void combate();
    void mostrarGanador(String nombre);
}