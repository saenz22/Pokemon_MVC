package vista;

import controlador.Controlador;
import modelo.Entrenador;
import modelo.Pokemon;
import modelo.Ataque;
import java.util.ArrayList;

public interface VistaPokemon {

   void bienvenido();
   void entrenadores();
   void pokemones();
   void mostrarPokemon(ArrayList<Pokemon> pokemon);
   void ganador(Entrenador entrenador);
   Pokemon elegirPokemon(Entrenador entrenador);
   Ataque elegirAtaque(Pokemon pokemon);
   void setControlador(Controlador controlador);
   boolean isError();
}