package modelo;

public abstract class SerVivo {

    // Esta es la clase Padre de Entrenador y Pokemon
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Constructor
    public SerVivo(String nombre){
        this.nombre = nombre;
    }
}