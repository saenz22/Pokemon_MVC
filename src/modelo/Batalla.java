
package modelo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Batalla {
    
    // Objetos globales
    static Random random = new Random(); // Generar aleatoriedad
    Entrenador e1, e2;
    ArrayList<Pokemon> disponibles1, disponibles2;

    public Batalla(Entrenador e1, Entrenador e2) {
        this.e1 = e1;
        this.e2 = e2;
        this.disponibles1 = e1.getEquipo();
        this.disponibles2 = e2.getEquipo();
    }

    public int turno(Pokemon atacante, Ataque ataqueElegido, Pokemon atacado) {

         // Si ambos tienen la misma velocidad, el primero que llege a la batalla, lo ataca
        atacante.atacar(ataqueElegido, atacado);
        if (atacado.getVivo() == false) {
            System.out.println("El pokemon " + atacado.getNombre() + " ha sido derrotado");
            // Verificar el pokemon de quién perdió
            if (disponibles1.contains(atacado)) {
                disponibles1.remove(atacado);
                if (disponibles1.isEmpty()) {
                    System.out.println("El entrenador " + e2.getNombre() + " ha ganado la batalla");
                    return 2; // el ganador es el entrenador 2
                } 
                else {
                    System.out.println("El entrenador " + e1.getNombre() + " tiene que elegir un nuevo pokemon");
                    return -2; // el entrenador1 tiene que elegir un nuevo pokemon
                }
            }
            else if (disponibles2.contains(atacado)) {
                disponibles2.remove(atacado);
                System.out.println("El pokemon " + atacado.getNombre() + " ha sido derrotado");
                if (disponibles2.isEmpty()) {
                    return 1; // El ganador es el entrenador 1
                } 
                else {
                    return -1; // el entrenador2 tiene que elegir un nuevo pokemon
                }
            }
        }
        System.out.println("El pokemon " + atacante.getNombre() + " ha atacado a " + atacado.getNombre());
        System.out.println("A " + atacado.getNombre() + " le quedan " + atacado.getHp()+"/"+atacado.getHPMAX() + " puntos de vida");
        return 0;
    }
    
     // Método principal para iniciar una batalla entre dos entrenadores
    public static Batalla instanciarBatalla(Entrenador e1, Entrenador e2) {
        return new Batalla(e1, e2);
    }

    public ArrayList<Pokemon> ordenBatalla(Pokemon atacante, Pokemon atacado) {

        if ((atacado.getVelocidad() > atacante.getVelocidad())) {
            return new ArrayList<Pokemon>(Arrays.asList(atacado, atacante));
        }
        return new ArrayList<Pokemon>(Arrays.asList(atacante, atacado));
    }
}   
