

public class Ataque {

    // Atributos
    private String nombre;
    private float poder, stab;
    private TipoAtaque tipo;

    public float getStab() {
        return stab;
    }
    public void setStab(float stab) {
        this.stab = stab;
    }

    public TipoAtaque getTipo() {
        return tipo;
    }
    public void setTipo(TipoAtaque tipo) {
        this.tipo = tipo;
    }
    // Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public float getPoder() {
        return poder;
    }
    public void setPoder(float poder) {
        this.poder = poder;
    }

    // Constructor
    public Ataque(String nombre, float poder, TipoAtaque tipo, float stab){
        this.nombre = nombre;
        this.poder = poder;
        this.tipo = tipo;
        this.stab = stab;
    }
}