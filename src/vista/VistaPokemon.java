package vista;

import controlador.Controlador;
import modelo.Entrenador;
import modelo.Pokemon;
import java.util.ArrayList;

/**
 * Interfaz que define los métodos que debe implementar cualquier vista
 * (GUI o consola) para interactuar con el usuario en el simulador Pokémon.
 */
public interface VistaPokemon {

   /**
    * Muestra la pantalla de bienvenida al usuario.
    */
   void bienvenido();

   /**
    * Solicita o muestra los nombres de los entrenadores.
    */
   void entrenadores();

   /**
    * Solicita o muestra los nombres de los Pokémon a seleccionar.
    */
   void pokemones();

   /**
    * Muestra la información de los Pokémon seleccionados.
    * @param pokemon Lista de Pokémon a mostrar.
    */
   void mostrarPokemon(ArrayList<Pokemon> pokemon);

   /**
    * Muestra el entrenador ganador al finalizar la batalla.
    * @param entrenador Entrenador ganador.
    */
   void ganador(Entrenador entrenador);

   /**
    * Permite al usuario elegir el Pokémon activo para el combate.
    * @param entrenador Entrenador 1.
    * @param entrenador2 Entrenador 2.
    */
   void elegirPokemon(Entrenador entrenador, Entrenador entrenador2);

   /**
    * Permite al usuario elegir el ataque a realizar.
    * @param pokemon Pokémon que atacará.
    */
   void elegirAtaque(Pokemon pokemon);

   /**
    * Asocia el controlador a la vista para manejar eventos y acciones.
    * @param controlador Instancia del controlador principal.
    */
   void setControlador(Controlador controlador);

   /**
    * Continúa con el flujo del juego (por ejemplo, pasar al siguiente turno o escena).
    */
   void continuar();

   /**
    * Indica si ocurrió un error en la vista (por ejemplo, entrada inválida).
    * @return true si hay error, false en caso contrario.
    */
   boolean isError();
}