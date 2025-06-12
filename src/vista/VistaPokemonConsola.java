package vista;

import controlador.Controlador;
import excepciones.AtaqueNoDisponibleException;
import excepciones.PokemonDebilitadoException;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.NoSuchElementException;

/**
 Implementación de la interfaz VistaPokemon para interacción por consola.
  Permite al usuario jugar el simulador Pokémon desde la terminal.
 */
public class VistaPokemonConsola implements VistaPokemon {
    // Scanner para la entrada de datos por consola
    private Scanner scanner;
    // Nombres de entrenadores y pokémon seleccionados temporalmente
    private String nombre1, nombre2, pokemon1, pokemon2, pokemon3;
    // Referencia al controlador principal
    private Controlador controlador;

    /**
      Constructor. Inicializa los valores y el scanner.
     */
    public VistaPokemonConsola() {
        scanner = new Scanner(System.in);
        this.nombre1 = "";
        this.nombre2 = "";
        this.pokemon1 = "";
        this.pokemon2 = "";
        this.pokemon3 = "";
    }
    
    /**
     * Muestra la pantalla de bienvenida y avanza a la siguiente escena.
     */
    @Override
    public void bienvenido() {
        System.out.println(""" 
                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!""");
        limpiarConsola();
        controlador.avanzarEscena();
    }

    /**
     * Solicita los nombres de los entrenadores y avanza la escena.
     * Valida que los nombres no estén vacíos.
     */
    @Override
    public void entrenadores() {
        boolean nombresValidos = false;
        while (!nombresValidos) {
            try {
                System.out.print("Ingrese el nombre del Entrenador 1: ");
                nombre1 = scanner.nextLine().trim(); 
                if (nombre1.isEmpty()) {
                    System.out.println("Error: El nombre del Entrenador 1 no puede estar vacío.");
                    scanner.nextLine();
                    continue; 
                }

                System.out.print("Ingrese el nombre del Entrenador 2: ");
                nombre2 = scanner.nextLine().trim();
                if (nombre2.isEmpty()) {
                    System.out.println("Error: El nombre del Entrenador 2 no puede estar vacío.");
                    scanner.nextLine();
                    continue; 
                }
                nombresValidos = true; 

            } catch (NoSuchElementException e) { // esta atrapa de las entradas cuando intenta acceder a un elemento que no existe
                System.err.println("Error: No se encontró más entrada. Saliendo...");
                break;
            } catch (IllegalStateException e) {
                System.err.println("Error: El scanner ha sido cerrado. Saliendo...");
                break;
            } // ilegal state exeption es para cuando se quiere evitar que una funcion se invoque este en un momento inadecuado
        }

        String texto = """
                        Bienvenidos a Pokémon {jugador1} y
                        {jugador2} les espera un gran
                        desafío en su aventura.

                        Sus pokemones serán asignados
                        aleatoriamente, y tendrán que
                        enfrentarse para demostrar
                        quién es el mejor entrenador.
                """;
        texto = texto.replace("{jugador1}", nombre1).replace("{jugador2}", nombre2);
        System.out.println(texto);
        controlador.setListaEntrenadores(nombre1, nombre2);
        limpiarConsola();
        controlador.avanzarEscena();
    }

