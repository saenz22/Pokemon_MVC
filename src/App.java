import controlador.Controlador;

public class App {
    public static void main(String[] args) throws Exception {
        Controlador controlador;

        // Para probar el juego, ingresa en el parámetro del controlador
        // true si deseas la aventura visual o false si deseas la aventura por consola
        // Para avanzar entre escenas, presiona Enter
        controlador = new Controlador(true);
        // Llama a un método para usar la variable y evitar la advertencia
    }
}