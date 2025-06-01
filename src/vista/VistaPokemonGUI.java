package vista;

import controlador.Controlador;
import modelo.Ataque;
import modelo.Entrenador;
import modelo.Pokemon;
import modelo.TipoAtaquePokemon;

import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List; 
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog; // Importado para el diálogo modal
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants; 

import java.awt.Color;
import java.awt.Component; 
import java.awt.Dimension; 
import java.awt.Font;
import java.awt.Graphics; 
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter; 
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter; // Importado
import java.awt.event.WindowEvent;  // Importado
import java.net.URL; 
import javax.swing.Timer;


public class VistaPokemonGUI extends JFrame implements ActionListener, KeyListener, VistaPokemon {

    // --- INICIO: Atributos y constantes para la nueva pantalla de selección ---
    private static class PokemonSeleccionable {
        String nombre;
        ImageIcon icono; 
        ImageIcon iconoOriginal; 
        boolean seleccionadoPorEntrenadorActual = false;
        JPanel panelRepresentacion; 

        PokemonSeleccionable(String nombre, ImageIcon iconoOriginal) {
            this.nombre = nombre;
            this.iconoOriginal = iconoOriginal;
            Image img = iconoOriginal.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            this.icono = new ImageIcon(img);
        }
    }

    private ArrayList<PokemonSeleccionable> listaPokemonSeleccionables;
    private ArrayList<PokemonSeleccionable> pokemonesElegidosTemporalmente;

    private final Color defaultBorderColorSeleccion = Color.GRAY;
    private final Color selectedBorderColorSeleccion = Color.CYAN;
    // private final Color disabledBorderColorSeleccion = Color.DARK_GRAY; // No se usa directamente aquí pero es buena práctica
    private final int defaultBorderSizeSeleccion = 2;
    private final int selectedBorderSizeSeleccion = 4;
    private JLabel[] etiquetasPokemonElegidosLabels; 

    // --- FIN: Atributos y constantes para la nueva pantalla de selección ---

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

    public int getEscena() { 
        return currentPanel;
    }

    public boolean isError() {
        return error;
    }

    private boolean error = false;
    private Timer timer;
    private int currentPanel = 0;

    private String nombre1 = "";
    private String nombre2 = "";
    private String pokemon1 = ""; 
    private String pokemon2 = ""; 
    private String pokemon3 = ""; 

    private JTextField jugador1Field = new JTextField();
    private JTextField jugador2Field = new JTextField();

    private Controlador controlador;

    // --- Clase interna para el panel con fondo ---
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                this.backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("No se pudo cargar la imagen de fondo: " + imagePath);
                this.backgroundImage = null;
                this.setBackground(Color.DARK_GRAY); 
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    // --- INICIO: Clase interna para el diálogo de cambio de Pokémon ---
    class PokemonChangeDialog extends JDialog {
        private Pokemon pokemonSeleccionado = null;
        // private Entrenador entrenadorActual; // No es necesario si se pasa el equipo directamente

