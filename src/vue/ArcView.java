package vue;

import controller.Boutoncontrol;
import controller.Maincontrol;
import modele.Arc;
import modele.Graphe;
import modele.Sommet;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ArcView extends JFrame {
    private JComboBox<String> s1ComboBox;
    private JComboBox<String> s2ComboBox;
    private Arc arc;
    private Maincontrol maincontrol;

    private Graphe graphe;
    private JTextField rueField;

    //fenetre de zoom de l'arc

    public ArcView(Arc arc, Maincontrol maincontrol, Graphe graphe) {
        this.arc = arc;
        this.maincontrol = maincontrol;
        this.graphe = graphe;
        int x1 = getArc().getS1().getPositionX();
        int y1 = getArc().getS1().getPositionY();
        int x2 = getArc().getS2().getPositionX();
        int y2 = getArc().getS2().getPositionY();

        // Création des composants de la fenêtre
        JLabel distanceInitituler  = new JLabel("  Arc de ditance :");
        JLabel distance  = new JLabel(""+calculerDistance(x1, y1, x2, y2));

        JLabel rueLabel = new JLabel("  Rue:");
        rueField = new JTextField(10);
        rueField.setText(String.valueOf(arc.getRue()));

        JLabel s1Label = new JLabel("  Sommet 1:");
        s1ComboBox = new JComboBox<String>();
        List<String> sommetIds = getSommetsIds();
        for (String id : sommetIds) {
            s1ComboBox.addItem(id);
        }
        s1ComboBox.setSelectedItem(arc.getS1().getVille());

        JLabel s2Label = new JLabel("  Sommet 2:");
        s2ComboBox = new JComboBox<String>();
        for (String id : sommetIds) {
            s2ComboBox.addItem(id);
        }
        s2ComboBox.setSelectedItem(arc.getS2().getVille());

        JButton saveButton = new JButton("Mettre à jour arc");
        JButton deleteButton = new JButton("Supprimer arc");

        // Ajout des composants à la fenêtre
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));
        panel.add(distanceInitituler);
        panel.add(distance);
        panel.add(rueLabel);
        panel.add(rueField);
        panel.add(s1Label);
        panel.add(s1ComboBox);
        panel.add(s2Label);
        panel.add(s2ComboBox);
        panel.add(saveButton);
        panel.add(deleteButton);
        add(panel);
        Boutoncontrol boutoncontrol = new Boutoncontrol(null, this, null, graphe);
        // Ajout de l'action pour le bouton "Save"
        saveButton.addActionListener(boutoncontrol);

        // Ajout de l'action pour le bouton "Delete"
        deleteButton.addActionListener(boutoncontrol);

        // Configuration de la fenêtre
        setTitle("Attribut Arc");
        setResizable(false);
        getContentPane().add(panel);
        pack();
        setVisible(true);
    }

    public double calculerDistance(int x1, int y1, int x2, int y2) {
        int deltaX = x2 - x1;
        int deltaY = y2 - y1;
        double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY); //calcule vectoriel
        return Math.round(distance * 100.0) / 100.0; // Arrondir à la centième décimale
    }


    private List<String> getSommetsIds() {
        List<String> sommetIds = new ArrayList<>();
        if (graphe != null) {
            // Récupérer les IDs des sommets existants depuis le contrôleur principal
            List<Sommet> sommets = graphe.getSommets();
            for (Sommet sommet : sommets) {
                sommetIds.add(sommet.getVille());
            }
        }
        return sommetIds;
    }

    public JTextField getRueField() {
        return rueField;
    }

    public JComboBox<String> getS1ComboBox() {
        return s1ComboBox;
    }

    public JComboBox<String> getS2ComboBox() {
        return s2ComboBox;
    }

    public Arc getArc() {
        return arc;
    }

    public Maincontrol getMaincontrol() {
        return maincontrol;
    }
}
