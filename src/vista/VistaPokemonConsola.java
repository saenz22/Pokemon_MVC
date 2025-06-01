package vista;

import controlador.Controlador;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.InputMismatchException;

public class VistaPokemonConsola implements VistaPokemon {
    private byte contadorEscena;
    private Scanner scanner;
    private String nombre1, nombre2, pokemon1, pokemon2, pokemon3;
    private Controlador controlador;
    ArrayList<String> listaPokemones = new ArrayList<>();
    ArrayList<String> listaEntrenadores = new ArrayList<>();

    public VistaPokemonConsola() {
        this.contadorEscena = 0;
        scanner = new Scanner(System.in);
        this.nombre1 = "";
        this.nombre2 = "";
        this.pokemon1 = "";
        this.pokemon2 = "";
        this.pokemon3 = "";
    }

    // Hacer souts mucho más descriptivos y estéticos, sería bueno agregar un await o algo parecido, métodos para que se sienta más ameno
    
    @Override
    public void bienvenido() {
        System.out.println(""" 
                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!""");
    }
    @Override
    public ArrayList<String> entrenadores() {
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

            } catch (NoSuchElementException e) {
                System.err.println("Error: No se encontró más entrada. Saliendo...");
                break;
            } catch (IllegalStateException e) {
                System.err.println("Error: El scanner ha sido cerrado. Saliendo...");
                break;
            }
        }
        listaEntrenadores.add(nombre1);
        listaEntrenadores.add(nombre2);

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
        return listaEntrenadores;
    }

    @Override
    public ArrayList<String> pokemones(String nombre) {
        boolean PokemonValidos = false;
        listaPokemones.clear();
        System.out.println(nombre + ", ¡Nombra a tus Pokémon!");
        while (!PokemonValidos) {
            try {
                System.out.print("Ingrese el nombre del primer Pokémon: ");
                this.pokemon1 = scanner.nextLine().trim();
                if (pokemon1.isEmpty()) {
                    System.out.println("Error: El nombre del primer Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }

                System.out.print("Ingrese el nombre del segundo Pokémon: ");
                this.pokemon2 = scanner.nextLine().trim();
                if (pokemon2.isEmpty()) {
                    System.out.println("Error: El nombre del segundo Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }

                System.out.print("Ingrese el nombre del tercer Pokémon: ");
                this.pokemon3 = scanner.nextLine().trim();
                if (pokemon3.isEmpty()) {
                    System.out.println("Error: El nombre del tercer Pokémon no puede estar vacío.");
                    scanner.nextLine();
                    continue;
                }

                PokemonValidos = true; // Si todos los nombres no están vacíos, salimos del bucle

                System.out.println("Los Pokémon ingresados son:");
                System.out.println("1. " + pokemon1);
                System.out.println("2. " + pokemon2);
                System.out.println("3. " + pokemon3);
                
            } catch (NoSuchElementException e) {
                System.err.println("Error: No se encontró más entrada. Puede que la entrada haya sido interrumpida.");
                break; // O podrías decidir salir del programa aquí
            }
        }
        listaPokemones.add(pokemon1);
        listaPokemones.add(pokemon2);
        listaPokemones.add(pokemon3);
        return listaPokemones; // Devuelve un ArrayList con los nombres de los Pokémon{pokemon1, pokemon2, pokemon3};
    }

    @Override
    public Pokemon elegirPokemon(Entrenador entrenador) {
        ArrayList<Pokemon> pokemones = entrenador.getEquipo();
        int opcion = 0; 
        System.out.println("Elige un Pokémon:" + entrenador.getNombre());
        for (int i = 0; i < pokemones.size(); i++) {
            System.out.println((i + 1) + ". " + pokemones.get(i).getNombre());
        }
        try {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> System.out.println("Has elegido a " + pokemones.get(0).getNombre()); 
                    case 2 -> System.out.println("Has elegido a " + pokemones.get(1).getNombre());
                    case 3 -> System.out.println("Has elegido a " + pokemones.get(2).getNombre());
                    default -> System.out.println("Opción no válida. Elige un número entre 1 y 3.");
                }
            } else {
                System.out.println("Entrada no válida. Por favor, ingresa un número.");
                scanner.nextLine();
                }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida. Por favor, ingresa un número.");
            scanner.nextLine();
        }
        return pokemones.get(opcion-1);
    }
    @Override
    public Ataque elegirAtaque(Pokemon pokemon) {
        ArrayList<Ataque> ataques = pokemon.getAtaques();
        int opcion = 0; 
        System.out.println("Elige un Ataque para : " + pokemon.getNombre());
        for (int i = 0; i < ataques.size(); i++) {
            System.out.println((i + 1) + ". " + ataques.get(i).getNombre() + " (Poder: " + ataques.get(i).getPoder() + ")");
        }
        try {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                switch (opcion) {
                    case 1 -> System.out.println("Has elegido a " + ataques.get(0).getNombre()); 
                    case 2 -> System.out.println("Has elegido a " + ataques.get(1).getNombre());
                    case 3 -> System.out.println("Has elegido a " + ataques.get(2).getNombre());
                    case 4 -> System.out.println("Has elegido a " + ataques.get(3).getNombre());
                    default -> System.out.println("Opción no válida. Elige un número entre 1 y 4.");
                }
            } else {
                System.out.println("Entrada no válida. Por favor, ingresa un número.");
                scanner.nextLine();
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Entrada no válida. Por favor, ingresa un número.");
            scanner.next();
        }
        return ataques.get(opcion-1);
    }
    @Override
    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }
    @Override
    public void mostrarPokemon(Pokemon pokemon) {
        System.out.println("Pokémon: " + pokemon.getNombre());
        System.out.println("Tipo: " + pokemon.getTipo());
        System.out.println("Nivel: " + String.valueOf(pokemon.getNivel()));
        System.out.println("HP: " + String.valueOf(pokemon.getHp()));
        System.out.println("ATAQUE: " + String.valueOf(pokemon.getAtk()));
        System.out.println("DEFENSA: " + String.valueOf(pokemon.getDf()));
        System.out.println("ATAQUE ESPECIAL: " + String.valueOf(pokemon.getAtkEs()));
        System.out.println("DEFENSA ESPECIAL: " + String.valueOf(pokemon.getDfEs()));
        System.out.println("VELOCIDAD: " +  String.valueOf(pokemon.getVelocidad()));
    }
    @Override
    public void ganador(Entrenador entrenador) {
        System.out.println("El ganador es: " + entrenador.getNombre());
    }
    @Override
    public byte getEscena() {
        return contadorEscena;
    }
    @Override
    public void setEscena(byte b) {
        this.contadorEscena = b;
    }
    @Override
    public void mostrarResultadoCombate(Pokemon pokemon ) {
        if (pokemon.getHp() <= 0) {
            System.out.println(pokemon.getNombre() + " ha sido derrotado.");
        } else {
            System.out.println(pokemon.getNombre() + " ha sobrevivido con " + pokemon.getHp() + " HP restantes.");
        }
    }

    @Override
    public boolean isError() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isError'");
    }

    @Override
    public void pokemones() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'pokemones'");
    }

    @Override
    public void mostrarPokemon(ArrayList<Pokemon> pokemon) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mostrarPokemon'");
    }
}