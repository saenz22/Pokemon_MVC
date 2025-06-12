package modelo;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import vista.VistaPokemonGUI;

// En lugar de hacer un ArrayList de ataques, se usó un HashSet que impide inherentemente que se repitan.

public class Pokemon extends SerVivo {

    // Inicializando atributos
    private TipoAtaquePokemon tipo;
    private ArrayList<Ataque> ataques = new ArrayList<>();
    private boolean vivo; // vivo se encanga de inhabilitar al Pokemon cuando hp=0
    private float hp;
    private final float HPMAX;
    private byte velocidad, nivel;
    private short df, dfEs, atk, atkEs;
    private ImageIcon imagen;
    
    // Getters y Setters

    public ImageIcon getImagen() {
        return imagen;
    }

    public void setImagen() {
        this.imagen = VistaPokemonGUI.ICONOS_TIPO.get(tipo);
    }
    
    public short getDf() {
        return df;
    }
    public short getDfEs() {
        return dfEs;
    }
    public short getAtk() {
        return atk;
    }
    public short getAtkEs() {
        return atkEs;
    }
    public TipoAtaquePokemon getTipo() {
        return tipo;
    }
    public float getHPMAX() {
        return HPMAX;
    }
    public void setTipo(TipoAtaquePokemon tipo) {
        this.tipo = tipo;
    }
    public ArrayList<Ataque> getAtaques() {
        return ataques;
    }
    public void setAtaques(ArrayList<Ataque> ataques) {
        this.ataques = ataques;
    }
    public float getHp() {
        return hp;
    }
    public void setHp(float hp) {
        this.hp = hp;
    }  
    public float getVelocidad() {
        return velocidad;
    }
    public void setVelocidad(byte velocidad) {
        this.velocidad = velocidad;
    }
    public boolean getVivo() {
        return vivo;
    }
    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }
    public byte getNivel() {
        return nivel;
    }
    public void setNivel(byte nivel) {
        this.nivel = nivel;
    }
    
    // Constructor
    public Pokemon(String nombre) {
        // Trayendo herencia: atributo nombre
        super(nombre);
        this.vivo = true;
        this.hp = aleatorio(500, 50);
        this.HPMAX = this.hp;
        this.imagen = null;
    }

    // Método para que el usuario elija los ataques de acuerdo con el tipo de pokemon elegido
    public static ArrayList<Ataque> capturarAtaques(TipoAtaquePokemon tipoPokemon) {

        // Inicializando variables locales
        String nombreAtk;
        float stab = 1.0f;
        TipoAtaque tipoAtk;
        ArrayList<Ataque> ataques = new ArrayList<>();
        String[] arsenal = tipoPokemon.getAtaques(); // Se asignan los ataques correspondientes

        // SELECCIÓN AUTOMÁTICA
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
        return ataques; // Se retorna ArrayList de ataques
    }

    // Uso del patrón "método de fábrica" para instanciar directamente al Pokemon en la clase
    public static Pokemon instanciarPokemon(String nombrePokemon) {

        Pokemon inicial = new Pokemon(nombrePokemon);
        // Se inicializan atributos aleatorios
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
        return inicial; // Se retorna un nuevo Pokemon
    }

    public float calculoDaño(float potencia, float stab, short tipoAtk, short dfEnemigo, TipoAtaquePokemon tipoEnemigo) {

        float variabilidad = (float) (Math.random() * (1f - 0.85f)) + 0.85f;
        // Fórmula oficial de cálculo de daño en Pokemon
        float modificador = stab * variabilidad * this.getTipo().getEfectividadContra(tipoEnemigo);
        float poder = (((2 * nivel / 5 + 2) * potencia * tipoAtk / dfEnemigo) / 50 + 2) * modificador;
        return poder;
    }

    // Método para calcular el daño recibido
    public void atacar(Ataque ataqueElegido, Pokemon enemigo){

        short dfEnemigo, tipoAtk;
        if (ataqueElegido.getTipo() == TipoAtaque.FISICO) {
            dfEnemigo = enemigo.getDf();
            tipoAtk = atk;
        } else {
            dfEnemigo = enemigo.getDfEs();
            tipoAtk = atkEs;
        }

        float poder = calculoDaño(ataqueElegido.getPoder(), ataqueElegido.getStab(), tipoAtk, dfEnemigo, enemigo.getTipo());

        if(poder >= enemigo.getHp()){
            // Si el poder derrota al Pokemon
            enemigo.setHp(0);
            enemigo.setVivo(false);
        } else {
            // Se resta poderAtk a hp del Pokemon
            float resta = enemigo.getHp() - poder;
            enemigo.setHp(resta);
        }
    }

    private static short aleatorio(int max, int min) {
        return (short) (Math.random() * (max - min + 1) + min);
    }

    private static boolean yaElegido(String nombreAtk, ArrayList<Ataque> ataques) {
        return ataques.stream().anyMatch(a -> nombreAtk.contains(a.getNombre()));
    }
}