package main;

import javax.swing.SwingUtilities;

import view.GUI;

public class App {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);

        });
    }
}
 