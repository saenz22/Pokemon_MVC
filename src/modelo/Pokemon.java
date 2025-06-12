package modelo;

import java.util.ArrayList;
import javax.swing.ImageIcon;
import vista.VistaPokemonGUI;

/**
 * Clase que representa un Pokémon en el sistema.
 * Hereda de SerVivo e incluye atributos y métodos para gestionar
 * estadísticas, ataques y lógica de combate.
 */
public class Pokemon extends SerVivo {

    // Tipo elemental del Pokémon (FUEGO, AGUA, etc.)
    private TipoAtaquePokemon tipo;
    // Lista de ataques disponibles para el Pokémon
    private ArrayList<Ataque> ataques = new ArrayList<>();
    // Indica si el Pokémon está habilitado para combatir
    private boolean vivo;
    // Puntos de vida actuales
    private float hp;
    // Puntos de vida máximos (constante)
    private final float HPMAX;
    // Estadísticas de combate
    private byte velocidad, nivel;
    private short df, dfEs, atk, atkEs;
    // Imagen asociada al Pokémon (para la GUI)
    private ImageIcon imagen;

    // ===================== Getters y Setters =====================

    /**
     * Devuelve la imagen asociada al Pokémon.
     */
    public ImageIcon getImagen() {
        return imagen;
    }

    /**
     * Asigna la imagen correspondiente según el tipo del Pokémon.
     */
    public void setImagen() {
        this.imagen = VistaPokemonGUI.ICONOS_TIPO.get(tipo);
    }

    public short getDf() { return df; }
    public short getDfEs() { return dfEs; }
    public short getAtk() { return atk; }
    public short getAtkEs() { return atkEs; }
    public TipoAtaquePokemon getTipo() { return tipo; }
    public float getHPMAX() { return HPMAX; }
    public void setTipo(TipoAtaquePokemon tipo) { this.tipo = tipo; }
    public ArrayList<Ataque> getAtaques() { return ataques; }
    public void setAtaques(ArrayList<Ataque> ataques) { this.ataques = ataques; }
    public float getHp() { return hp; }
    public void setHp(float hp) { this.hp = hp; }
    public float getVelocidad() { return velocidad; }
    public void setVelocidad(byte velocidad) { this.velocidad = velocidad; }
    public boolean getVivo() { return vivo; }
    public void setVivo(boolean vivo) { this.vivo = vivo; }
    public byte getNivel() { return nivel; }
    public void setNivel(byte nivel) { this.nivel = nivel; }

    // ===================== Constructores =====================

    /**
     * Constructor principal. Inicializa el Pokémon con nombre y valores aleatorios.
     * @param nombre Nombre del Pokémon.
     */
    public Pokemon(String nombre) {
        super(nombre);
        this.vivo = true;
        this.hp = aleatorio(100, 50);
        this.HPMAX = this.hp;
        this.imagen = null;
    }

    // ===================== Métodos de Fábrica y Utilidad =====================

    /**
     * Permite seleccionar automáticamente 4 ataques válidos para un tipo de Pokémon.
     * @param tipoPokemon Tipo elemental del Pokémon.
     * @return Lista de ataques seleccionados.
     */
    public static ArrayList<Ataque> capturarAtaques(TipoAtaquePokemon tipoPokemon) {
        String nombreAtk;
        float stab = 1.0f;
        TipoAtaque tipoAtk;
        ArrayList<Ataque> ataques = new ArrayList<>();
        String[] arsenal = tipoPokemon.getAtaques();

        // Selección automática de ataques únicos
        while (ataques.size() < 4) {
            nombreAtk = arsenal[(short) (Math.random() * (arsenal.length))];
            if (!yaElegido(nombreAtk, ataques)) {
                if (nombreAtk.endsWith(" (E)")) {
                    tipoAtk = TipoAtaque.ESPECIAL;
                    stab = 1.5f;
                    nombreAtk = nombreAtk.replace(" (E)", "").trim();
                } else {
                    tipoAtk = TipoAtaque.FISICO;
                    nombreAtk = nombreAtk.replace(" (F)", "").trim();
                }
                String poderStr = nombreAtk.replaceAll("[^0-9]", "");
                byte poderAtk = Byte.parseByte(poderStr);
                nombreAtk = nombreAtk.replaceAll("^[0-9]+", "").trim();
                ataques.add(new Ataque(nombreAtk, poderAtk, tipoAtk, stab));
            }
        }
        return ataques;
    }

