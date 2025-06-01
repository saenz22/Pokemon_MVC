
import vista.*;
import controlador.Controlador;

public class App {
    public static void main(String[] args) throws Exception {
        Controlador controlador;
        VistaPokemon vista = new VistaPokemonGUI();
        controlador = new Controlador(vista, true);
        controlador.actualizarEscena();
    }
}