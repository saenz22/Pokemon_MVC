
package vista;

import controlador.Controlador;
import modelo.Entrenador;
import modelo.Pokemon;
import modelo.TipoAtaquePokemon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


public class VistaPokemonGUI extends JFrame implements ActionListener, KeyListener, VistaPokemon { // Implementamos ActionListener
    
 
    public String getNombre1() {
        return nombre1;
    }


    public String getNombre2() {
        return nombre2;
    }

    public String getPokemon1() {
        return pokemon1;
    }

    public String getPokemon2() {
        return pokemon2;
    }

    public String getPokemon3() {
        return pokemon3;
    }

    public byte getEscena() {
        return (byte) currentPanel;
    }
    public boolean isError() {
        return error;
    }

    private boolean error = false;

    private Timer timer; // Declaramos el Timer como un campo de la clase
    private int currentPanel = 0;
    

    private String nombre1 = "";
    private String nombre2 = "";
    private String pokemon1 = "";
    private String pokemon2 = "";
    private String pokemon3 = "";
  

    private JTextField jugador1Field = new JTextField();
    private JTextField jugador2Field = new JTextField();
    private JTextField poke1Field = new JTextField();
    private JTextField poke2Field = new JTextField();
    private JTextField poke3Field = new JTextField();
   
    Controlador controlador;


    public VistaPokemonGUI() {
        setTitle("Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(605, 327);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true); // Hacemos visible la ventana

        // Creamos un temporizador que se activará después de 3 segundos
        timer = new Timer(3000, this); // El temporizador escucha a esta clase (WindowBuilder)
        timer.setRepeats(false);
        timer.start(); // Iniciamos el temporizador
        
    }


    private JPanel showFirstPanel() {
        currentPanel = 1; // Cambiamos el panel actual a 1
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.decode("#1e1e1e"));

        Font font = new Font("Monospaced", Font.PLAIN, 15);
        Font boldFont = new Font("Monospaced", Font.BOLD, 20);

        JLabel element1 = new JLabel("©2025");
        element1.setBounds(200, 90, 60, 23);
        element1.setFont(font);
        element1.setForeground(Color.decode("#D9D9D9"));
        panel.add(element1);

        JLabel element2 = new JLabel("Pokémon");
        element2.setBounds(300, 80, 100, 30);
        element2.setFont(boldFont);
        element2.setForeground(Color.decode("#D9D9D9"));
        panel.add(element2);

        JLabel element4 = new JLabel("©1995-2025");
        element4.setBounds(200, 120, 106, 17);
        element4.setFont(font);
        element4.setForeground(Color.decode("#D9D9D9"));
        panel.add(element4);

        JLabel element5 = new JLabel("Nintendo");
        element5.setBounds(300, 110, 106, 30);
        element5.setFont(boldFont);
        element5.setForeground(Color.decode("#D9D9D9"));
        panel.add(element5);

        JLabel element6 = new JLabel("©1995-2025");
        element6.setBounds(200, 150, 106, 17);
        element6.setFont(font);
        element6.setForeground(Color.decode("#D9D9D9"));
        panel.add(element6);

        JLabel element7 = new JLabel("Univallunos Inc");
        element7.setBounds(300, 140, 185, 30);
        element7.setFont(boldFont);
        element7.setForeground(Color.decode("#D9D9D9"));
        panel.add(element7);

        JLabel element9 = new JLabel("©1995-2025");
        element9.setBounds(200, 180, 106, 17);
        element9.setFont(font);
        element9.setForeground(Color.decode("#D9D9D9"));
        panel.add(element9);

        JLabel element10 = new JLabel("GAME FREAK inc");
        element10.setBounds(300, 170, 191, 31);
        element10.setFont(boldFont);
        element10.setForeground(Color.decode("#D9D9D9"));
        panel.add(element10);

        this.add(panel); // Usamos 'this' porque es una instancia de JFrame (WindowBuilder)

        return panel;
    }

    public void switchToNextPanel(JPanel panel) {
        getContentPane().removeAll(); // Limpiar la ventana
        add(panel); // Añadir el nuevo panel
        if ( currentPanel != 1) {
            panel.setFocusable(true); // Habilitar el foco para el panel
            panel.requestFocusInWindow(); // Solicitar el foco al panel
        }
        if (currentPanel == 5) {
           poke1Field.setText(""); 
           poke2Field.setText(""); 
           poke3Field.setText("");
           pokemon1 = "";
           pokemon2 = "";
           pokemon3 = "";
        }
        this.revalidate(); // Revalidar la ventana para que se actualice
        this.repaint();
    }

