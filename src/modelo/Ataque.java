package modelo;

/**
 * Clase que representa un ataque que puede realizar un Pokémon.
 * Incluye información sobre el nombre, poder, tipo y bonificación STAB.
 */
public class Ataque {

    // Nombre del ataque
    private String nombre;
    // Poder base del ataque
    private float poder;
    // Bonificación por afinidad de tipo (STAB)
    private float stab;
    // Tipo de ataque (FISICO o ESPECIAL)
    private TipoAtaque tipo;

    /**
     * Obtiene la bonificación STAB del ataque.
     * @return Valor de STAB.
     */
    public float getStab() {
        return stab;
    }

    /**
     * Establece la bonificación STAB del ataque.
     * @param stab Valor de STAB.
     */
    public void setStab(float stab) {
        this.stab = stab;
    }

    /**
     * Obtiene el tipo del ataque (FISICO o ESPECIAL).
     * @return Tipo de ataque.
     */
    public TipoAtaque getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo del ataque.
     * @param tipo Tipo de ataque.
     */
    public void setTipo(TipoAtaque tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del ataque.
     * @return Nombre del ataque.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del ataque.
     * @param nombre Nombre del ataque.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el poder base del ataque.
     * @return Poder del ataque.
     */
    public float getPoder() {
        return poder;
    }

    /**
     * Establece el poder base del ataque.
     * @param poder Valor de poder.
     */
    public void setPoder(float poder) {
        this.poder = poder;
    }

    /**
     * Constructor para inicializar un ataque con todos sus atributos.
     * @param nombre Nombre del ataque.
     * @param poder Poder base del ataque.
     * @param tipo Tipo de ataque (FISICO o ESPECIAL).
     * @param stab Bonificación STAB.
     */
    public Ataque(String nombre, float poder, TipoAtaque tipo, float stab){
        this.nombre = nombre;
        this.poder = poder;
        this.tipo = tipo;
        this.stab = stab;
    }
}