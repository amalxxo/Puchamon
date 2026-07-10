package com.uce.programacion2.Dungeon_Crawler.Principal;

import javax.swing.SwingUtilities;

import com.uce.programacion2.Dungeon_Crawler.GUI.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            new VentanaPrincipal();

        });

    }

}