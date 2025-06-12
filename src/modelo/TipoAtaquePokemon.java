package modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Enum que representa los diferentes tipos elementales de Pokémon.
 * Cada tipo tiene un conjunto de ataques y una tabla de efectividad contra otros tipos.
 */
public enum TipoAtaquePokemon {
    
    FUEGO(new String[]{
        "85 Infiernum (E)", "95 Inceniracion derretidora (E)", "110 Ultrafuego (F)", "70 Calor infrajuliano (E)", 
        "120 Explosion infernal (F)", "60 Lavabosa (E)", "105 Fueguisimo (F)", "75 Latigo lava (F)", 
        "90 Bola de fuego (E)", "100 Llamarada (E)"
    }),
    AGUA(new String[]{
        "100 Tsunami (E)", "80 Olas magnificas (F)", "95 Hydroespada (F)", "85 Ventisca helada (E)", 
        "75 Gotas abismales (E)", "70 Rafagahidro (E)", "110 Superchapuson (F)", "90 Cascadadon (F)", 
        "95 Bombaguaso (E)", "40 Chorrito de agua (F)"
    }),
    PLANTA(new String[]{
        "60 Humoextravenenoso (E)", "65 Lazos venenoso (E)", "85 Espiral de espinas (F)", "70 Semillerar (E)", 
        "80 Rodatronco (F)", "95 Raices opresivas (E)", "90 Enredadera (F)", "100 Cañon frutas (F)", 
        "75 Lluvia de hojas (E)", "85 Hojazo (F)"
    }),
    ELECTRICO(new String[]{
        "100 Rayolaser (E)", "80 Electrorapinito (F)", "90 Ferroinstataque (F)", "95 Impacto sobrelectrizante (E)", 
        "110 Megatormenta electrica (E)", "85 Corriente de rayos (F)", "70 Descarga (F)", "120 Electrimaximo (E)", 
        "100 Corrientazo (F)", "60 Chispas (F)"
    }),
    TIERRA(new String[]{
        "95 Montaña (E)", "90 Sumergimiento placoso (F)", "85 Bloque diamante (F)", "70 Lodo Hyperarenoso (E)", 
        "100 Lanzamontes (F)", "60 Enmurallar (E)", "80 Apreton de arcilla (F)", "65 Tierra sucias (E)", 
        "75 Rocal (F)", "50 Polvo (F)"
    });

    // Arreglo de ataques disponibles para el tipo
    private String[] ataques;

    // Tabla de efectividad: para cada tipo atacante, un mapa de efectividad contra cada tipo defensor
    private static final Map<TipoAtaquePokemon, Map<TipoAtaquePokemon, Float>> efectividad = new HashMap<>();

    /**
     * Constructor privado para asociar los ataques a cada tipo.
     * @param ataques Arreglo de nombres de ataques.
     */
    private TipoAtaquePokemon(String[] ataques) {
        this.ataques = ataques;
    }

    // Inicialización estática de la tabla de efectividad entre tipos
    static { 
        efectividad.put(PLANTA, new HashMap<>() {{
            put(PLANTA, 0.5f);
            put(ELECTRICO, 1.0f);
            put(FUEGO, 0.5f);
            put(TIERRA, 2.0f);
            put(AGUA, 0.5f);
        }});
        efectividad.put(ELECTRICO, new HashMap<>() {{
            put(PLANTA, 0.5f);
            put(ELECTRICO, 0.5f);
            put(FUEGO, 1.0f);
            put(TIERRA, 0.0f);
            put(AGUA, 2.0f);
        }});
        efectividad.put(FUEGO, new HashMap<>() {{
            put(PLANTA, 2.0f);
            put(ELECTRICO, 1.0f);
            put(FUEGO, 0.5f);
            put(TIERRA, 1.0f);
            put(AGUA, 0.5f);
        }});
        efectividad.put(TIERRA, new HashMap<>() {{
            put(PLANTA, 0.5f);
            put(ELECTRICO, 2.0f);
            put(FUEGO, 2.0f);
            put(TIERRA, 1.0f);
            put(AGUA, 1.0f);
        }});
        efectividad.put(AGUA, new HashMap<>() {{
            put(PLANTA, 2.0f);
            put(ELECTRICO, 1.0f);
            put(FUEGO, 2.0f);
            put(TIERRA, 2.0f);
            put(AGUA, 0.5f);
        }});
    }

    /**
     * Devuelve el arreglo de ataques asociados a este tipo.
     * @return Arreglo de nombres de ataques.
     */
    public String[] getAtaques() {
        return ataques;
    }

    /**
     * Devuelve la efectividad de este tipo atacante contra un tipo defensor.
     * @param tipoDefensor Tipo de Pokémon defensor.
     * @return Multiplicador de efectividad (ej: 2.0, 0.5, 1.0, 0.0).
     */
    public float getEfectividadContra(TipoAtaquePokemon tipoDefensor) {
        return efectividad.get(this).get(tipoDefensor);
    }
}