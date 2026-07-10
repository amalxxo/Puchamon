package com.uce.programacion2.Dungeon_Crawler.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PanelMenu extends JPanel {

    private VentanaPrincipal ventana;

    private static final Color BG_TOP     = new Color(8,  4, 25);
    private static final Color BG_BOT     = new Color(22, 9, 58);
    private static final Color GOLD       = new Color(255, 200, 50);
    private static final Color GOLD_DIM   = new Color(165, 115, 18);
    private static final Color BTN_NORMAL = new Color(18, 10, 48);
    private static final Color BTN_HOVER  = new Color(46, 22, 98);
    private static final Color BTN_BORDER = new Color(108, 58, 210);
    private static final Color BTN_TEXT   = new Color(215, 195, 255);
    private static final Color RED_NORMAL = new Color(95, 14, 14);
    private static final Color RED_HOVER  = new Color(155, 32, 32);
    private static final Color RED_BORDER = new Color(185, 48, 48);

    public PanelMenu(VentanaPrincipal ventana) {
        this.ventana = ventana;
        setLayout(new BorderLayout());
        construir();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo degradado oscuro
        g2.setPaint(new GradientPaint(0, 0, BG_TOP, 0, getHeight(), BG_BOT));
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Cuadrícula sutil
        g2.setColor(new Color(90, 50, 190, 22));
        for (int y = 0; y < getHeight(); y += 50) g2.drawLine(0, y, getWidth(), y);
        for (int x = 0; x < getWidth();  x += 80) g2.drawLine(x, 0, x, getHeight());

        // Línea dorada separadora bajo el header
        g2.setStroke(new BasicStroke(1.2f));
        g2.setColor(new Color(GOLD.getRed(), GOLD.getGreen(), GOLD.getBlue(), 75));
        g2.drawLine(55, 148, getWidth() - 55, 148);
    }

    private void construir() {

        // ── HEADER ──────────────────────────────────────────────────────────
        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBorder(BorderFactory.createEmptyBorder(36, 0, 14, 0));

        JLabel titulo = new JLabel("MONSTER QUEST");
        titulo.setFont(new Font("Serif", Font.BOLD, 54));
        titulo.setForeground(GOLD);
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sub = new JLabel("— Eclipse Realms —");
        sub.setFont(new Font("Serif", Font.ITALIC, 20));
        sub.setForeground(GOLD_DIM);
        sub.setAlignmentX(Component.CENTER_ALIGNMENT);

        header.add(titulo);
        header.add(Box.createVerticalStrut(6));
        header.add(sub);
        add(header, BorderLayout.NORTH);

        // ── BOTONES ─────────────────────────────────────────────────────────
        JPanel centerWrap = new JPanel(new GridBagLayout());
        centerWrap.setOpaque(false);

        JPanel col = new JPanel();
        col.setOpaque(false);
        col.setLayout(new BoxLayout(col, BoxLayout.Y_AXIS));
        col.setBorder(BorderFactory.createEmptyBorder(8, 0, 28, 0));

        String[] etiquetas = {
            "  ★  Nuevo Juego",
            "  ⚔  Ver Equipo",
            "  ✧  Inventario",
            "  ▶  Explorar",
            "  ✷  Tienda",
            "  ♟  Entrenador",
            "  ✴  Jefe Final",
            "  ✕  Salir"
        };
        boolean[] danger = { false, false, false, false, false, false, false, true };
        JButton[] btns   = new JButton[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++) {
            btns[i] = crearBoton(etiquetas[i], danger[i]);
            col.add(btns[i]);
            if (i < etiquetas.length - 1) col.add(Box.createVerticalStrut(8));
        }

        centerWrap.add(col);
        add(centerWrap, BorderLayout.CENTER);

        // ── FOOTER ──────────────────────────────────────────────────────────
        JLabel version = new JLabel("v1.0  •  Programación II  •  UCE");
        version.setFont(new Font("SansSerif", Font.PLAIN, 11));
        version.setForeground(new Color(90, 70, 150));
        version.setHorizontalAlignment(SwingConstants.CENTER);
        version.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(version, BorderLayout.SOUTH);

        // ── ACCIONES (lógica idéntica al original) ───────────────────────────

        btns[0].addActionListener(e -> {
            String nombre = JOptionPane.showInputDialog(this, "Nombre del entrenador:");
            if (nombre == null || nombre.isBlank()) return;
            ventana.getJuego().crearJugador(nombre);
            ventana.mostrar("INICIAL");
        });

        btns[1].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelEquipo().actualizar();
            ventana.mostrar("EQUIPO");
        });

        btns[2].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelInventario().actualizar();
            ventana.mostrar("INVENTARIO");
        });

        btns[3].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero crea un entrenador."); return;
            }
            if (ventana.getJuego().getJugador().getCantidadCriaturas() == 0) {
                JOptionPane.showMessageDialog(this, "Primero elige una criatura inicial."); return;
            }
            ventana.getPanelMapa().actualizarMapa();
            ventana.mostrar("MAPA");
        });

        btns[4].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelTienda().actualizar();
            ventana.mostrar("TIENDA");
        });

        btns[5].addActionListener(e -> {
            if (ventana.getJuego().getJugador() == null) {
                JOptionPane.showMessageDialog(this, "Primero inicia una partida."); return;
            }
            ventana.getPanelEntrenador().actualizar();
            ventana.mostrar("ENTRENADOR");
        });

        btns[6].addActionListener(e ->
            JOptionPane.showMessageDialog(this,
                "Esta función se implementará desde el Panel de Batalla."));

        btns[7].addActionListener(e -> System.exit(0));
    }

    private JButton crearBoton(String texto, boolean danger) {
        Color bgNormal = danger ? RED_NORMAL : BTN_NORMAL;
        Color bgHover  = danger ? RED_HOVER  : BTN_HOVER;
        Color border   = danger ? RED_BORDER : BTN_BORDER;
        boolean[] hovered = {false};

        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(hovered[0] ? bgHover : bgNormal);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.setColor(border);
                g2.setStroke(new BasicStroke(hovered[0] ? 2f : 1.2f));
                g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                if (hovered[0]) {
                    g2.setColor(new Color(border.getRed(), border.getGreen(), border.getBlue(), 55));
                    g2.setStroke(new BasicStroke(5f));
                    g2.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, 10, 10);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };

        btn.addMouseListener(new MouseAdapter() {
            @Override public void mouseEntered(MouseEvent e) { hovered[0] = true;  btn.repaint(); }
            @Override public void mouseExited (MouseEvent e) { hovered[0] = false; btn.repaint(); }
        });

        btn.setForeground(BTN_TEXT);
        btn.setFont(new Font("Serif", Font.BOLD, 15));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(252, 40));
        btn.setPreferredSize(new Dimension(252, 40));

        return btn;
    }
}
