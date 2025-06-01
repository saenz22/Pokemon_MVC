package vista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image; // Importación añadida
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL; // Importación añadida

public class PanelPokemonConMejoras {

    private JFrame frame;
    private BackgroundPanel mainPanel;
    private JPanel selectedPokemonPanel = null; // Para rastrear el panel de Pokémon actualmente seleccionado

    private final Color defaultBorderColor = Color.GRAY;
    private final Color selectedBorderColor = Color.CYAN;
    private final Color disabledBorderColor = Color.DARK_GRAY;
    private final int defaultBorderSize = 1;
    private final int selectedBorderSize = 3;

    // Datos de los entrenadores y Pokémon
    // Fila 0: Entrenadores
    // Filas 1-3: Pokémon
    private final String[][] cellData = {
            {"<html><center>Entrenador 1:<br>Ash</center></html>", "<html><center>Entrenador 2:<br>Misty</center></html>"},
            {"<html><center>Pikachu<br>Nivel: 75</center></html>", "<html><center>Staryu<br>Nivel: 68</center></html>"},
            {"<html><center>Charizard<br>Nivel: 70</center></html>", "<html><center>Gyarados<br>Nivel: 72</center></html>"},
            {"<html><center>Snorlax<br>Nivel: 65</center></html>", "<html><center>Psyduck<br>Nivel: 60</center></html>"}
    };

    // Para almacenar referencias a los paneles de las celdas y su estado
    private JPanel[][] cellPanels = new JPanel[4][2];
    private JLabel[][] pokemonNameLabels = new JLabel[4][2]; // Para cambiar color de texto
    private JLabel[][] pokemonHealthBarLabels = new JLabel[4][2]; // Para las barras de vida
    private boolean[] columnLocked = {false, false}; // Estado de bloqueo para cada columna

    public PanelPokemonConMejoras() {
        frame = new JFrame("Panel de Entrenadores y Pokémon V2");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(700, 600); 

        // Ruta de la imagen de fondo (debe estar en la carpeta 'vista' en el classpath)
        String backgroundPath = "/vista/election.png"; 
        
        mainPanel = new BackgroundPanel(backgroundPath); // Pasar la ruta de la imagen
        mainPanel.setLayout(new GridLayout(4, 2, 10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Crear y añadir las celdas ---
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 2; col++) {
                boolean isPokemonCell = (row > 0); 
                JPanel cell = createCell(cellData[row][col], row, col, isPokemonCell);
                cellPanels[row][col] = cell;
                mainPanel.add(cell);
            }
        }

        frame.setContentPane(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createCell(String text, int row, int col, boolean isPokemonCell) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS)); 
        panel.setOpaque(false); // Hacer el panel de la celda transparente para que se vea el fondo del mainPanel

        JLabel nameLabel = new JLabel(text, SwingConstants.CENTER);
        nameLabel.setForeground(Color.WHITE); // Considera cambiar esto si tu fondo es claro
        nameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        pokemonNameLabels[row][col] = nameLabel; 

        panel.add(nameLabel);