    /**
     * Método de fábrica para crear un Pokémon con estadísticas y ataques aleatorios.
     * @param nombrePokemon Nombre del Pokémon.
     * @return Instancia de Pokémon inicializada.
     */
    public static Pokemon instanciarPokemon(String nombrePokemon) {
        Pokemon inicial = new Pokemon(nombrePokemon);
        // Inicialización aleatoria de estadísticas
        inicial.atk = aleatorio(100, 10);
        inicial.atkEs = aleatorio(100, 10);
        inicial.df = aleatorio(100, 10);
        inicial.dfEs = aleatorio(100, 10);
        inicial.velocidad = (byte) aleatorio(120, 10);
        inicial.nivel = (byte) aleatorio(100, 1);
        // Inicialización de tipo y ataques
        byte tipoElegido = (byte) (Math.random() * (TipoAtaquePokemon.values().length));
        inicial.setTipo(TipoAtaquePokemon.values()[tipoElegido]);
        inicial.setAtaques(Pokemon.capturarAtaques(inicial.getTipo()));
        inicial.setImagen();
        return inicial;
    }

    // ===================== Lógica de Combate =====================

    /**
     * Calcula el daño infligido a un Pokémon enemigo según la fórmula oficial.
     * @param potencia Potencia base del ataque.
     * @param stab Bonificación por afinidad de tipo.
     * @param tipoAtk Ataque físico o especial del atacante.
     * @param dfEnemigo Defensa física o especial del enemigo.
     * @param tipoEnemigo Tipo elemental del enemigo.
     * @return Daño calculado.
     */
    public float calculoDaño(float potencia, float stab, short tipoAtk, short dfEnemigo, TipoAtaquePokemon tipoEnemigo) {
        float variabilidad = (float) (Math.random() * (1f - 0.85f)) + 0.85f;
        float modificador = stab * variabilidad * this.getTipo().getEfectividadContra(tipoEnemigo);
        float poder = (((2 * nivel / 5 + 2) * potencia * tipoAtk / dfEnemigo) / 50 + 2) * modificador;
        return poder;
    }

    /**
     * Ejecuta un ataque sobre un Pokémon enemigo y actualiza su estado.
     * @param ataqueElegido Ataque seleccionado.
     * @param enemigo Pokémon objetivo.
     */
    public void atacar(Ataque ataqueElegido, Pokemon enemigo) {
        short dfEnemigo, tipoAtk;
        if (ataqueElegido.getTipo() == TipoAtaque.FISICO) {
            dfEnemigo = enemigo.getDf();
            tipoAtk = atk;
        } else {
            dfEnemigo = enemigo.getDfEs();
            tipoAtk = atkEs;
        }

        float poder = calculoDaño(ataqueElegido.getPoder(), ataqueElegido.getStab(), tipoAtk, dfEnemigo, enemigo.getTipo());

        if (poder >= enemigo.getHp()) {
            enemigo.setHp(0);
            enemigo.setVivo(false);
        } else {
            float resta = enemigo.getHp() - poder;
            enemigo.setHp(resta);
        }
    }

    // ===================== Métodos Auxiliares =====================

    /**
     * Genera un valor aleatorio entre min y max (inclusive).
     */
    private static short aleatorio(int max, int min) {
        return (short) (Math.random() * (max - min + 1) + min);
    }

    /**
     * Verifica si un ataque ya fue seleccionado.
     * @param nombreAtk Nombre del ataque.
     * @param ataques Lista de ataques ya elegidos.
     * @return true si ya fue elegido, false en caso contrario.
     */
    private static boolean yaElegido(String nombreAtk, ArrayList<Ataque> ataques) {
        return ataques.stream().anyMatch(a -> nombreAtk.contains(a.getNombre()));
    }
}