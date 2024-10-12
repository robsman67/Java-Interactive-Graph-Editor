package controller;

import modele.*;
import vue.*;



import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

//tous les cliques de souris
public class Mousecontrol  extends MouseAdapter implements MouseListener {
    private Graphe graphe;
    private GrapheView grapheView;
    private static int WINDOWWIDTH;
    private static int WINDOWHEIGHT;
    private static int BLOCK_SIZE;
    private boolean ajout;
    private Maincontrol maincontrol;

    private ArcView arcView;

    public Mousecontrol(Graphe g, GrapheView gv, int w, int h, Maincontrol m){
        this.graphe = g;
        this.grapheView = gv;
        this.WINDOWWIDTH = w;
        this.WINDOWHEIGHT = h;
        BLOCK_SIZE = this.grapheView.getBlockSize();
        this.ajout = false;
        maincontrol = m;

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ajout = true;
        int x = e.getX();
        int y = e.getY();
        String ville = "_"; //nom de ville par defaut


        if(x< WINDOWWIDTH-WINDOWWIDTH/4 && e.getClickCount() == 1){
            boolean select = false;
            for(Sommet sommet : this.graphe.getSommets()){
                if(x<=sommet.getPositionX()+this.grapheView.getRayonSommet() &&x>=sommet.getPositionX()
                        && y<=sommet.getPositionY()+this.grapheView.getRayonSommet() && y>=sommet.getPositionY()){
                    this.grapheView.setSommetSelect(sommet);
                    SommetView sommetView = new SommetView(sommet, this.maincontrol); //affiche le zoom du sommet quand on clique sur les bonnes coordonées
                    select = true;
                }
            }

            if (select == false) {
                for (Arc arc : this.graphe.getArcs()) {
                    if (arc.isClicked(x, y)) {
                        this.grapheView.setArcSelect(arc);
                        this.arcView = new ArcView(arc, maincontrol, graphe); // Créez une nouvelle instance de ArcView
                        this.arcView.setVisible(true); // Affichez la vue ArcView
                        break;
                    }
                }
            }
        }


        if (x< WINDOWWIDTH-WINDOWWIDTH/4 && e.getClickCount() == 2 ){
            //Affichage des attribut d'un sommet

            for(Sommet sommet : this.graphe.getSommets()){
                if(x<=sommet.getPositionX()+this.grapheView.getRayonSommet() &&x>=sommet.getPositionX()
                        && y<=sommet.getPositionY()+this.grapheView.getRayonSommet() && y>=sommet.getPositionY()){
                    ajout = false;
                }
            }
            if (ajout) {
                int maxId = -1;
                boolean sommetExiste = false;
                for (Sommet sommet : this.graphe.getSommets()) {
                    if (sommet.getPositionX() == x && sommet.getPositionY() == y) {
                        sommetExiste = true;
                        break;
                    }
                    if (sommet.getIdentifiant() > maxId) {
                        maxId = sommet.getIdentifiant();
                    }
                }

                if (!sommetExiste) {
                    maxId++;
                    Sommet nouveauSommet = new Sommet(x, y, maxId, ville);//creation du nouveau sommet
                    this.graphe.getSommets().add(nouveauSommet);
                    grapheView.updateSommetComboBoxes(); // Mise à jour de la liste déroulante des sommets dans la vue

                    Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
                    graphe.setFichier(fichier);
                    graphe.getFichier().getSommets().clear(); // Effacer les sommets existants dans la liste du fichier
                    graphe.getFichier().getSommets().addAll(this.graphe.getSommets()); // Ajouter les sommets de this.graphe.getSommets() au fichier
                    graphe.getFichier().mettreAJourFichierCSV();
                }
        }
            ajout = true;
        }

        this.grapheView.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