    public JPanel showSecondPanel() {
        currentPanel = 2; // Cambiamos el panel actual a 2
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        secondPanel.setBackground(new Color(10, 20, 48));

        secondPanel.addKeyListener(this);

        // Crear el área de texto
        String texto = """

                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!
                """;
        
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200); // Posicionamos el cuadro de texto
        textArea.setEditable(false); // No editable
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0)); // Sin cursor visible// Márgenes dentro del área de texto
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true)); // Borde alrededor

        // Añadir el área de texto al panel
        secondPanel.add(textArea);

        // Crear la flecha roja
        JLabel flecha = new JLabel("Presiona Enter para continuar...");
        flecha.setForeground(Color.RED);
        flecha.setBounds(350, 250, 500, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 10)); // Fuente de la flecha

        // Añadir la flecha encima del cuadro de texto
        secondPanel.add(flecha);

        // Añadir el secondPanel al JFrame

        return secondPanel; // Usamos 'this' para añadir el segundo panel
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Este método se ejecutará cuando el temporizador haya terminado (3 segundos)
            switchToNextPanel(showSecondPanel()); 
             // Mostrar el segundo panel
        
        // Cambiar al siguiente panel
    }


    public JPanel showThirdPanel() {
        currentPanel = 3; // Cambiamos el panel actual a 3
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(null);
        thirdPanel.setBackground(new Color(10, 20, 48));

        thirdPanel.addKeyListener(this);
      
        JLabel label1 = new JLabel("Ingrese el nombre del entrenador 1:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label1.setBounds(150, 60, 360, 25);
        thirdPanel.add(label1);

        jugador1Field.setBounds(210, 100, 200, 25);
        jugador1Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador1Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador1Field.setBackground(Color.WHITE);
        jugador1Field.setForeground(Color.BLACK);
        jugador1Field.addKeyListener(this); // Añadir el KeyListener al campo de texto

        thirdPanel.add(jugador1Field);

        JLabel label2 = new JLabel("Ingrese el nombre del entrenador 2:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label2.setBounds(150, 140, 360, 25);
        thirdPanel.add(label2);

       
        jugador2Field.setBounds(210, 180, 200, 25);
        jugador2Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador2Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador2Field.setBackground(Color.WHITE);
        jugador2Field.setForeground(Color.BLACK);
        jugador2Field.addKeyListener(this); // Añadir el KeyListener al campo de texto 
        thirdPanel.add(jugador2Field);
        // Crear la flecha roja
        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 230, 30, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha
        // Añadir la flecha encima del cuadro de texto
        thirdPanel.add(flecha);
    

        return thirdPanel; // Usamos 'this' para añadir el tercer panel
        // Código para el tercer panel
        
    }

    public JPanel showFourthPanel() {
        currentPanel = 4; // Cambiamos el panel actual a 4
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(null);
        fourthPanel.setBackground(new Color(10, 20, 48));
       

       fourthPanel.addKeyListener(this);

        // Crear el área de texto
     
        String texto1 = """

                        Bienvenidos a Pokémon {jugador1} y 
                        {jugador2} les espera un gran 
                        desafío en su aventura.

                        Sus pokemones serán asignados 
                        aleatoriamente,y tendrán que 
                        enfrentarse para demostrar 
                        quién es el mejor entrenador.
                """;
        String texto = texto1.replace("{jugador1}", nombre1).replace("{jugador2}", nombre2);
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200); // Posicionamos el cuadro de texto
        textArea.setEditable(false); // No editable
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0)); // Sin cursor visible// Márgenes dentro del área de texto
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true)); // Borde alrededor

        // Añadir el área de texto al panel
       fourthPanel.add(textArea);

        // Crear la flecha roja
        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30); // Nueva posición razonable de la flecha
        flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha

        // Añadir la flecha encima del cuadro de texto
       fourthPanel.add(flecha);

        // Añadir elfourthPanel al JFrame


        return fourthPanel; // Usamos 'this' para añadir el cuarto panel
    }

    public JPanel showFifthPanel(String nombre) {
    currentPanel = 5; // Cambiamos el panel actual a 5
    JPanel pokemonPanel = new JPanel();
    pokemonPanel.setLayout(null);
    pokemonPanel.setBackground(new Color(10, 20, 48)); // Fondo oscuro

    pokemonPanel.addKeyListener(this);
    // Título
    String solicitarpokemon = "¡Selecciona tus pokemones " + nombre + "!";
    JLabel titulo = new JLabel(solicitarpokemon);
    titulo.setForeground(Color.WHITE);
    titulo.setFont(new Font("Monospaced", Font.BOLD, 18));
    titulo.setBounds(120, 20, 500, 30);
    pokemonPanel.add(titulo);

    // Etiqueta y campo para Pokemon 1
    JLabel label1 = new JLabel("Pokemon 1");
    label1.setForeground(Color.WHITE);
    label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
    label1.setBounds(200, 60, 200, 25);
    pokemonPanel.add(label1);

    // Antes de agregar remover listeners previos
    for (KeyListener kl : poke1Field.getKeyListeners()) poke1Field.removeKeyListener(kl); 
    for (KeyListener kl : poke2Field.getKeyListeners()) poke2Field.removeKeyListener(kl);
    for (KeyListener kl : poke3Field.getKeyListeners()) poke3Field.removeKeyListener(kl);

    poke1Field.setBounds(200, 90, 200, 25);
    poke1Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
    poke1Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
    poke1Field.setBackground(Color.WHITE);
    poke1Field.setForeground(Color.BLACK);
    poke1Field.addKeyListener(this); // Añadir el KeyListener al campo de texto
    pokemonPanel.add(poke1Field);

    // Etiqueta y campo para Pokemon 2
    JLabel label2 = new JLabel("Pokemon 2");
    label2.setForeground(Color.WHITE);
    label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
    label2.setBounds(200, 120, 200, 25);
    pokemonPanel.add(label2);

   
    poke2Field.setBounds(200, 150, 200, 25);
    poke2Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
    poke2Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
    poke2Field.setBackground(Color.WHITE);
    poke2Field.setForeground(Color.BLACK);
    poke2Field.addKeyListener(this); // Añadir el KeyListener al campo de texto
    pokemonPanel.add(poke2Field);

    // Etiqueta y campo para Pokemon 3
    JLabel label3 = new JLabel("Pokemon 3");
    label3.setForeground(Color.WHITE);
    label3.setFont(new Font("Monospaced", Font.PLAIN, 14));
    label3.setBounds(200, 180, 200, 25);
    pokemonPanel.add(label3);

    poke3Field.setBounds(200, 210, 200, 25);
    poke3Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
    poke3Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
    poke3Field.setBackground(Color.WHITE);
    poke3Field.setForeground(Color.BLACK);
    poke3Field.addKeyListener(this); // Añadir el KeyListener al campo de texto
    pokemonPanel.add(poke3Field);

    // Crear la flecha roja
    JLabel flecha = new JLabel("▼");
    flecha.setForeground(Color.RED);
    flecha.setBounds(430, 250, 30, 30); // Nueva posición razonable de la flecha
    flecha.setFont(new Font("Arial", Font.BOLD, 20)); // Fuente de la flecha    

    // Añadir la flecha encima del cuadro de texto currentPanel = 7
    pokemonPanel.add(flecha);

    

    return pokemonPanel;
}

   
     public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO;

    static {
        ICONOS_TIPO = new HashMap<>();
        ICONOS_TIPO.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmander.png")));
        ICONOS_TIPO.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasur.png")));
        ICONOS_TIPO.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/picachu.png")));
        ICONOS_TIPO.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/diglet.png")));
        ICONOS_TIPO.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtle.png")));
    }
    

    public JPanel showSixthPanel(Pokemon pokemon ) {
        currentPanel = 6; // Cambiamos el panel actual a 6
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xF8D070));

        JPanel franjamorada = new JPanel();
        franjamorada.setBackground(new Color(0xE0B0FF));
        franjamorada.setBounds(0, 0, 250, 30);
        panel.add(franjamorada);

        String nombreConNivel = "Nv. " + pokemon.getNivel() + "/" + pokemon.getNombre() + " (" + pokemon.getTipo() + ")";
        JLabel nombrLabel = new JLabel(nombreConNivel, JLabel.LEFT);
        nombrLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
        nombrLabel.setForeground(Color.WHITE);
        nombrLabel.setBounds(0, 0, 250, 20);
        franjamorada.add(nombrLabel);

        JPanel cajaBlanca = new JPanel();
        cajaBlanca.setBackground(Color.WHITE);
        cajaBlanca.setBounds(0, 30, 250, 140);
        cajaBlanca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

        String[][] Stats = {
            {"HP", String.valueOf(pokemon.getHp())},
            {"ATAQUE", String.valueOf(pokemon.getAtk())},
            {"DEFENSA", String.valueOf(pokemon.getDf())},
            {"ATAQUE ESPECIAL", String.valueOf(pokemon.getAtkEs())},
            {"DEFENSA ESPECIAL", String.valueOf(pokemon.getDfEs())},
            {"VELOCIDAD", String.valueOf(pokemon.getVelocidad())}
            
        };


        for (int i = 0; i < Stats.length; i++) {
            JLabel label = new JLabel(Stats[i][0], JLabel.CENTER);
            label.setFont(new Font("Monospaced", Font.PLAIN, 15));
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(88, 88, 88));
            label.setBounds(270, i * 25 + 10 , 150, 15);
            panel.add(label);
            JLabel valores = new JLabel(Stats[i][1], JLabel.CENTER);
            valores.setFont(new Font("Monospaced", Font.PLAIN, 15));
            valores.setForeground(Color.BLACK);
            valores.setOpaque(    true);
            valores.setBackground( new Color(240, 225, 160));
            valores.setBounds(450, i * 25 + 10 , 130, 15);
            panel.add(valores);
        }

        String [][] ataques = new String[4][4];
        for (int i = 0; i < pokemon.getAtaques().size(); i++) {
            ataques[i][0] = pokemon.getAtaques().get(i).getNombre();
            ataques[i][1] = String.valueOf(pokemon.getAtaques().get(i).getPoder());
        }

        for (int i = 0; i < ataques.length; i++) {
            JLabel label = new JLabel(ataques[i][0], JLabel.CENTER);
            label.setFont(new Font("Monospaced", Font.PLAIN, 15));
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(88, 88, 88));
            label.setBounds(5, i * 30 + 180 , 150, 15);
            panel.add(label);

            JLabel poder = new JLabel("PODER. " );
            poder.setFont(new Font("Monospaced", Font.PLAIN, 18));
            poder.setForeground(Color.BLACK);
            poder.setOpaque(true);
            poder.setBackground(new Color(240, 200, 80));
            poder.setBounds(155, i * 30 + 175 , 210, 18);

            panel.add(poder);

            JLabel valores = new JLabel(ataques[i][1], JLabel.CENTER);
            valores.setFont(new Font("Monospaced", Font.PLAIN, 15));
            valores.setForeground(Color.BLACK);
            valores.setOpaque(true);
            valores.setBackground(new Color(240, 225, 160));
            valores.setBounds(365, i * 30 + 180 , 220, 15);
            panel.add(valores);


        }

        ImageIcon imagen = ICONOS_TIPO.get(pokemon.getTipo());
        Image img = imagen.getImage().getScaledInstance(210, 120, Image.SCALE_SMOOTH);
        JLabel labelImagen = new JLabel(new ImageIcon(img));
        labelImagen.setBounds(20,40,210, 120);
        panel.add(labelImagen);
        panel.add(cajaBlanca);
        panel.setLayout(null);
        panel.setFocusable(true);
        panel.addKeyListener(this);
    
       return panel; // Usamos 'this' para añadir el sexto panel
    }

     public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO_DEFENSOR;

    static {
        ICONOS_TIPO_DEFENSOR = new HashMap<>();
        ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmanderFrente.png")));
        ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasurFrente.png")));
        ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/pikachuFrente.png")));
        ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/diglettFrente.png")));
        ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtleFrente.png")));
    }

    
     public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO_ATACANTE;

    static {
        ICONOS_TIPO_ATACANTE = new HashMap<>();
        ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmanderEspalda.png")));
        ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasurEspalda.png")));
        ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/pikachuEspalda.png")));
        ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/digtletEspalda.png")));
        ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtleEspalda.png")));
    }

    boolean seleccionados[] = {false, false};

    public JPanel mostrarTabla(Entrenador e1, Entrenador e2) {
        seleccionados[0] = false;
        seleccionados[1] = false;
        System.out.println("Entramos a mostrar tabla");
        System.out.println(seleccionados[0] + " " + seleccionados[1]);
        JPanel fondo = new JPanel(new GridLayout(1, 2, 5, 5));
        fondo.setBackground(new Color(25, 95, 95));
        fondo.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // ActionListener elegirPokemon
        fondo.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (seleccionados[0] && seleccionados[1]) {
                        System.out.println("Pulsado enter");
                        controlador.ordenarContrincantes();
                        switchToNextPanel(showSeventhPanel(controlador.getOrden().get(0), controlador.getOrden().get(1)));    
                    }
                    else {
                        JOptionPane.showMessageDialog(fondo, "Por favor, seleccione un pokemon para cada entrenador.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        // Hacer foco en el panel para detectar la tecla
        fondo.setFocusable(true);
        fondo.requestFocusInWindow(); // Requiere que la ventana esté visible

        // Equipos
        List<Pokemon> equipoE1 = e1.getEquipo();
        List<Pokemon> equipoE2 = e2.getEquipo();

        // Listas de botones por equipo
        ArrayList<BotonPokemon> botonesE1 = new ArrayList<>();
        ArrayList<BotonPokemon> botonesE2 = new ArrayList<>();

        // Panel para el equipo del entrenador 1
        JPanel panelE1 = new JPanel(new GridLayout(equipoE1.size() + 1, 1, 5, 5)); // +1 para el nombre
        panelE1.setBackground(new Color(25, 95, 95));
        JLabel entrenador1 = new JLabel("Entrenador 1: " + e1.getNombre(), SwingConstants.CENTER);
        entrenador1.setForeground(Color.WHITE);
        entrenador1.setFont(new Font("Arial", Font.BOLD, 16));
        panelE1.add(entrenador1);

        // Crear y agregar botones al panel ganador
        for (int i = 0; i < equipoE1.size(); i++) {
            int finalI = i;
            BotonPokemon botonE1 = new BotonPokemon(equipoE1.get(i));
            botonesE1.add(botonE1);
            panelE1.add(botonE1);
            botonesE1.get(i).addActionListener(_ -> {
                System.out.println(botonesE1.get(finalI).getPokemon().getNombre());
                for (BotonPokemon b : botonesE1) {
                    b.setEnabled(false);
                }
                botonesE1.get(finalI).seleccionar();
                controlador.setPokemonActivoEntrenador1(botonesE1.get(finalI).getPokemon());
                seleccionados[0] = true;
            });
        }

        // Panel para el equipo del entrenador 2
        JPanel panelE2 = new JPanel(new GridLayout(equipoE2.size() + 1, 1, 5, 5));
        panelE2.setBackground(new Color(25, 95, 95));
        JLabel entrenador2 = new JLabel("Entrenador 2: " + e2.getNombre(), SwingConstants.CENTER);
        entrenador2.setForeground(Color.WHITE);
        entrenador2.setFont(new Font("Arial", Font.BOLD, 16));
        panelE2.add(entrenador2);

        for (int i = 0; i < equipoE2.size(); i++) {
            int finalI = i;
            BotonPokemon botonE2 = new BotonPokemon(equipoE2.get(i));
            botonesE2.add(botonE2);
            panelE2.add(botonE2);
            botonesE2.get(i).addActionListener(_ -> {
                System.out.println(botonesE2.get(finalI).getPokemon().getNombre());
                for (BotonPokemon b : botonesE2) {
                    b.setEnabled(false);
                }
                botonesE2.get(finalI).seleccionar();
                controlador.setPokemonActivoEntrenador2(botonesE2.get(finalI).getPokemon());
                seleccionados[1] = true;
            });
        }
        fondo.add(panelE1);
        fondo.add(panelE2);
        fondo.requestFocusInWindow();
        return fondo;
    }

    public ImageIcon VidaActual(Pokemon pokemon) {
        
        if (pokemon.getHp() > pokemon.getHPMAX()*0.9) {
          
            return new ImageIcon(getClass().getResource("/vista/vida1.png"));
        }
        else if (pokemon.getHp() > pokemon.getHPMAX()*0.8) {
            return new ImageIcon(getClass().getResource("/vista/vida2.png"));
        }
        else if (pokemon.getHp() > pokemon.getHPMAX()*0.7) {
            return new ImageIcon(getClass().getResource("/vista/vida3.png"));
        } 
        else if (pokemon.getHp() > pokemon.getHPMAX()*0.6) {
            return new ImageIcon(getClass().getResource("/vista/vida4.png"));
        } 
        else if(pokemon.getHp() > pokemon.getHPMAX()*0.5) {
            return new ImageIcon(getClass().getResource("/vista/vida5.png"));
        }
        else if(pokemon.getHp() > pokemon.getHPMAX()*0.4) {
            return new ImageIcon(getClass().getResource("/vista/vida6.png"));
        }
        else if(pokemon.getHp() > pokemon.getHPMAX()*0.3) {
            return new ImageIcon(getClass().getResource("/vista/vida7.png"));
        }
        else if(pokemon.getHp() > pokemon.getHPMAX()*0.2) {
            return new ImageIcon(getClass().getResource("/vista/vida8.png"));
        }
        else if(pokemon.getHp() > pokemon.getHPMAX()*0.1) {
            return new ImageIcon(getClass().getResource("/vista/vida9.png"));
        }
        else {
            return new ImageIcon(getClass().getResource("/vista/vida10.png"));
        }
    }

    public JPanel showSeventhPanel(Pokemon atacante, Pokemon defensor) {
        System.out.println("Mostrando panel 7");
        
        currentPanel = 7;

        JPanel panel = new JPanel();
        panel.setLayout(null); // para que el LayeredPane funcione bien
        panel.setPreferredSize(new java.awt.Dimension(605, 327));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 605, 327);

        // 1. Fondo
        ImageIcon fondoBatalla = new ImageIcon(getClass().getResource("/vista/fondo.png"));
        Image imagenFondo = fondoBatalla.getImage().getScaledInstance(605, 327, Image.SCALE_SMOOTH);
        JLabel labelFondo = new JLabel(new ImageIcon(imagenFondo));
        labelFondo.setBounds(0, 0, 605, 327);
        layeredPane.add(labelFondo, Integer.valueOf(0));

    
        // 3. Pokémon
        ImageIcon jugador2 = ICONOS_TIPO_ATACANTE.get(atacante.getTipo());
        JLabel spriteJugador = new JLabel(jugador2);
        spriteJugador.setBounds(90, 100, 120, 120);
        layeredPane.add(spriteJugador, Integer.valueOf(1));

        ImageIcon jugador1 = ICONOS_TIPO_DEFENSOR.get(defensor.getTipo());
        JLabel spriteEnemigo = new JLabel(jugador1);
        spriteEnemigo.setBounds(370, 30, 120, 120);
        layeredPane.add(spriteEnemigo, Integer.valueOf(1));

        //panel info jugador avanzar

        JPanel infoEnemigo = new JPanel();
        infoEnemigo.setLayout(null);
        infoEnemigo.setBackground(new Color(255, 255, 200)); // color de fondo claro
        infoEnemigo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoEnemigo.setBounds(70, 20, 200, 60);

        JLabel nombreEnemigo = new JLabel(defensor.getNombre());
        nombreEnemigo.setFont(new Font("Monospaced", Font.BOLD, 20));
        nombreEnemigo.setBounds(10, 5, 100, 20);
        infoEnemigo.add(nombreEnemigo);

        JLabel nivelEnemigo = new JLabel("Nv" + defensor.getNivel());
        nivelEnemigo.setFont(new Font("Monospaced", Font.BOLD, 20));
        nivelEnemigo.setBounds(140, 5, 100, 20);
        infoEnemigo.add(nivelEnemigo);

        JLabel barraVidaEnemigo = new JLabel(VidaActual(defensor));
        barraVidaEnemigo.setBounds(40, 40, 150, 10);
        infoEnemigo.add(barraVidaEnemigo);

        layeredPane.add(infoEnemigo, Integer.valueOf(2));

        // 5. Panel de información del jugador
        JPanel infoJugador = new JPanel();
        infoJugador.setLayout(null);
        infoJugador.setBackground(new Color(255, 255, 200));
        infoJugador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoJugador.setBounds(360, 125, 200, 60);

        JLabel nombreJugador = new JLabel(atacante.getNombre());
        nombreJugador.setFont(new Font("Monospaced", Font.BOLD, 20));
        nombreJugador.setBounds(10, 5, 100, 20);
        infoJugador.add(nombreJugador);

        JLabel nivelJugador = new JLabel("Nv" + atacante.getNivel());
        nivelJugador.setFont(new Font("Monospaced", Font.BOLD, 20));
        nivelJugador.setBounds(140, 5, 100, 20);
        infoJugador.add(nivelJugador);
        System.out.println(VidaActual(atacante));
        System.out.println(VidaActual(defensor));
        System.out.println(atacante.getNivel());
        System.out.println(defensor.getNivel());
        JLabel barraVidaJugador = new JLabel(VidaActual(atacante));
        barraVidaJugador.setBounds(40, 40, 150, 10);
        infoJugador.add(barraVidaJugador);

        layeredPane.add(infoJugador, Integer.valueOf(2));


        // 6. Panel de opciones de ataque
        // Panel de comandos personalizado (capa 2) mostrarPokemon
        JPanel comandos = new JPanel();
        comandos.setLayout(new GridLayout(2, 2, 10, 10));
        comandos.setBounds(8, 190, 400, 100);
        comandos.setBackground(Color.WHITE);
        comandos.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3)); // Borde café/naranja

        String[] ataques = new String[4];
        for (int i = 0; i < 4; i++) {
            ataques[i] = atacante.getAtaques().get(i).getNombre();
        }

        JButton boton1 = new JButton(ataques[0]);
        boton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes manejar la acción del botón 1
                controlador.atacar(atacante.getAtaques().get(0));
                if(controlador.getOrden().get(0).getVivo() == true && controlador.getOrden().get(1).getVivo() == true) {
                   switchToNextPanel(WinnerPanel(null, atacante, defensor,  false));
                }
            }
        });
        boton1.setBorderPainted(false); // Sin borde
        boton1.setContentAreaFilled(false); // Sin fondo
        boton1.setFont(new Font("Arial", Font.BOLD, 12));
        comandos.add(boton1);

        JButton boton2 = new JButton(ataques[1]);
        boton2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes manejar la acción del botón 2
                controlador.atacar(atacante.getAtaques().get(1));
                if(controlador.getOrden().get(0).getVivo() == true && controlador.getOrden().get(1).getVivo() == true) {
                    switchToNextPanel(WinnerPanel(null, atacante, defensor,  false));
                }
            }
        });
        boton2.setBorderPainted(false); // Sin borde
        boton2.setContentAreaFilled(false); // Sin fondo
        boton2.setFont(new Font("Arial", Font.BOLD, 12));
        comandos.add(boton2);

        JButton boton3 = new JButton(ataques[2]);
        boton3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes manejar la acción del botón 3
                controlador.atacar(atacante.getAtaques().get(2));
                if(controlador.getOrden().get(0).getVivo() == true && controlador.getOrden().get(1).getVivo() == true) {
                   switchToNextPanel(WinnerPanel(null, atacante, defensor,  false));
                }
            }
        });
        boton3.setBorderPainted(false); // Sin borde
        boton3.setContentAreaFilled(false); // Sin fondo
        boton3.setFont(new Font("Arial", Font.BOLD, 12));
        comandos.add(boton3);

        JButton boton4 = new JButton(ataques[3]);
        boton4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Aquí puedes manejar la acción del botón 4
                controlador.atacar(atacante.getAtaques().get(3));
                if(controlador.getOrden().get(0).getVivo() == true && controlador.getOrden().get(1).getVivo() == true) {
                   switchToNextPanel(WinnerPanel(null, atacante, defensor,  false));
                }
            }
        });
        boton4.setBorderPainted(false); // Sin borde
        boton4.setContentAreaFilled(false); // Sin fondo
        boton4.setFont(new Font("Arial", Font.BOLD, 12));
        comandos.add(boton4);

        layeredPane.add(comandos, Integer.valueOf(2));

        // 7. Panel de tipo y PP
        JPanel panelInfoAtaque = new JPanel();
        panelInfoAtaque.setLayout(new GridLayout(2, 1));
        panelInfoAtaque.setBackground(new Color(240, 240, 240));
        panelInfoAtaque.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3));
        panelInfoAtaque.setBounds(408, 190, 180, 100);

        JLabel etiquetaPP = new JLabel("PP:     " + atacante.getAtk());
        etiquetaPP.setFont(new Font("Monospaced", Font.BOLD, 15));
        JLabel etiquetaTipo = new JLabel(" TIPO/" + atacante.getTipo());
        etiquetaTipo.setFont(new Font("Monospaced", Font.BOLD, 15));
        panelInfoAtaque.add(etiquetaPP);
        panelInfoAtaque.add(etiquetaTipo);

        layeredPane.add(panelInfoAtaque, Integer.valueOf(2));

        // Agregar el layeredPane al panel base
        panel.add(layeredPane);
        return panel;
    }

    // ganador
    public JPanel WinnerPanel(Entrenador ganador, Pokemon atacante, Pokemon defensor, boolean isWinner) {
        
        currentPanel = 8;

        JPanel panel = new JPanel();
        panel.setLayout(null); // para que el LayeredPane funcione bien
        panel.setPreferredSize(new java.awt.Dimension(605, 327));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 605, 327);

        // 1. Fondo
        ImageIcon fondoBatalla = new ImageIcon(getClass().getResource("/vista/fondo.png"));
        Image imagenFondo = fondoBatalla.getImage().getScaledInstance(605, 327, Image.SCALE_SMOOTH);
        JLabel labelFondo = new JLabel(new ImageIcon(imagenFondo));
        labelFondo.setBounds(0, 0, 605, 327);
        layeredPane.add(labelFondo, Integer.valueOf(0));

    
        // 3. Pokémon
        ImageIcon jugador2 = ICONOS_TIPO_ATACANTE.get(atacante.getTipo());
        JLabel spriteJugador = new JLabel(jugador2);
        spriteJugador.setBounds(90, 100, 120, 120);
        layeredPane.add(spriteJugador, Integer.valueOf(1));

        ImageIcon jugador1 = ICONOS_TIPO_DEFENSOR.get(defensor.getTipo());
        JLabel spriteEnemigo = new JLabel(jugador1);
        spriteEnemigo.setBounds(370, 30, 120, 120);
        layeredPane.add(spriteEnemigo, Integer.valueOf(1));

        //panel info jugador

        JPanel infoEnemigo = new JPanel();
        infoEnemigo.setLayout(null);
        infoEnemigo.setBackground(new Color(255, 255, 200)); // color de fondo claro
        infoEnemigo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoEnemigo.setBounds(70, 20, 200, 60);

        JLabel nombreEnemigo = new JLabel(defensor.getNombre());
        nombreEnemigo.setFont(new Font("Monospaced", Font.BOLD, 20));
        nombreEnemigo.setBounds(10, 5, 100, 20);
        infoEnemigo.add(nombreEnemigo);

        JLabel nivelEnemigo = new JLabel("Nv" + defensor.getNivel());
        nivelEnemigo.setFont(new Font("Monospaced", Font.BOLD, 20));
        nivelEnemigo.setBounds(140, 5, 100, 20);
        infoEnemigo.add(nivelEnemigo);

        JLabel barraVidaEnemigo = new JLabel(VidaActual(defensor));
        barraVidaEnemigo.setBounds(40, 40, 150, 10);
        infoEnemigo.add(barraVidaEnemigo);

        layeredPane.add(infoEnemigo, Integer.valueOf(2));

        // 5. Panel de información del jugador
        JPanel infoJugador = new JPanel();
        infoJugador.setLayout(null);
        infoJugador.setBackground(new Color(255, 255, 200));
        infoJugador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        infoJugador.setBounds(360, 125, 200, 60);

        JLabel nombreJugador = new JLabel(atacante.getNombre());
        nombreJugador.setFont(new Font("Monospaced", Font.BOLD, 20));
        nombreJugador.setBounds(10, 5, 100, 20);
        infoJugador.add(nombreJugador);

        JLabel nivelJugador = new JLabel("Nv" + atacante.getNivel());
        nivelJugador.setFont(new Font("Monospaced", Font.BOLD, 20));
        nivelJugador.setBounds(140, 5, 100, 20);
        infoJugador.add(nivelJugador);
        System.out.println(VidaActual(atacante));
        System.out.println(VidaActual(defensor));
        System.out.println(atacante.getNivel());
        System.out.println(defensor.getNivel());
        JLabel barraVidaJugador = new JLabel(VidaActual(atacante));
        barraVidaJugador.setBounds(40, 40, 150, 10);
        infoJugador.add(barraVidaJugador);

        layeredPane.add(infoJugador, Integer.valueOf(2));



    // 6. Panel de opciones de ataque
    // Panel de comandos personalizado (capa 2) mostrarPokemon
    JPanel comandos = new JPanel();
    comandos.setLayout(new GridLayout(2, 2, 10, 10));
    comandos.setBounds(8, 190, 400, 100);
    comandos.setBackground(Color.WHITE);
    comandos.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3)); // Borde café/naranja

    String[] ataques = new String[4];
    for (int i = 0; i < 4; i++) {
        ataques[i] = atacante.getAtaques().get(i).getNombre();
    }
    JLabel ganadorLabel;
    JLabel ganadorLabel2;
    if (isWinner) {
     ganadorLabel = new JLabel("¡" + ganador.getNombre() + " ha ganado !");
     ganadorLabel2 = new JLabel("el combate!");
    }
    else{
    ganadorLabel = new JLabel("¡" + atacante.getNombre() + " ha hecho");
    ganadorLabel2 = new JLabel((int)(defensor.getHPMAX() - defensor.getHp()) + " puntos de daño!");
    panel.addKeyListener(new KeyListener() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    switchToNextPanel(showSeventhPanel(defensor, atacante));
                }
            }
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyReleased(KeyEvent e) {}
        });
}
    ganadorLabel.setFont(new Font("Monospaced", Font.BOLD, 20));
    ganadorLabel.setForeground(Color.BLACK);
    ganadorLabel.setBounds(50, 10, 400, 30);
    comandos.add(ganadorLabel);

    ganadorLabel2.setFont(new Font("Monospaced", Font.BOLD, 20));
    ganadorLabel2.setForeground(Color.BLACK);
    ganadorLabel2.setBounds(50, 20, 400, 30);
    comandos.add(ganadorLabel2);

    layeredPane.add(comandos, Integer.valueOf(2));
    // 7. Panel de tipo y PP
    JPanel panelInfoAtaque = new JPanel();
    panelInfoAtaque.setLayout(new GridLayout(2, 1));
    panelInfoAtaque.setBackground(new Color(240, 240, 240));
    panelInfoAtaque.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3));
    panelInfoAtaque.setBounds(408, 190, 180, 100);

    JLabel etiquetaPP = new JLabel("PP:     " + atacante.getAtk());
    etiquetaPP.setFont(new Font("Monospaced", Font.BOLD, 15));
    JLabel etiquetaTipo = new JLabel(" TIPO/" + atacante.getTipo());
    etiquetaTipo.setFont(new Font("Monospaced", Font.BOLD, 15));
    panelInfoAtaque.add(etiquetaPP);
    panelInfoAtaque.add(etiquetaTipo);

    layeredPane.add(panelInfoAtaque, Integer.valueOf(2));
    // Agregar el layeredPane al panel base
    panel.add(layeredPane);
    return panel;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void bienvenido() {
        switchToNextPanel(showFirstPanel()); // Mostrar el primer panel
    }


    public void entrenadores() {
        nombre1 = jugador1Field.getText(); // Obtener el texto del primer campo
        nombre2 = jugador2Field.getText(); // Obtener el texto del segundo campo
        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese ambos nombres.", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        } else {
            error = false;
            controlador.setListaEntrenadores(nombre1, nombre2);
            switchToNextPanel(showFourthPanel());
        }
    }
    
    public void pokemones() {
        pokemon1 =  poke1Field.getText();
        pokemon2 =  poke2Field.getText();
        pokemon3 =  poke3Field.getText();
        if ((pokemon1.isEmpty() || pokemon2.isEmpty() || pokemon3.isEmpty()) ) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese todos los nombres.", "Error", JOptionPane.ERROR_MESSAGE);
            error = true;
        } 
        else{
            System.out.println("Pokemon 1: " + pokemon1);
            System.out.println("Pokemon 2: " + pokemon2);
            System.out.println("Pokemon 3: " + pokemon3);
            controlador.setListaPokemones(pokemon1, pokemon2, pokemon3);
            error = false;
            controlador.avanzarEscena();
        }

       System.out.println("Error: " + error);
    }

    ArrayList<Pokemon> listaPokemones = new ArrayList<>();
    byte contadorPokemones = 0;
    byte contadorEntrenadores = 0;
       @Override
    public void mostrarPokemon(ArrayList<Pokemon> pokemon) {
        System.out.println("Lista de pokemones: " + pokemon);
        listaPokemones = pokemon; 
        currentPanel = 6;
        switchToNextPanel(showSixthPanel(listaPokemones.get(contadorPokemones)));
    }

    public void ganador(Entrenador entrenador) {
        // Aquí puedes implementar la lógica para mostrar el ganador
        switchToNextPanel(WinnerPanel(entrenador, controlador.getOrden().get(0), controlador.getOrden().get(1), true));
    }



    public void elegirPokemon(Entrenador e1, Entrenador e2){
        System.out.println("Entramos a elegir pokemon");
        switchToNextPanel(mostrarTabla(e1, e2));
    }

    public void elegirAtaque(Pokemon pokemon) {
        // Aquí este método tampoco hace nada
    }

    // Si lees esto, eres el mejor Joshua
    
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("current panel: " + currentPanel);
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            switch (currentPanel) {
                case 2:
                    switchToNextPanel(showThirdPanel()); // Cambiar al tercer panel
                    break;
                case 4: 
                    switchToNextPanel(showFifthPanel(getNombre1())); // Cambiar al quinto panel
                    break;
                case 6:
                    error = false;
                    System.out.println("Contador: " + contadorPokemones);
                    if (contadorPokemones < 2) {
                        contadorPokemones++;
                        switchToNextPanel(showSixthPanel(listaPokemones.get(contadorPokemones)));
                    } // Cambiar al sexto panel
                    else{
                        contadorPokemones = 0;
                        if (contadorEntrenadores < 1) {
                            switchToNextPanel(showFifthPanel(getNombre2()));
                            contadorEntrenadores++;
                        }
                        else{
                            controlador.avanzarEscena();
                            currentPanel = 7;
                        }
                    }
                    break;
                default:            
                    if(error == false) {
                        controlador.avanzarEscena();
                    }
                    else {
                        controlador.actualizarEscena();
                    }
                    break;
            }
        }   
    }
    @Override
    public void keyTyped(KeyEvent e) {
    }
    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    @Override
    public void continuar() {
    }
}