    /* 
     Solicita los nombres de los Pokémon para el equipo y avanza la escena.
     salida que los nombres no estén vacíos.
     */
    @Override
    public void pokemones() {
        boolean PokemonValidos = false;
        while (!PokemonValidos) {
            try {
                System.out.print("Ingresa el nombre del primer Pokémon: ");
                this.pokemon1 = scanner.nextLine().trim();
                if (pokemon1.isEmpty()) {
                    System.out.println("Error: El nombre del primer Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }

                System.out.print("Ingresa el nombre del segundo Pokémon: ");
                this.pokemon2 = scanner.nextLine().trim();
                if (pokemon2.isEmpty()) {
                    System.out.println("Error: El nombre del segundo Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }

                System.out.print("Ingresa el nombre del tercer Pokémon: ");
                this.pokemon3 = scanner.nextLine().trim();
                if (pokemon3.isEmpty()) {
                    System.out.println("Error: El nombre del tercer Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }
                PokemonValidos = true; // Si todos los nombres no están vacíos, salimos del bucle
                
            } catch (NoSuchElementException e) {
                System.err.println("Error: No se encontró más entrada. Puede que la entrada haya sido interrumpida.");
                break; // O podrías decidir salir del programa aquí
            }
        }
        controlador.setListaPokemones(pokemon1, pokemon2, pokemon3);
        limpiarConsola();
        controlador.avanzarEscena();
    }

    /*
    Permite al usuario elegir el Pokémon activo para el combate de cada entrenador.
      Llama a un método auxiliar para la selección.
     */
    @Override
    public void elegirPokemon(Entrenador entrenador1, Entrenador entrenador2) {
        controlador.setPokemonActivoEntrenador1(entrenador1.getEquipo().get(eleccion(entrenador1)-1));
        controlador.setPokemonActivoEntrenador2(entrenador2.getEquipo().get(eleccion(entrenador2)-1));
        controlador.ordenarContrincantes();
        System.out.println(controlador.getOrden().get(0).getNombre() + " vs " + controlador.getOrden().get(1).getNombre());
    }

  private byte eleccion(Entrenador entrenador) {
        ArrayList<Pokemon> pokemones = entrenador.getEquipo();
        int opcion = 0;
        
        while (true) {
            try {
                System.out.println("Elige un Pokémon para " + entrenador.getNombre() + ":");
                for (int i = 0; i < pokemones.size(); i++) {
                    Pokemon p = pokemones.get(i);
            
                    String estado = p.getHp() <= 0 ? " (Debilitado)" : " (HP: " + p.getHp() + ")";
                    System.out.println((i + 1) + ". " + p.getNombre() + estado);
                }

                System.out.print("Ingresa el número del Pokémon: ");
                opcion = scanner.nextInt();

                if (opcion >= 1 && opcion <= pokemones.size()) {
                    // NUEVO: Verificación de que el Pokémon no esté debilitado.
                    if (pokemones.get(opcion - 1).getHp() <= 0) {
                        throw new PokemonDebilitadoException("Error: " + pokemones.get(opcion - 1).getNombre() + " está debilitado y no puede luchar.");
                    }
                    System.out.println("Has elegido a " + pokemones.get(opcion - 1).getNombre());
                    break;
                } else {
                    throw new IndexOutOfBoundsException("Opción no válida. Elige un número entre 1 y " + pokemones.size() + ".");
                }
            } catch (InputMismatchException e) { 
                System.err.println("Entrada no válida. Por favor, ingresa únicamente un número.");
                scanner.next(); 
           
            } catch (PokemonDebilitadoException | IndexOutOfBoundsException e) {
                System.err.println(e.getMessage());
            }
        }
        
        scanner.nextLine();
        limpiarConsola();
        return (byte) opcion;
    }
    @Override
    public void elegirAtaque(Pokemon pokemon) {
        ArrayList<Ataque> ataques = pokemon.getAtaques();
        int opcion = 0;

        while (true) {
            try {
                System.out.println("Elige un Ataque para: " + pokemon.getNombre());
                for (int i = 0; i < ataques.size(); i++) {
                    System.out.println((i + 1) + ". " + ataques.get(i).getNombre() + " / Poder: " + ataques.get(i).getPoder());
                }

                System.out.print("Ingresa el número del ataque: ");
                opcion = scanner.nextInt();

                if (opcion >= 1 && opcion <= ataques.size()) {
                    System.out.println("Has elegido usar " + ataques.get(opcion - 1).getNombre());
                    break; 
                } else {
                    throw new AtaqueNoDisponibleException("Opción no válida. Elige un número entre 1 y " + ataques.size() + ".");
                }
            } catch (InputMismatchException e) { 
                System.err.println("Entrada no válida. Por favor, ingresa únicamente un número.");            
                scanner.next(); 
            } catch (AtaqueNoDisponibleException e) { 
                System.err.println(e.getMessage());
            }
        } 
  
        scanner.nextLine();
        limpiarConsola(); 
        controlador.atacar(ataques.get(opcion - 1));
    }
    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    @Override
    public void mostrarPokemon(ArrayList<Pokemon> pokemon) {
        for (int i = 0; i < pokemon.size(); i++) {  
            String stats = "Pokémon: " + pokemon.get(i).getNombre() + " | Tipo: " + pokemon.get(i).getTipo().toString() + "\nNivel: " + String.valueOf(pokemon.get(i).getNivel()) + " | HP: " + String.valueOf(pokemon.get(i).getHp()) + "\nAtaque: " + String.valueOf(pokemon.get(i).getAtk()) + " | Ataque Especial: " + String.valueOf(pokemon.get(i).getAtkEs()) + "\nDefensa: " + String.valueOf(pokemon.get(i).getDf()) + " | Defensa Especial: " + String.valueOf(pokemon.get(i).getDfEs()) + "\nVelocidad: " + String.valueOf(pokemon.get(i).getVelocidad());
            System.out.println(stats);
            System.out.println("\n");
            limpiarConsola();
        }
        controlador.avanzarEscena();
        elegirAtaque(controlador.getOrden().get(0));
    }
    
    /*
      Muestra el mensaje de victoria y termina el programa.
     */
    @Override
    public void ganador(Entrenador entrenador) {
        System.out.println("\n¡El ganador es: " + entrenador.getNombre() + "!");
        System.exit(0);
    }

    @Override
    public boolean isError() {
        return false;
    }
    @Override
    public void continuar() {
        elegirAtaque(controlador.getOrden().get(0));
    }
    public void limpiarConsola() {
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine(); 
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}