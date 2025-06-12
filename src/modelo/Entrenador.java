package modelo;

import java.util.ArrayList;
import java.util.List;
public class Entrenador extends SerVivo {

    // Atributos
    private ArrayList<Pokemon> equipo = new ArrayList<>();

    // Constructor
    public Entrenador(String nombre) {
        // Heredando nombre
        super(nombre);
    }

    // Getter
    public ArrayList<Pokemon> getEquipo() {
        return equipo;
    }

    //Factory method para capturar un entrenador por consola sin necesidad de instanciar un objeto con new
    public static Entrenador capturarEntrenador(String nombre, String primero, String segundo, String tercero) {
        
        Entrenador inicial = new Entrenador(nombre);
        inicial.capturarPokemon(primero, segundo, tercero);
        return inicial; // Retorna nuevo entrenador
    }

    // Método para crear el equipo de 3 Pokemones
    public void capturarPokemon(String primero, String segundo, String tercero) {

        List<String> lista = List.of(primero, segundo, tercero); // inmutable
        
        for (int i = 0; i < 3; i++) {
            equipo.add(Pokemon.instanciarPokemon(lista.get(i))); // Se instancian automáticamente
        }
    }
}