        public PokemonChangeDialog(JFrame owner, Entrenador entrenador) {
            super(owner, "Elige un Pokémon, " + entrenador.getNombre(), true); // true for modal
            // this.entrenadorActual = entrenador; // Almacenar si se necesita más adelante
            setSize(550, 480); // Tamaño ajustado para 3 Pokémon en 2 columnas o similar
            setLocationRelativeTo(owner);
            // Evita que el diálogo se cierre con la 'X' si no se ha hecho una selección válida
            setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    // Si el usuario intenta cerrar con 'X', y no hay selección,
                    // podríamos forzar una selección o simplemente no hacer nada (mantener abierto).
                    // Como el diálogo SÓLO se cierra programáticamente al elegir un Pokémon válido,
                    // este listener es más una salvaguarda o para lógica adicional si se desea.
                    if (pokemonSeleccionado == null) {
                         JOptionPane.showMessageDialog(PokemonChangeDialog.this,
                                "Debes seleccionar un Pokémon para continuar.",
                                "Selección Requerida",
                                JOptionPane.WARNING_MESSAGE);
                    } else {
                        dispose(); // Permitir cierre si ya hubo selección (no debería llegar aquí)
                    }
                }
            });


            BackgroundPanel dialogMainPanel = new BackgroundPanel("/vista/election.png");
            dialogMainPanel.setLayout(new BoxLayout(dialogMainPanel, BoxLayout.Y_AXIS));
            dialogMainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            
            JLabel titleLabel = new JLabel("¡" + entrenador.getNombre() + ", elige un Pokémon para continuar!");
            titleLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
            titleLabel.setForeground(Color.WHITE);
            titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            dialogMainPanel.add(titleLabel);
            dialogMainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

            JPanel gridPanel = new JPanel(new GridLayout(0, 2, 15, 15)); // 2 columnas, espaciado
            gridPanel.setOpaque(false);

            ArrayList<Pokemon> equipo = entrenador.getEquipo();
            boolean hayOpcionesValidas = false;

            if (equipo.isEmpty()) {
                JLabel noPokemonLabel = new JLabel("¡No quedan Pokémon en el equipo!");
                noPokemonLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
                noPokemonLabel.setForeground(Color.YELLOW);
                noPokemonLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                dialogMainPanel.add(noPokemonLabel);
            } else {
                for (Pokemon p : equipo) {
                    if (p.getHp() > 0) hayOpcionesValidas = true;
                    JPanel pokemonCard = createPokemonCardInDialog(p);
                    gridPanel.add(pokemonCard);
                }
                dialogMainPanel.add(gridPanel);
            }
            
            if (!hayOpcionesValidas && !equipo.isEmpty()) {
                 JLabel todosDebilitadosLabel = new JLabel("¡Todos tus Pokémon están debilitados!");
                 todosDebilitadosLabel.setFont(new Font("Monospaced", Font.BOLD, 16));
                 todosDebilitadosLabel.setForeground(Color.RED);
                 todosDebilitadosLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
                 dialogMainPanel.add(Box.createRigidArea(new Dimension(0,10)));
                 dialogMainPanel.add(todosDebilitadosLabel);
                 // Permitir cerrar el diálogo si no hay opciones válidas.
                 // No se puede seleccionar nada. El controlador lo manejará.
                 setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE); 
            }


            add(dialogMainPanel);
        }

        private JPanel createPokemonCardInDialog(Pokemon pokemon) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setOpaque(false); 
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            card.setPreferredSize(new Dimension(200, 160)); // Tamaño fijo para las tarjetas

            ImageIcon pokemonIconOriginal = ICONOS_TIPO_DEFENSOR.get(pokemon.getTipo());
            JLabel imgLabel;
            if (pokemonIconOriginal != null) {
                Image scaledImg = pokemonIconOriginal.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
                imgLabel = new JLabel(new ImageIcon(scaledImg));
            } else {
                imgLabel = new JLabel("No Img");
                imgLabel.setPreferredSize(new Dimension(70,70));
            }
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(imgLabel);
            card.add(Box.createRigidArea(new Dimension(0, 5)));

            JLabel nameLabel = new JLabel(pokemon.getNombre() + " Nv." + pokemon.getNivel());
            nameLabel.setForeground(Color.WHITE);
            nameLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
            nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(nameLabel);

            JLabel hpBarLabel = new JLabel(VidaActual(pokemon)); 
            hpBarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(hpBarLabel);
            
            JLabel hpTextLabel = new JLabel("HP: " + pokemon.getHp() + "/" + pokemon.getHPMAX());
            hpTextLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
            hpTextLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            card.add(hpTextLabel);

            if (pokemon.getHp() > 0) {
                nameLabel.setForeground(Color.WHITE);
                hpTextLabel.setForeground(Color.GREEN.brighter());
                card.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        pokemonSeleccionado = pokemon;
                        PokemonChangeDialog.this.dispose(); 
                    }
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(selectedBorderColorSeleccion.brighter(), selectedBorderSizeSeleccion),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        ));
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                         card.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                            BorderFactory.createEmptyBorder(10, 10, 10, 10)
                        ));
                    }
                });
            } else {
                nameLabel.setForeground(Color.GRAY);
                hpTextLabel.setForeground(Color.RED);
                card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.DARK_GRAY, defaultBorderSizeSeleccion),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                 // Hacer la tarjeta no interactiva si está debilitado
                for (MouseListener ml : card.getMouseListeners()) {
                    card.removeMouseListener(ml);
                }
            }
            return card;
        }

        public Pokemon getPokemonSeleccionado() {
            return pokemonSeleccionado;
        }
    }
    // --- FIN: Clase interna para el diálogo de cambio de Pokémon ---


    public VistaPokemonGUI() {
        setTitle("Pokémon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(605, 327); 
        setResizable(false);
        setLocationRelativeTo(null);
        
        inicializarPokemonSeleccionables();
        pokemonesElegidosTemporalmente = new ArrayList<>();
        etiquetasPokemonElegidosLabels = new JLabel[3];

        timer = new Timer(3000, this);
        timer.setRepeats(false);
    }
    
    private void inicializarPokemonSeleccionables() {
        listaPokemonSeleccionables = new ArrayList<>();
        if (ICONOS_TIPO.get(TipoAtaquePokemon.FUEGO) != null)
            listaPokemonSeleccionables.add(new PokemonSeleccionable("Charmander", ICONOS_TIPO.get(TipoAtaquePokemon.FUEGO)));
        if (ICONOS_TIPO.get(TipoAtaquePokemon.PLANTA) != null)
            listaPokemonSeleccionables.add(new PokemonSeleccionable("Bulbasaur", ICONOS_TIPO.get(TipoAtaquePokemon.PLANTA)));
        if (ICONOS_TIPO.get(TipoAtaquePokemon.ELECTRICO) != null)
            listaPokemonSeleccionables.add(new PokemonSeleccionable("Pikachu", ICONOS_TIPO.get(TipoAtaquePokemon.ELECTRICO)));
        if (ICONOS_TIPO.get(TipoAtaquePokemon.TIERRA) != null)
            listaPokemonSeleccionables.add(new PokemonSeleccionable("Diglett", ICONOS_TIPO.get(TipoAtaquePokemon.TIERRA)));
        if (ICONOS_TIPO.get(TipoAtaquePokemon.AGUA) != null)
            listaPokemonSeleccionables.add(new PokemonSeleccionable("Squirtle", ICONOS_TIPO.get(TipoAtaquePokemon.AGUA)));
    }


    public static void main(String[] args) {
        // Este main es solo para pruebas, la ejecución real es desde App.java
        // VistaPokemonGUI vista = new VistaPokemonGUI();
        // vista.bienvenido(); // Inicia el flujo
    }

    private JPanel showFirstPanel() {
        currentPanel = 1;
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
        
        return panel;
    }

    public void switchToNextPanel(JPanel panel) {
        getContentPane().removeAll();
        add(panel);
        // Asegurar que el panel nuevo pueda escuchar teclas SIEMPRE, menos el de licencias
        if (currentPanel != 1) { 
            // Remover KeyListeners viejos del panel para evitar duplicados si se reutiliza.
            for (KeyListener kl : panel.getKeyListeners()) {
                panel.removeKeyListener(kl);
            }
            panel.addKeyListener(this);
        }

        panel.setFocusable(true);
        panel.requestFocusInWindow();
        
        if (currentPanel == 8) { 
            setSize(650, 550); 
        } else {
            setSize(605, 327); 
        }
        setLocationRelativeTo(null); 

        this.revalidate();
        this.repaint(); 
    }

    public JPanel showSecondPanel() {
        currentPanel = 2;
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout(null);
        secondPanel.setBackground(new Color(10, 20, 48));

        String texto = """

                    Estás a punto de sumergirte en un
                    mundo lleno de aventuras de las
                    que vas a ser protagonista.

                    Te cruzarás con rivales y criaturas
                    salvajes que querrán luchar contigo,
                    pero ¡ánimo, tú puedes!
                """;
        
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true));
        secondPanel.add(textArea);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        secondPanel.add(flecha);

        return secondPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == timer) {
            switchToNextPanel(showSecondPanel());
        }
    }


    public JPanel showThirdPanel() {
        currentPanel = 3;
        JPanel thirdPanel = new JPanel();
        thirdPanel.setLayout(null);
        thirdPanel.setBackground(new Color(10, 20, 48));

        JLabel label1 = new JLabel("Ingrese el nombre del entrenador 1:");
        label1.setForeground(Color.WHITE);
        label1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label1.setBounds(150, 60, 360, 25);
        thirdPanel.add(label1);

        // Limpiar KeyListeners de los text fields antes de añadir el de la ventana
        for (KeyListener kl : jugador1Field.getKeyListeners()) {
            jugador1Field.removeKeyListener(kl);
        }
        jugador1Field.setBounds(210, 100, 200, 25);
        jugador1Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador1Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador1Field.setBackground(Color.WHITE);
        jugador1Field.setForeground(Color.BLACK);
        // jugador1Field.addKeyListener(this); // El KeyListener del panel general debería bastar
        thirdPanel.add(jugador1Field);

        JLabel label2 = new JLabel("Ingrese el nombre del entrenador 2:");
        label2.setForeground(Color.WHITE);
        label2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        label2.setBounds(150, 140, 360, 25);
        thirdPanel.add(label2);
        
        for (KeyListener kl : jugador2Field.getKeyListeners()) {
            jugador2Field.removeKeyListener(kl);
        }
        jugador2Field.setBounds(210, 180, 200, 25);
        jugador2Field.setFont(new Font("Monospaced", Font.PLAIN, 13));
        jugador2Field.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 2));
        jugador2Field.setBackground(Color.WHITE);
        jugador2Field.setForeground(Color.BLACK);
        // jugador2Field.addKeyListener(this);
        thirdPanel.add(jugador2Field);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 230, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        thirdPanel.add(flecha);
    
        return thirdPanel;
    }

    public JPanel showFourthPanel() {
        currentPanel = 4;
        JPanel fourthPanel = new JPanel();
        fourthPanel.setLayout(null);
        fourthPanel.setBackground(new Color(10, 20, 48));
     
        String texto1 = """

                        Bienvenidos a Pokémon {jugador1} y 
                        {jugador2} les espera un gran 
                        desafío en su aventura.

                        Sus pokemones serán asignados 
                        y tendrán que enfrentarse para
                        demostrar quién es el mejor
                        entrenador.
                """; 
        String texto = texto1.replace("{jugador1}", nombre1).replace("{jugador2}", nombre2);
        JTextArea textArea = new JTextArea(texto);
        textArea.setBounds(90, 40, 400, 200);
        textArea.setEditable(false);
        textArea.setBackground(Color.WHITE);
        textArea.setForeground(Color.DARK_GRAY);
        textArea.setFont(new Font("Monospaced", Font.BOLD, 15));
        textArea.setCaretColor(new Color(0, 0, 0, 0));
        textArea.setBorder(BorderFactory.createLineBorder(new Color(100, 180, 255), 3, true));
        fourthPanel.add(textArea);

        JLabel flecha = new JLabel("▼");
        flecha.setForeground(Color.RED);
        flecha.setBounds(450, 250, 30, 30);
        flecha.setFont(new Font("Arial", Font.BOLD, 20));
        fourthPanel.add(flecha);

        return fourthPanel;
    }

    public JPanel showPokemonSelectionScreen(String nombreEntrenador) {
        currentPanel = 8; 
        
        pokemonesElegidosTemporalmente.clear();
        for (PokemonSeleccionable ps : listaPokemonSeleccionables) {
            ps.seleccionadoPorEntrenadorActual = false;
            if (ps.panelRepresentacion != null) { 
                 ps.panelRepresentacion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        }

        BackgroundPanel panelSeleccion = new BackgroundPanel("/vista/election.png");
        panelSeleccion.setLayout(new BoxLayout(panelSeleccion, BoxLayout.Y_AXIS));
        panelSeleccion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titulo = new JLabel("Elige 3 Pokémon, " + nombreEntrenador + "!");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Monospaced", Font.BOLD, 22));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSeleccion.add(titulo);
        panelSeleccion.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel panelElegidosDisplay = new JPanel(new GridLayout(1, 3, 10, 0));
        panelElegidosDisplay.setOpaque(false);
        panelElegidosDisplay.setMaximumSize(new Dimension(500, 30)); 
        for(int i=0; i<3; i++) {
            etiquetasPokemonElegidosLabels[i] = new JLabel("Slot " + (i+1));
            etiquetasPokemonElegidosLabels[i].setForeground(Color.LIGHT_GRAY);
            etiquetasPokemonElegidosLabels[i].setFont(new Font("Monospaced", Font.ITALIC, 14));
            etiquetasPokemonElegidosLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            panelElegidosDisplay.add(etiquetasPokemonElegidosLabels[i]);
        }
        panelSeleccion.add(panelElegidosDisplay);
        panelSeleccion.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel cuadriculaPokemones = new JPanel(new GridLayout(0, 3, 15, 15)); 
        cuadriculaPokemones.setOpaque(false); 

        for (PokemonSeleccionable ps : listaPokemonSeleccionables) {
            JPanel celdaPokemon = new JPanel();
            celdaPokemon.setLayout(new BoxLayout(celdaPokemon, BoxLayout.Y_AXIS));
            celdaPokemon.setOpaque(false); 
            celdaPokemon.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                BorderFactory.createEmptyBorder(5, 5, 5, 5) 
            ));
            ps.panelRepresentacion = celdaPokemon; 

            JLabel imgLabel = new JLabel(ps.icono);
            imgLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            celdaPokemon.add(imgLabel);

            JLabel nombreLabel = new JLabel(ps.nombre);
            nombreLabel.setForeground(Color.WHITE);
            nombreLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
            nombreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            celdaPokemon.add(Box.createRigidArea(new Dimension(0,5)));
            celdaPokemon.add(nombreLabel);
            
            celdaPokemon.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (ps.seleccionadoPorEntrenadorActual) {
                        pokemonesElegidosTemporalmente.remove(ps);
                        ps.seleccionadoPorEntrenadorActual = false;
                        celdaPokemon.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    } else {
                        if (pokemonesElegidosTemporalmente.size() < 3) {
                            pokemonesElegidosTemporalmente.add(ps);
                            ps.seleccionadoPorEntrenadorActual = true;
                            celdaPokemon.setBorder(BorderFactory.createCompoundBorder(
                                BorderFactory.createLineBorder(selectedBorderColorSeleccion, selectedBorderSizeSeleccion),
                                BorderFactory.createEmptyBorder(5, 5, 5, 5)
                            ));
                        } else {
                            JOptionPane.showMessageDialog(VistaPokemonGUI.this, 
                                "Ya has seleccionado 3 Pokémon.", "Máximo Alcanzado", JOptionPane.WARNING_MESSAGE);
                        }
                    }
                    actualizarDisplayElegidos();
                }
                 @Override
                public void mouseEntered(MouseEvent e) {
                    if (!ps.seleccionadoPorEntrenadorActual) {
                         celdaPokemon.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(selectedBorderColorSeleccion.darker(), defaultBorderSizeSeleccion +1), // Ligero engrosamiento
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                     if (!ps.seleccionadoPorEntrenadorActual) {
                         celdaPokemon.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                            BorderFactory.createEmptyBorder(5, 5, 5, 5)
                        ));
                    }
                }
            });
            cuadriculaPokemones.add(celdaPokemon);
        }
        panelSeleccion.add(cuadriculaPokemones);
        panelSeleccion.add(Box.createVerticalGlue()); 

        JLabel flecha = new JLabel("▼ (Presiona Enter para confirmar)");
        flecha.setForeground(Color.ORANGE); 
        flecha.setFont(new Font("Arial", Font.BOLD, 18));
        flecha.setAlignmentX(Component.CENTER_ALIGNMENT);
        panelSeleccion.add(flecha);
        panelSeleccion.add(Box.createRigidArea(new Dimension(0,10)));

        actualizarDisplayElegidos(); 
        return panelSeleccion;
    }
    
    private void actualizarDisplayElegidos() {
        for (int i=0; i<3; i++) {
            if (i < pokemonesElegidosTemporalmente.size()) {
                etiquetasPokemonElegidosLabels[i].setText(pokemonesElegidosTemporalmente.get(i).nombre);
                etiquetasPokemonElegidosLabels[i].setForeground(Color.CYAN); 
            } else {
                etiquetasPokemonElegidosLabels[i].setText("Slot " + (i+1));
                etiquetasPokemonElegidosLabels[i].setForeground(Color.LIGHT_GRAY);
            }
        }
    }

    private void resetSeleccionablesVisual() { 
        for(PokemonSeleccionable ps : listaPokemonSeleccionables) {
            ps.seleccionadoPorEntrenadorActual = false;
            if (ps.panelRepresentacion != null) {
                ps.panelRepresentacion.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(defaultBorderColorSeleccion, defaultBorderSizeSeleccion),
                    BorderFactory.createEmptyBorder(5, 5, 5, 5)
                ));
            }
        }
        // pokemonesElegidosTemporalmente.clear(); // Se limpia al inicio de showPokemonSelectionScreen
        // actualizarDisplayElegidos(); // Se llama al final de showPokemonSelectionScreen
    }


    public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO;
    static {
        ICONOS_TIPO = new HashMap<>();
        try {
            ICONOS_TIPO.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmander.png")));
            ICONOS_TIPO.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasur.png"))); 
            ICONOS_TIPO.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/pikachu.png"))); 
            ICONOS_TIPO.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/diglett.png"))); 
            ICONOS_TIPO.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtle.png")));
        } catch (Exception e) {
            System.err.println("Error al cargar uno o más iconos de tipo base (ICONOS_TIPO). Verifica las rutas e imágenes: " + e.getMessage());
        }
    }
    

    public JPanel showSixthPanel(Pokemon pokemon ) {
        currentPanel = 6;
        JPanel panel = new JPanel();
        panel.setBackground(new Color(0xF8D070));
        panel.setLayout(null); 

        JPanel franjamorada = new JPanel();
        franjamorada.setBackground(new Color(0xE0B0FF));
        franjamorada.setBounds(0, 0, 250, 30); 
        panel.add(franjamorada);
        
        // Ajustar el layout de franjamorada para que el JLabel se posicione bien
        franjamorada.setLayout(new BoxLayout(franjamorada, BoxLayout.X_AXIS));
        franjamorada.add(Box.createHorizontalStrut(5)); // Pequeño margen izquierdo

        String nombreConNivel = "Nv." + pokemon.getNivel() + " " + pokemon.getNombre() + " (" + pokemon.getTipo() + ")";
        JLabel nombrLabel = new JLabel(nombreConNivel); // Sin alineación explícita aquí
        nombrLabel.setFont(new Font("Monospaced", Font.BOLD, 12)); // Ligeramente más pequeño
        nombrLabel.setForeground(Color.DARK_GRAY); 
        // nombrLabel.setBounds(5, 5, 240, 20); // No es necesario si el layout de franjamorada lo maneja
        franjamorada.add(nombrLabel);
        franjamorada.add(Box.createHorizontalGlue()); // Empuja el label a la izquierda si hay espacio


        JPanel cajaBlanca = new JPanel(); 
        cajaBlanca.setBackground(Color.WHITE);
        cajaBlanca.setBounds(10, 35, 230, 135); 
        cajaBlanca.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        cajaBlanca.setLayout(null); // Para posicionar la imagen dentro
        panel.add(cajaBlanca);
        
        ImageIcon imagenPokemonOriginal = ICONOS_TIPO_DEFENSOR.get(pokemon.getTipo()); 
        if (imagenPokemonOriginal == null) imagenPokemonOriginal = ICONOS_TIPO.get(pokemon.getTipo()); // Fallback

        if (imagenPokemonOriginal != null) {
            Image img = imagenPokemonOriginal.getImage().getScaledInstance(210, 120, Image.SCALE_SMOOTH);
            JLabel labelImagen = new JLabel(new ImageIcon(img));
            labelImagen.setBounds(10, 7, 210, 120); // Posición relativa a cajaBlanca
            cajaBlanca.add(labelImagen);
        } else {
             JLabel noImgLabel = new JLabel("No Img");
             noImgLabel.setBounds(10,7,210,120);
             noImgLabel.setHorizontalAlignment(SwingConstants.CENTER);
             cajaBlanca.add(noImgLabel);
        }


        String[][] Stats = {
            {"HP", String.valueOf(pokemon.getHp()) + "/" + String.valueOf(pokemon.getHPMAX())}, // Mostrar Max HP
            {"ATAQUE", String.valueOf(pokemon.getAtk())},
            {"DEFENSA", String.valueOf(pokemon.getDf())},
            {"ATK. ESP.", String.valueOf(pokemon.getAtkEs())}, 
            {"DEF. ESP.", String.valueOf(pokemon.getDfEs())}, 
            {"VELOCIDAD", String.valueOf(pokemon.getVelocidad())}
        };

        int statYPos = 10;
        int statHeight = 20;
        int statSpacing = 2; // Espacio entre stats

        for (int i = 0; i < Stats.length; i++) {
            JLabel label = new JLabel(Stats[i][0], JLabel.LEFT); 
            label.setFont(new Font("Monospaced", Font.BOLD, 13)); 
            label.setForeground(Color.WHITE);
            label.setOpaque(true);
            label.setBackground(new Color(88, 88, 88));
            label.setBounds(270, statYPos + i * (statHeight + statSpacing), 120, statHeight); 
            panel.add(label);

            JLabel valores = new JLabel(Stats[i][1], JLabel.RIGHT); 
            valores.setFont(new Font("Monospaced", Font.BOLD, 13)); 
            valores.setForeground(Color.BLACK);
            valores.setOpaque(true);
            valores.setBackground( new Color(240, 225, 160));
            valores.setBounds(400, statYPos + i * (statHeight + statSpacing), 80, statHeight); // Ancho ajustado para valores
            panel.add(valores);
        }
        
        int ataqueYPos = 180; 
        int ataqueHeight = 20;
        int ataqueSpacing = 3;

        if (pokemon.getAtaques() != null) {
            for (int i = 0; i < pokemon.getAtaques().size() && i < 4; i++) { 
                Ataque ataque = pokemon.getAtaques().get(i);
                JLabel labelAtaque = new JLabel(ataque.getNombre(), JLabel.LEFT);
                labelAtaque.setFont(new Font("Monospaced", Font.PLAIN, 12));
                labelAtaque.setForeground(Color.WHITE);
                labelAtaque.setOpaque(true);
                labelAtaque.setBackground(new Color(70, 70, 70)); 
                labelAtaque.setBounds(10, ataqueYPos + i * (ataqueHeight + ataqueSpacing), 150, ataqueHeight); 
                panel.add(labelAtaque);

                JLabel poderLabel = new JLabel("PODER: " + ataque.getPoder(), JLabel.RIGHT);
                poderLabel.setFont(new Font("Monospaced", Font.PLAIN, 12));
                poderLabel.setForeground(Color.BLACK);
                poderLabel.setOpaque(true);
                poderLabel.setBackground(new Color(240, 200, 80));
                poderLabel.setBounds(170, ataqueYPos + i * (ataqueHeight + ataqueSpacing), 120, ataqueHeight); // Ancho ajustado
                panel.add(poderLabel);
            }
        }
        return panel;
    }

    public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO_DEFENSOR;
    static {
        ICONOS_TIPO_DEFENSOR = new HashMap<>();
         try {
            ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmanderFrente.png")));
            ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasurFrente.png")));
            ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/pikachuFrente.png")));
            ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/diglettFrente.png")));
            ICONOS_TIPO_DEFENSOR.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtleFrente.png")));
        } catch (Exception e) { System.err.println("Error al cargar ICONOS_TIPO_DEFENSOR: " + e.getMessage()); }
    }

    public static final Map<TipoAtaquePokemon, ImageIcon> ICONOS_TIPO_ATACANTE;
    static {
        ICONOS_TIPO_ATACANTE = new HashMap<>();
        try {
            ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.FUEGO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/charmanderEspalda.png")));
            ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.PLANTA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/bulbasurEspalda.png")));
            ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.ELECTRICO, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/pikachuEspalda.png")));
            ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.TIERRA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/diglettEspalda.png"))); 
            ICONOS_TIPO_ATACANTE.put(TipoAtaquePokemon.AGUA, new ImageIcon(VistaPokemonGUI.class.getResource("/vista/squirtleEspalda.png")));
        } catch (Exception e) { System.err.println("Error al cargar ICONOS_TIPO_ATACANTE: " + e.getMessage()); }
    }
  

    public ImageIcon VidaActual(Pokemon pokemon) {
        if (pokemon == null || pokemon.getHPMAX() == 0) { // Check for null or 0 max HP
             try { return new ImageIcon(getClass().getResource("/vista/vida5.jpg")); }
             catch (Exception e) { System.err.println("Error cargando vida5.jpg (fallback): " + e.getMessage()); return null; }
        }
        if (pokemon.getHp() <=0) {
            try { return new ImageIcon(getClass().getResource("/vista/vida5.jpg")); }
            catch (Exception e) { System.err.println("Error cargando vida5.jpg: " + e.getMessage()); return null; }
        }
        double porcentajeVida = (double) pokemon.getHp() / pokemon.getHPMAX();
        String path = "/vista/";
        if (porcentajeVida > 0.8) path += "vida1.jpg";
        else if (porcentajeVida > 0.6) path += "vida2.jpg";
        else if (porcentajeVida > 0.4) path += "vida3.jpg";
        else if (porcentajeVida > 0.2) path += "vida4.jpg";
        else path += "vida5.jpg";
        
        try { return new ImageIcon(getClass().getResource(path)); }
        catch (Exception e) { System.err.println("Error cargando imagen de vida ("+path+"): " + e.getMessage()); return null;}
    }

    public JPanel showSeventhPanel(Pokemon atacante, Pokemon defensor) {
        currentPanel = 7;

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new java.awt.Dimension(605, 327));

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setBounds(0, 0, 605, 327);

        ImageIcon fondoBatallaOriginal = null;
        try {
            fondoBatallaOriginal = new ImageIcon(getClass().getResource("/vista/fondo.png"));
        } catch (Exception e) { System.err.println("Error cargando fondo.png: " + e.getMessage());}

        if (fondoBatallaOriginal != null && fondoBatallaOriginal.getIconWidth() > 0) {
            Image imagenFondo = fondoBatallaOriginal.getImage().getScaledInstance(605, 327, Image.SCALE_SMOOTH);
            JLabel labelFondo = new JLabel(new ImageIcon(imagenFondo));
            labelFondo.setBounds(0, 0, 605, 327);
            layeredPane.add(labelFondo, Integer.valueOf(0));
        } else {
            panel.setBackground(Color.DARK_GRAY); // Fallback si el fondo no carga
        }
        

        if (atacante != null) {
            ImageIcon imgAtacante = ICONOS_TIPO_ATACANTE.get(atacante.getTipo());
            if (imgAtacante != null) {
                JLabel spriteJugador = new JLabel(imgAtacante);
                spriteJugador.setBounds(90, 100, imgAtacante.getIconWidth(), imgAtacante.getIconHeight()); 
                layeredPane.add(spriteJugador, Integer.valueOf(1));
            }
            // Panel info jugador
            JPanel infoJugador = new JPanel();
            infoJugador.setLayout(null);
            infoJugador.setBackground(new Color(255, 255, 200, 200));
            infoJugador.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            infoJugador.setBounds(360, 130, 200, 60);

            JLabel nombreJugador = new JLabel(atacante.getNombre());
            nombreJugador.setFont(new Font("Monospaced", Font.BOLD, 15));
            nombreJugador.setBounds(10, 5, 100, 20);
            infoJugador.add(nombreJugador);

            JLabel nivelJugador = new JLabel("Nv " + atacante.getNivel());
            nivelJugador.setFont(new Font("Monospaced", Font.BOLD, 15));
            nivelJugador.setBounds(120, 5, 70, 20);
            infoJugador.add(nivelJugador);

            JLabel barraVidaJugador = new JLabel(VidaActual(atacante));
            barraVidaJugador.setBounds(10, 30, 100, 10);
            infoJugador.add(barraVidaJugador);
            layeredPane.add(infoJugador, Integer.valueOf(2));

            // Panel de comandos
            JPanel comandos = new JPanel();
            comandos.setLayout(new GridLayout(2, 2, 5, 5)); 
            comandos.setBounds(8, 190, 400, 100);
            comandos.setBackground(new Color(255,255,255,220)); 
            comandos.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3));

            if (atacante.getAtaques() != null && atacante.getAtaques().size() >= 4) {
                for (int i = 0; i < 4; i++) {
                    Ataque ataque = atacante.getAtaques().get(i);
                    JButton botonAtaque = new JButton(ataque.getNombre());
                    final int ataqueIndex = i; 
                    botonAtaque.addActionListener(e -> {
                        controlador.atacar(atacante.getAtaques().get(ataqueIndex));
                    });
                    botonAtaque.setBorderPainted(false);
                    botonAtaque.setContentAreaFilled(false);
                    botonAtaque.setFont(new Font("Arial", Font.BOLD, 12));
                    comandos.add(botonAtaque);
                }
            }
            layeredPane.add(comandos, Integer.valueOf(2));

            // Panel info ataque (del atacante)
            JPanel panelInfoAtaque = new JPanel();
            panelInfoAtaque.setLayout(new GridLayout(2, 1));
            panelInfoAtaque.setBackground(new Color(240, 240, 240, 220));
            panelInfoAtaque.setBorder(BorderFactory.createLineBorder(new Color(184, 115, 51), 3));
            panelInfoAtaque.setBounds(408, 190, 180, 100);

            JLabel etiquetaHP = new JLabel("HP: " + atacante.getHp() + "/" + atacante.getHPMAX()); 
            etiquetaHP.setFont(new Font("Monospaced", Font.BOLD, 15));
            etiquetaHP.setHorizontalAlignment(SwingConstants.CENTER);
            JLabel etiquetaTipo = new JLabel("TIPO/" + atacante.getTipo());
            etiquetaTipo.setFont(new Font("Monospaced", Font.BOLD, 15));
            etiquetaTipo.setHorizontalAlignment(SwingConstants.CENTER);
            panelInfoAtaque.add(etiquetaHP);
            panelInfoAtaque.add(etiquetaTipo);
            layeredPane.add(panelInfoAtaque, Integer.valueOf(2));
        }


        if (defensor != null) {
            ImageIcon imgDefensor = ICONOS_TIPO_DEFENSOR.get(defensor.getTipo());
            if (imgDefensor != null) {
                JLabel spriteEnemigo = new JLabel(imgDefensor);
                spriteEnemigo.setBounds(370, 30, imgDefensor.getIconWidth(), imgDefensor.getIconHeight()); 
                layeredPane.add(spriteEnemigo, Integer.valueOf(1));
            }
            // Panel info enemigo
            JPanel infoEnemigo = new JPanel();
            infoEnemigo.setLayout(null);
            infoEnemigo.setBackground(new Color(255, 255, 200, 200)); 
            infoEnemigo.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            infoEnemigo.setBounds(70, 20, 200, 50);

            JLabel nombreEnemigo = new JLabel(defensor.getNombre());
            nombreEnemigo.setFont(new Font("Monospaced", Font.BOLD, 15));
            nombreEnemigo.setBounds(10, 5, 100, 20);
            infoEnemigo.add(nombreEnemigo);

            JLabel nivelEnemigo = new JLabel("Nv " + defensor.getNivel());
            nivelEnemigo.setFont(new Font("Monospaced", Font.BOLD, 15));
            nivelEnemigo.setBounds(120, 5, 70, 20); 
            infoEnemigo.add(nivelEnemigo);

            JLabel barraVidaEnemigo = new JLabel(VidaActual(defensor));
            barraVidaEnemigo.setBounds(10, 25, 100, 10); 
            infoEnemigo.add(barraVidaEnemigo);
            layeredPane.add(infoEnemigo, Integer.valueOf(2));
        }
        panel.add(layeredPane);
        return panel;
    }

    public void setControlador(Controlador controlador) {
        this.controlador = controlador;
    }

    public void bienvenido() {
        if (!isVisible()) { // Solo hacer visible una vez
            setVisible(true); 
        }
        switchToNextPanel(showFirstPanel());
        timer.start(); 
    }


    public void entrenadores() { 
        nombre1 = jugador1Field.getText().trim();
        nombre2 = jugador2Field.getText().trim();
        if (nombre1.isEmpty() || nombre2.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese ambos nombres.", "Error", JOptionPane.ERROR_MESSAGE);
            error = true; 
        } else {
            error = false;
            controlador.setListaEntrenadores(nombre1, nombre2); 
            switchToNextPanel(showFourthPanel()); 
        }
    }
    
    @Override
    public void pokemones() {
        error = false; 
        // pokemonesElegidosTemporalmente.clear(); // Ya se limpia en showPokemonSelectionScreen
        // resetSeleccionablesVisual(); // Ya se hace en showPokemonSelectionScreen

        String entrenadorActualNombre;
        if (contadorEntrenadores == 0) { 
            entrenadorActualNombre = getNombre1();
        } else { 
            entrenadorActualNombre = getNombre2();
        }
        switchToNextPanel(showPokemonSelectionScreen(entrenadorActualNombre));
    }


    ArrayList<Pokemon> listaPokemonesMostrando; 
    byte contadorPokemonesMostrando = 0; 
    byte contadorEntrenadores = 0; 

    @Override
    public void mostrarPokemon(ArrayList<Pokemon> pokemonesDelEntrenador) {
        this.listaPokemonesMostrando = pokemonesDelEntrenador;
        this.contadorPokemonesMostrando = 0;
        if (pokemonesDelEntrenador != null && !pokemonesDelEntrenador.isEmpty()) {
            switchToNextPanel(showSixthPanel(listaPokemonesMostrando.get(0)));
        } else {
            System.err.println("Intentando mostrar un equipo vacío o nulo para el entrenador: " + (contadorEntrenadores == 0 ? nombre1 : nombre2) );
             if (contadorEntrenadores == 0) { 
                contadorEntrenadores++;
                controlador.avanzarEscena(); 
            } else { 
                controlador.avanzarEscena(); 
            }
        }
    }

    public void ganador(Entrenador entrenador) {
        String mensaje = (entrenador == null) ? "¡Es un empate o error!" : "¡El ganador es " + entrenador.getNombre() + "!";
        JOptionPane.showMessageDialog(this, mensaje, "Fin de la Batalla", JOptionPane.INFORMATION_MESSAGE);
        // Considerar opciones post-juego: reiniciar, cerrar.
        // currentPanel = -1; // Un estado para indicar fin de juego, si se maneja en keyPressed
        // Opciones de reinicio:
        // int reintentar = JOptionPane.showConfirmDialog(this, "Jugar de nuevo?", "Reiniciar", JOptionPane.YES_NO_OPTION);
        // if (reintentar == JOptionPane.YES_OPTION) {
        //    controlador.reiniciarJuegoCompleto(); // Necesitaría este método en controlador
        // } else {
        //    System.exit(0);
        // }
    }

    // --- MÉTODO MODIFICADO PARA MOSTRAR DIÁLOGO DE CAMBIO DE POKÉMON ---
    @Override
    public Pokemon elegirPokemon(Entrenador entrenador) {
        System.out.println("VistaPokemonGUI: Se necesita elegir Pokémon para " + entrenador.getNombre());
        
        boolean puedeContinuar = false;
        if (entrenador.getEquipo() != null) {
            for (Pokemon p : entrenador.getEquipo()) {
                if (p.getHp() > 0) {
                    puedeContinuar = true;
                    break;
                }
            }
        }

        if (!puedeContinuar) {
            JOptionPane.showMessageDialog(this, 
                "¡Todos los Pokémon de " + entrenador.getNombre() + " están debilitados!", 
                "Equipo Derrotado", 
                JOptionPane.WARNING_MESSAGE);
            return null; // Controlador debe manejar esto (ej. el otro entrenador gana)
        }

        PokemonChangeDialog dialog = new PokemonChangeDialog(this, entrenador);
        dialog.setVisible(true); // Bloquea hasta que el diálogo se disponga

        Pokemon elegido = dialog.getPokemonSeleccionado();
        if (elegido != null) {
            System.out.println(entrenador.getNombre() + " ha elegido a " + elegido.getNombre());
        } else {
            // Esto podría ocurrir si el diálogo se cierra sin selección y no hay fallback robusto.
            // El PokemonChangeDialog actual está diseñado para forzar una selección si hay opciones.
            // Si todos están debilitados, el check anterior lo captura.
            System.out.println(entrenador.getNombre() + " no eligió un Pokémon (diálogo cerrado / sin opción válida).");
             // Fallback de emergencia si el diálogo se cierra inesperadamente sin selección
            // y aún hay Pokémon válidos (no debería suceder con el diseño actual del diálogo)
            if (puedeContinuar) { 
                for (Pokemon p : entrenador.getEquipo()) {
                    if (p.getHp() > 0) {
                        System.out.println("Fallback de emergencia: " + entrenador.getNombre() + " elige a " + p.getNombre());
                        return p; // Retorna el primer Pokémon válido encontrado
                    }
                }
            }
        }
        return elegido;
    }


    public Ataque elegirAtaque(Pokemon pokemon) {
        // La elección de ataque se hace con los botones en showSeventhPanel.
        return null;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            // System.out.println("Enter presionado en panel: " + currentPanel + ", error: " + error); // Debug
            switch (currentPanel) {
                case 2: 
                    switchToNextPanel(showThirdPanel());
                    break;
                case 3: 
                    entrenadores(); 
                    // No avanzar escena aquí, entrenadores() ya cambia de panel si es exitoso.
                    // El flujo continúa con el Enter en el panel 4.
                    break;
                case 4: 
                    controlador.avanzarEscena(); 
                    break;
                case 8: 
                    if (pokemonesElegidosTemporalmente.size() == 3) {
                        error = false;
                        pokemon1 = pokemonesElegidosTemporalmente.get(0).nombre;
                        pokemon2 = pokemonesElegidosTemporalmente.get(1).nombre;
                        pokemon3 = pokemonesElegidosTemporalmente.get(2).nombre;
                        controlador.setListaPokemones(pokemon1, pokemon2, pokemon3);
                        controlador.avanzarEscena(); 
                    } else {
                        error = true;
                        JOptionPane.showMessageDialog(this, "Debes seleccionar 3 Pokémon.", "Selección Incompleta", JOptionPane.ERROR_MESSAGE);
                    }
                    break;
                case 6: 
                    if (listaPokemonesMostrando != null && contadorPokemonesMostrando < listaPokemonesMostrando.size() - 1) {
                        contadorPokemonesMostrando++;
                        switchToNextPanel(showSixthPanel(listaPokemonesMostrando.get(contadorPokemonesMostrando)));
                    } else { 
                        contadorPokemonesMostrando = 0; 
                        if (contadorEntrenadores == 0) { 
                            contadorEntrenadores++; 
                            controlador.avanzarEscena(); 
                        } else { 
                            controlador.avanzarEscena(); 
                            // La escena 6 del controlador (prepararBatalla) debería llamar a vista.mostrarPanelBatalla
                        }
                    }
                    break;
                case 7: 
                    // Enter en batalla no hace nada por defecto, acciones son por botones.
                    break;
                default:
                    // Evitar avances de escena no controlados
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    public void mostrarPanelBatalla(Pokemon atacante, Pokemon defensor) {
        if (atacante == null && defensor == null) {
            System.err.println("Ambos Pokémon son nulos al intentar mostrar panel de batalla. Fin de juego?");
            // Podría ser un empate o un error en la lógica del controlador.
            if (controlador != null) {
                // Intentar determinar un ganador si uno de los entrenadores no tiene más Pokémon.
                // Esta lógica es compleja y depende de cómo el controlador maneje 'pokemon1' y 'pokemon2'.
                // Por ahora, solo se muestra un mensaje.
            }
            ganador(null); // Indica un posible empate o estado indeterminado
            return;
        }
        // Si solo uno es null, el otro podría ser el ganador si el nulo representa un equipo sin más Pokémon.
        // Esta lógica de quién gana si un Pokémon es null debería estar en el Controlador.

        switchToNextPanel(showSeventhPanel(atacante, defensor));
    }
}