// Botón personalizado para mostrar el Pokémon

class BotonPokemon extends JButton {
    Pokemon pokemon;
    private boolean seleccionado = false;

    public BotonPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        setPreferredSize(new Dimension(290, 75));
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void seleccionar() {
        this.seleccionado = true;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fondo según selección
        if (seleccionado) {
            g.setColor(new Color(30, 100, 180)); // Azul oscuro si está seleccionado
        } else {
            g.setColor(new Color(100, 180, 255)); // Azul claro por defecto
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        if (pokemon.getImagen().getImage() != null) {
        g.drawImage(pokemon.getImagen().getImage(), 10, 10, 50, 50, this); // (x, y, ancho, alto)
        }

        // Texto nombre y nivel
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 14));
        g.drawString(pokemon.getNombre().toUpperCase(), 70, 20);
        g.drawString("Nv" + pokemon.getNivel(), 70, 40);

        // PS
        g.setColor(Color.ORANGE);
        g.drawString("PS", 200, 20);

        // Barra gris de fondo
        g.setColor(Color.GRAY);
        g.fillRect(230, 12, 80, 10);

        // Barra verde
        int barra = (int)((double)pokemon.getHp() / pokemon.getHPMAX() * 80);
        double porcentaje = (double)pokemon.getHp() / pokemon.getHPMAX();

        // Ajustar de acuerdo con los porcentajes e imágenes de VistaPokemonGUI.vidaActual()
        g.setColor(Color.GREEN);
        if (porcentaje > 0.5) {
            g.setColor(Color.GREEN);
        } else if (porcentaje > 0.2) {
            g.setColor(Color.YELLOW);
        } else {
            g.setColor(Color.RED);
        }
        g.fillRect(230, 12, barra, 10);
        
        // PS numérico
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        g.drawString(pokemon.getHp() + "/" + pokemon.getHPMAX(), 230, 35);

        super.paintComponent(g); // Para eventos del botón
    }
}