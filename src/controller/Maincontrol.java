package controller;

import modele.*;
import vue.*;
import javax.swing.*;
import java.awt.*;


public class Maincontrol {
    private Graphe graphe;
    private GrapheView grapheView;
    private Mousecontrol mousecontrol;

    public Maincontrol(){
        JFrame frame = new JFrame("Graphe Edition");

        // Obtenir la taille de l'écran
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        // Calculer les dimensions de la fenêtre
        int WINDOWWIDTH = screenWidth - 200;
        int WINDOWHEIGHT = screenHeight - 200;

        // Calculer la position de la fenêtre
        int windowX = (screenWidth - WINDOWWIDTH) / 2;
        int windowY = (screenHeight - WINDOWHEIGHT) / 2;

        // Définir les dimensions et la position de la fenêtre
        frame.setBounds(windowX, windowY, WINDOWWIDTH, WINDOWHEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        this.graphe = new Graphe();
        this.grapheView = new GrapheView(graphe,WINDOWWIDTH, WINDOWHEIGHT );
        this.mousecontrol = new Mousecontrol(this.graphe, this.grapheView, WINDOWWIDTH, WINDOWHEIGHT, this);
        grapheView.addMouseListener(mousecontrol);
        frame.add(grapheView);
        frame.setVisible(true);

    }

    public Graphe getGraphe() {
        return this.graphe;
    }

    public GrapheView getGrapheView() {
        return grapheView;
    }
}
