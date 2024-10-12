package vue;

import controller.Boutoncontrol;
import controller.Maincontrol;
import modele.Sommet;

import javax.swing.*;
import java.awt.*;

public class SommetView extends JFrame{

    private JTextField xField, yField, villeField;
    private Sommet s1;
    private Maincontrol maincontrol;

    public SommetView(Sommet sommet, Maincontrol m) {
        this.s1 = sommet;
        this.maincontrol = m;

        // Création des composants de la fenêtre
        JLabel xLabel = new JLabel("X:");
        xField = new JTextField(10);
        xField.setText(String.valueOf(s1.getPositionX()));

        JLabel yLabel = new JLabel("Y:");
        yField = new JTextField(10);
        yField.setText(String.valueOf(s1.getPositionY()));

        JLabel villeLabel = new JLabel("Nom:");
        villeField = new JTextField(10);
        villeField.setText(String.valueOf(s1.getVille()));

        JButton updateButton = new JButton("Mettre à jour sommet");
        JButton deleteButton = new JButton("Supprimer sommet");

        Boutoncontrol boutoncontrol = new Boutoncontrol(this, null,null,maincontrol.getGraphe() );
        // Ajout d'un gestionnaire d'événements au bouton de mise à jour
        updateButton.addActionListener(boutoncontrol);
        deleteButton.addActionListener(boutoncontrol);

        // Création du panneau et ajout des composants
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(xLabel);
        panel.add(xField);
        panel.add(yLabel);
        panel.add(yField);
        panel.add(villeLabel);
        panel.add(villeField);
        panel.add(updateButton);
        panel.add(deleteButton);

        // Configuration de la fenêtre
        setTitle("Attribut Sommet");
        setResizable(false);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }
    public JTextField getXField() {
        return this.xField;
    }

    public JTextField getYField() {
        return this.yField;
    }

    public JTextField getVilleField() {
        return this.villeField;
    }

    public Sommet getSommet() {
        return this.s1;
    }
    public Maincontrol getMaincontrol(){
        return maincontrol;
    }

}