        if (isPokemonCell) {
            JLabel healthBarLabel = new JLabel(); // No más texto por defecto
            healthBarLabel.setPreferredSize(new Dimension(100, 20)); 
            
            // Cargar imagen de la barra de vida
            // Asegúrate que la imagen está en la carpeta 'vista' dentro de tu classpath (ej. src/vista)
            URL healthBarImageUrl = getClass().getResource("/vista/WhatsApp Image 2025-05-24 at 6.17.00 PM (1).jpeg");
            if (healthBarImageUrl != null) {
                ImageIcon healthBarIcon = new ImageIcon(healthBarImageUrl);
                healthBarLabel.setIcon(healthBarIcon);
            } else {
                healthBarLabel.setText("Vida?"); // Texto de fallback si no se carga la imagen
                System.err.println("No se pudo cargar la imagen de la barra de vida: /vista/WhatsApp Image 2025-05-24 at 6.17.00 PM (1).jpeg");
            }
            
            healthBarLabel.setHorizontalAlignment(SwingConstants.CENTER); // Centrar icono/texto
            healthBarLabel.setFont(new Font("Monospaced", Font.PLAIN, 42));
            healthBarLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre el nombre y la barra de vida
            panel.add(healthBarLabel);
            pokemonHealthBarLabels[row][col] = healthBarLabel; 

            Border initialBorder = BorderFactory.createLineBorder(defaultBorderColor, defaultBorderSize);
            panel.setBorder(BorderFactory.createCompoundBorder(initialBorder, BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            panel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (columnLocked[col]) {
                        System.out.println("Columna " + (col + 1) + " está bloqueada.");
                        return;
                    }

                    JPanel clickedPanel = (JPanel) e.getSource();

                    if (selectedPokemonPanel != null && selectedPokemonPanel != clickedPanel) {
                        for(int r=1; r<4; r++) { 
                            for(int c=0; c<2; c++) {
                                if (cellPanels[r][c] == selectedPokemonPanel && !columnLocked[c]) {
                                     Border previousBorder = BorderFactory.createLineBorder(defaultBorderColor, defaultBorderSize);
                                     selectedPokemonPanel.setBorder(BorderFactory.createCompoundBorder(previousBorder, BorderFactory.createEmptyBorder(10,10,10,10)));
                                     break;
                                }
                            }
                        }
                    }
                    
                    Border highlightBorder = BorderFactory.createLineBorder(selectedBorderColor, selectedBorderSize);
                    clickedPanel.setBorder(BorderFactory.createCompoundBorder(highlightBorder, BorderFactory.createEmptyBorder(10,10,10,10)));
                    selectedPokemonPanel = clickedPanel;

                    columnLocked[col] = true;
                    disablePokemonInColumn(col, clickedPanel);

                    String content = "Celda";
                    if (pokemonNameLabels[row][col] != null) {
                        content = pokemonNameLabels[row][col].getText();
                        content = content.replaceAll("<[^>]*>", " ").replace("  ", " ").trim(); 
                    }
                    System.out.println(content + " seleccionado. Columna " + (col + 1) + " bloqueada.");
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    if (columnLocked[col]) return; 
                    JPanel hoveredPanel = (JPanel) e.getSource();
                    if (hoveredPanel != selectedPokemonPanel) {
                        Border hoverBorder = BorderFactory.createLineBorder(selectedBorderColor.darker(), defaultBorderSize + 1);
                        hoveredPanel.setBorder(BorderFactory.createCompoundBorder(hoverBorder, BorderFactory.createEmptyBorder(10,10,10,10)));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    if (columnLocked[col]) return; 
                    JPanel exitedPanel = (JPanel) e.getSource();
                    if (exitedPanel != selectedPokemonPanel) {
                         Border exitedBorder = BorderFactory.createLineBorder(defaultBorderColor, defaultBorderSize);
                         exitedPanel.setBorder(BorderFactory.createCompoundBorder(exitedBorder, BorderFactory.createEmptyBorder(10,10,10,10)));
                    }
                }
            });

        } else { 
            panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); 
        }
        return panel;
    }

    private void disablePokemonInColumn(int lockedCol, JPanel selectedPanelInColumn) {
        for (int r = 1; r < 4; r++) { 
            JPanel panelToDisable = cellPanels[r][lockedCol];
            if (panelToDisable != selectedPanelInColumn) { 
                Border disabledB = BorderFactory.createLineBorder(disabledBorderColor, defaultBorderSize);
                panelToDisable.setBorder(BorderFactory.createCompoundBorder(disabledB, BorderFactory.createEmptyBorder(10,10,10,10)));
                
                if (pokemonNameLabels[r][lockedCol] != null) {
                    pokemonNameLabels[r][lockedCol].setForeground(Color.LIGHT_GRAY);
                }
                // Para la barra de vida, si es un icono, cambiar el icono a una versión "deshabilitada"
                // o simplemente dejarlo como está, ya que cambiar el foreground no afectará al icono.
                // Si es texto, sí se cambiaría.
                if (pokemonHealthBarLabels[r][lockedCol] != null && pokemonHealthBarLabels[r][lockedCol].getIcon() == null) {
                     pokemonHealthBarLabels[r][lockedCol].setForeground(Color.GRAY);
                }
            }
        }
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            // Cargar la imagen de fondo usando getResource
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) {
                this.backgroundImage = new ImageIcon(imageUrl).getImage();
            } else {
                System.err.println("No se pudo cargar la imagen de fondo: " + imagePath);
                this.backgroundImage = null;
            }
            // No es necesario setOpaque(false) aquí si la imagen cubre todo el panel
            // y los paneles hijos (celdas) son los que se hacen transparentes (setOpaque(false)).
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                // Escalar la imagen para que se ajuste al tamaño del panel
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                // Si no hay imagen, pintar un fondo oscuro por defecto
                g.setColor(Color.DARK_GRAY); // O el color que prefieras
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PanelPokemonConMejoras();
            }
        });
    }
}