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
   void elegirPokemon(Entrenador entrenador);
   void elegirAtaque(Pokemon pokemon);
   void setControlador(Controlador controlador);
   void continuar();
   boolean isError();
}