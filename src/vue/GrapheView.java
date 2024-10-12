package vue;

import controller.Boutoncontrol;
import modele.Graphe;
import modele.PlusCourtChemin;
import modele.Sommet;
import modele.Arc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

public class GrapheView extends JPanel {
    private Graphe graphe;
    private static int RAYON_SOMMET = 20;
    private static int WINDOWWIDTH;
    private static int WINDOWHEIGHT;
    public static int BLOCK_SIZE = 50;
    private Sommet sommetSelect;
    private Arc arcSelect;
    private PlusCourtChemin plusCourtChemin;
    private JComboBox<String> sommet1ComboBox;
    private JComboBox<String> sommet2ComboBox;
    private JButton createArcButton;

    private JButton deleteAllButton;

    private JButton algoButton;

    private JButton toggleColorsButton;

    private boolean darkMode = false;

    private Color backgroundColorLight = new Color(240, 240, 240); // Couleur claire pour l'arrière-plan
    private Color backgroundColorDark = new Color(40, 40, 40); // Couleur sombre pour l'arrière-plan
    private Color sommetColorLight =  new Color(100, 100, 100);
    private Color sommetColorDark = new Color(80, 80, 80); // Couleur sombre pour les sommets
    private Color arcColorLight = Color.BLACK; // Couleur claire pour les arcs
    private Color arcColorDark = Color.WHITE; // Couleur sombre pour les arcs


