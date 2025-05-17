package vista;

import java.util.ArrayList;

import controlador.Controlador;
import modelo.Entrenador;
import modelo.Pokemon;
import modelo.Ataque;

public interface VistaPokemon {

   void bienvenido();
   ArrayList<String> entrenadores();
   ArrayList<String> pokemones(String nombre);
   void mostrarPokemon(Pokemon pokemon);
   void ganador(Entrenador entrenador);
   byte getEscena();
   Pokemon elegirPokemon(Entrenador entrenador);
   Ataque elegirAtaque(Pokemon pokemon);
   void setControlador(Controlador controlador);
   void setEscena(byte b);
   void mostrarResultadoCombate(Pokemon pokemon);
}