    public GrapheView(Graphe g, int w, int h) {
        this.graphe = g;
        WINDOWWIDTH = w;
        WINDOWHEIGHT = h;
        plusCourtChemin = null;

        // Initialisation des composants Swing
        sommet1ComboBox = new JComboBox<>();
        sommet2ComboBox = new JComboBox<>();
        createArcButton = new JButton("Créer Arc");

        // Ajout des listeners pour le bouton
        Boutoncontrol boutoncontrol = new Boutoncontrol(null, null,this, graphe);
        createArcButton.addActionListener(boutoncontrol);

        algoButton = new JButton("Chemin le plus court");

        // Ajout des listeners pour le bouton
        algoButton.addActionListener(boutoncontrol);
        add(algoButton);


        // Ajout des composants à la vue
        setLayout(new FlowLayout());
        add(sommet1ComboBox);
        add(sommet2ComboBox);
        add(createArcButton);

        deleteAllButton = new JButton("Supprimer tout");
        deleteAllButton.addActionListener(boutoncontrol);
        add(deleteAllButton);

        toggleColorsButton = new JButton("Mode claire/sombre");
        toggleColorsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleColors(); // Appeler la méthode pour changer les couleurs
            }
        });
        add(toggleColorsButton);


        // Initialisation des listes déroulantes avec les ID des sommets
        initComboBoxes();
    }

    // Méthode pour initialiser les listes déroulantes avec les ID des sommets
    public void initComboBoxes() {
        List<Sommet> sommets = graphe.getSommets();
        sommet1ComboBox.removeAllItems();
        sommet2ComboBox.removeAllItems();

        for (Sommet sommet : sommets) {
            sommet1ComboBox.addItem(sommet.getVille());
            sommet2ComboBox.addItem(sommet.getVille());
        }
    }
    // Méthode pour mettre à jour les listes déroulantes avec les ID des sommets
    public void updateComboBoxes() {
        List<Sommet> sommets = graphe.getSommets();
        sommet1ComboBox.removeAllItems();
        sommet2ComboBox.removeAllItems();

        for (Sommet sommet : sommets) {
            sommet1ComboBox.addItem(sommet.getVille());
            sommet2ComboBox.addItem(sommet.getVille());
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);


        drawPoints(g);
    }

    private void toggleColors() {
        darkMode = !darkMode; // Inverser l'état du mode sombre

        // Redessiner la vue pour refléter les changements de couleur
        repaint();
    }

    public void updateSommetComboBoxes() {
        updateComboBoxes(); //Ajoute les nouveau sommet et arc à leur liste
    }

    private void drawPoints(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color sommetColor;
        Color arcColor;
        if (darkMode) {
            g2d.setColor(backgroundColorDark);
            sommetColor = sommetColorDark;
            arcColor = arcColorDark;

        } else {
            g2d.setColor(backgroundColorLight);
            sommetColor = sommetColorLight;
            arcColor = arcColorLight;
        }
        g2d.fillRect(0, 0, WINDOWWIDTH - WINDOWWIDTH / 4, WINDOWHEIGHT);
        g2d.setColor(Color.WHITE);
        g2d.fillRect(WINDOWWIDTH - WINDOWWIDTH / 4, 0, WINDOWWIDTH / 4, WINDOWHEIGHT);

        //Ecriture du Plus court chemin :
        ecritPlusCourtChemin(g2d);


        List<Sommet> sommets = graphe.getSommets();
        List<Arc> arcs = graphe.getArcs();

        g2d.setColor(Color.DARK_GRAY);
        g2d.setStroke(new BasicStroke(RAYON_SOMMET / 4));


        for (Arc arc : arcs) {
            g2d.setColor(arcColor);

            int sommet1Id = arc.getSommetId1();
            int sommet2Id = arc.getSommetId2();

            Sommet sommet1 = null;
            Sommet sommet2 = null;
            //g2d.setColor(Color.WHITE);
            // Recherche des sommets correspondants
            for (Sommet sommet : sommets) {


                if (sommet.getIdentifiant() == sommet1Id) {
                    sommet1 = sommet;
                } else if (sommet.getIdentifiant() == sommet2Id) {
                    sommet2 = sommet;
                }

                // Si les deux sommets ont été trouvés, on arrête la recherche
                if (sommet1 != null && sommet2 != null) {
                    break;
                }
            }

            // Si les deux sommets ont été trouvés, on dessine le trait entre eux avec une flèche
            if (sommet1 != null && sommet2 != null) {
                int x1 = sommet1.getPositionX() + RAYON_SOMMET / 2;
                int y1 = sommet1.getPositionY() + RAYON_SOMMET / 2;
                int x2 = sommet2.getPositionX() + RAYON_SOMMET / 2;
                int y2 = sommet2.getPositionY() + RAYON_SOMMET / 2;

                // Dessin de la ligne reliant les sommets
                g2d.drawLine(x1, y1, x2, y2);

                // Dessin de la flèche à l'extrémité du trait
                //g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(RAYON_SOMMET / 6));
                drawArrow(g2d, x1, y1, x2, y2);
                g2d.setStroke(new BasicStroke(RAYON_SOMMET / 4));

            }
        }
        for (Sommet sommet : sommets) {
            g2d.setColor(sommetColor);
            int x = sommet.getPositionX();
            int y = sommet.getPositionY();
            g2d.fillOval(x, y, RAYON_SOMMET, RAYON_SOMMET);
            g2d.drawString(sommet.getVille(), x, y - 20);
        }

        if (this.sommetSelect != null) {
        }

        if (this.arcSelect != null) {
        }

    }
    private void drawArrow(Graphics2D g2d, int x1, int y1, int x2, int y2) { //pour les fleches
        int arrowSize = RAYON_SOMMET - RAYON_SOMMET/4 ;
        int arrowAngle = 30;
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowX = x2 ;
        int arrowY = y2 ;

        //g2d.drawLine(x2, y2, arrowX, arrowY);
        g2d.drawLine(arrowX, arrowY, (int) (arrowX - arrowSize * Math.cos(angle + Math.toRadians(arrowAngle))), (int) (arrowY - arrowSize * Math.sin(angle + Math.toRadians(arrowAngle))));
        g2d.drawLine(arrowX, arrowY, (int) (arrowX - arrowSize * Math.cos(angle - Math.toRadians(arrowAngle))), (int) (arrowY - arrowSize * Math.sin(angle - Math.toRadians(arrowAngle))));
    }

    private void ecritPlusCourtChemin(Graphics2D g2d){ //affichage du plus court chemin
        g2d.setColor(sommetColorDark);
        if(plusCourtChemin != null){
            double distance = plusCourtChemin.getDistanceTotal();
            g2d.drawString("Plus court Chemin : ", WINDOWWIDTH - WINDOWWIDTH / 5, WINDOWHEIGHT / 4 -(WINDOWHEIGHT/15));

            int posY = WINDOWHEIGHT / 4;
            for(int i = 0; i < plusCourtChemin.getChemin().size(); i++){
                g2d.drawString(plusCourtChemin.getChemin().get(plusCourtChemin.getChemin().size()-i-1), WINDOWWIDTH - WINDOWWIDTH / 5, posY);
                posY += WINDOWHEIGHT / 15;
            }

            g2d.drawString("Distance totale : " + distance, WINDOWWIDTH - WINDOWWIDTH / 5, posY);
        }
    }

    public static int getWINDOWHEIGHT() {
        return WINDOWHEIGHT;
    }

    public static int getWINDOWWIDTH() {
        return WINDOWWIDTH;
    }

    public static int getRayonSommet() {
        return RAYON_SOMMET;
    }

    public Sommet getSommetSelect() {
        return sommetSelect;
    }

    public void setSommetSelect(Sommet sommetSelect) {
        this.sommetSelect = sommetSelect;
    }

    public static int getBlockSize() {
        return BLOCK_SIZE;
    }

    public void setArcSelect(Arc a) {
        this.arcSelect = a;
    }

    public JComboBox<String> getSommet1ComboBox() {
        return sommet1ComboBox;
    }

    public JComboBox<String> getSommet2ComboBox() {
        return sommet2ComboBox;
    }
    
    public Graphe getGraphe() {
        return graphe;
    }

    public void setPlusCourtChemin(PlusCourtChemin plusCourtChemin) {
        this.plusCourtChemin = plusCourtChemin;
    }
}

