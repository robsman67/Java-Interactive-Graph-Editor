package controller;
import modele.*;
import vue.*;
import java.util.List;

import java.awt.event.*;

//différente fonction pour chacun des boutons
public class Boutoncontrol implements ActionListener {
    private SommetView sommet;

    private ArcView arc;

    private Sommet s;
    private GrapheView grapheView;

    private Graphe graphe;


    public Boutoncontrol(SommetView sommet, ArcView a, GrapheView g, Graphe graphe) {
        this.sommet = sommet;
        this.arc = a;
        this.grapheView = g;
        this.graphe= graphe;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Mettre à jour sommet")) {
            mettreAJourSommet();
        } else if (e.getActionCommand().equals("Supprimer sommet")) {
            supprimerSommet();
        } else if(e.getActionCommand().equals("Mettre à jour arc")) {
            mettreAJourArc();
        }else if (e.getActionCommand().equals("Supprimer arc")) {
            supprimerArc();
        } else if(e.getActionCommand().equals("Créer Arc")) {
            creerArc();
        }else if(e.getActionCommand().equals("Chemin le plus court")) {
            algo();
        }
        else if (e.getActionCommand().equals("Supprimer tout")) {
            supprimerTout();
        }
        
        if(sommet !=null){
            this.sommet.getMaincontrol().getGrapheView().repaint();

        } else if(arc !=null) {
            this.arc.getMaincontrol().getGrapheView().repaint();

        }else{
            grapheView.repaint();
        }
    }

    public void algo() { // ce qui se passe quand on clique sur Chemin le plus court
        String ville_depart = (String) grapheView.getSommet1ComboBox().getSelectedItem();
        String ville_arrive = (String) grapheView.getSommet2ComboBox().getSelectedItem();
        System.out.println("ville depart  " + ville_depart);
        System.out.println("ville arrive " + ville_arrive);
        double distance = 0;
        PlusCourtChemin pluscourtchemin = new PlusCourtChemin(this.graphe , ville_depart , ville_arrive, distance);   // calcul du plus court chemin
        grapheView.setPlusCourtChemin(pluscourtchemin);
    }

    private void supprimerTout() {
        grapheView.getGraphe().getArcs().clear();
        grapheView.getGraphe().getSommets().clear();
        grapheView.repaint();
        grapheView.updateComboBoxes();

        Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV()); //Pour mettre à jour le fichier, on va l'utiliser souvent
        graphe.setFichier(fichier);
        graphe.getFichier().getSommets().clear();
        graphe.getFichier().getArcs().clear();
        graphe.getFichier().mettreAJourFichierCSV();
    }

    private void creerArc() {
        String ville1 = (String) grapheView.getSommet1ComboBox().getSelectedItem();
        String ville2 = (String) grapheView.getSommet2ComboBox().getSelectedItem();

        // Vérifier si l'arc n'existe pas déjà
        boolean arcExiste = false;
        for (Arc arc : grapheView.getGraphe().getArcs()) {
            int sommetId1 = trouverIdParVille(ville1);
            int sommetId2 = trouverIdParVille(ville2);
            if (arc.getSommetId1() == sommetId1 && arc.getSommetId2() == sommetId2) {
                arcExiste = true;
                break;
            }
        }

        if (!arcExiste) {
            int sommetId1 = trouverIdParVille(ville1);
            int sommetId2 = trouverIdParVille(ville2);
            Sommet s1 = null;
            Sommet s2 = null;
            if (sommetId1 != -1 && sommetId2 != -1) {
                for(Sommet sommet : graphe.getSommets()){
                    if(sommetId1 == sommet.getIdentifiant()){
                        s1 = sommet;
                    }
                    if(sommetId2 == sommet.getIdentifiant()){
                        s2 = sommet;
                    }
                }
                Arc nouvelArc = new Arc(sommetId1, sommetId2, "_");
                nouvelArc.setS1(s1);
                nouvelArc.setS2(s2);
                grapheView.getGraphe().getArcs().add(nouvelArc);

                Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
                graphe.setFichier(fichier);
                graphe.getFichier().getArcs().clear();
                graphe.getFichier().getArcs().addAll(grapheView.getGraphe().getArcs());
                graphe.getFichier().mettreAJourFichierCSV();
            }
        }
    }

    private int trouverIdParVille(String ville) {
        for (Sommet sommet : grapheView.getGraphe().getSommets()) {
            if (sommet.getVille().equals(ville)) {
                return sommet.getIdentifiant();
            }
        }
        return -1; // Retourne -1 si la ville n'est pas trouvée
    }



    private void supprimerArc() {
        arc.getMaincontrol().getGraphe().getArcs().remove(arc.getArc());
        arc.dispose();
        Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
        graphe.setFichier(fichier);
        graphe.getFichier().getArcs().clear();
        graphe.getFichier().getArcs().addAll(arc.getMaincontrol().getGraphe().getArcs());
        graphe.getFichier().mettreAJourFichierCSV();
    }

    private void mettreAJourArc() {
        String s1 = (String) arc.getS1ComboBox().getSelectedItem();
        String s2 = (String) arc.getS2ComboBox().getSelectedItem();
        String rue = arc.getRueField().getText();

        int s1Id = -1;
        int s2Id = -1;

        for(Sommet sommet : graphe.getSommets()){
            if(sommet.getVille() == s1){
                s1Id = sommet.getIdentifiant();
            }
            if(sommet.getVille() == s2){
                s2Id = sommet.getIdentifiant();
            }
        }
        boolean arcExiste = false;
        int i =0;
        for (Arc arc : graphe.getArcs()) {
            if(s1Id == arc.getSommetId1() && s2Id == arc.getSommetId2()){
                i++;
            }
        }
        if(i>1){
            arcExiste = true;
        }

        Sommet st1 = null;
        Sommet st2 = null;

        if(s1Id != -1 && s2Id != -1 && !arcExiste) {
            arc.getArc().setSommetId1(s1Id);
            arc.getArc().setSommetId2(s2Id);
            for(Sommet sommet : graphe.getSommets()){
                if(arc.getArc().getSommetId1() == sommet.getIdentifiant()){
                    st1 = sommet;
                }
                if(arc.getArc().getSommetId2() == sommet.getIdentifiant()){
                    st2 = sommet;
                }
            }
            arc.getArc().setRue(rue);
            arc.getArc().setS1(st1);
            arc.getArc().setS2(st2);

            Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
            graphe.setFichier(fichier);
            graphe.getFichier().getArcs().clear();
            graphe.getFichier().getArcs().addAll(arc.getMaincontrol().getGraphe().getArcs());
            graphe.getFichier().mettreAJourFichierCSV();
        }
        arc.dispose();
    }

    private void mettreAJourSommet() {
        // Récupérer les nouvelles valeurs des attributs depuis les champs de texte de la fenêtre
        int x = Integer.parseInt(sommet.getXField().getText());
        int y = Integer.parseInt(sommet.getYField().getText());
        String ville = sommet.getVilleField().getText();

        // Mettre à jour les attributs du sommet dans la fenêtre
        sommet.getSommet().setPositionX(x);
        sommet.getSommet().setPositionY(y);
        sommet.getSommet().setVille(ville);

        // Rechercher le sommet dans la liste des sommets du graphe
        List<Sommet> sommets = sommet.getMaincontrol().getGraphe().getSommets();
        for (Sommet s : sommets) {
            if (s.getIdentifiant() == sommet.getSommet().getIdentifiant()) {
                // Mettre à jour les attributs du sommet dans la liste du graphe
                s.setPositionX(x);
                s.setPositionY(y);
                s.setVille(ville);
                break;
            }
        }
        // Mettre à jour l'affichage de la liste déroulante des sommets
        sommet.getMaincontrol().getGrapheView().updateComboBoxes();

        // Mettre à jour le fichier CSV
        Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
        graphe.setFichier(fichier);
        graphe.getFichier().getSommets().clear();
        graphe.getFichier().getSommets().addAll(sommets);
        graphe.getFichier().mettreAJourFichierCSV();

        // Fermer la fenêtre de modification du sommet
        sommet.dispose();
    }

    private void supprimerSommet() {
        for (Arc arc : sommet.getMaincontrol().getGraphe().getArcs()) {
            if (arc.getSommetId1() == sommet.getSommet().getIdentifiant() || arc.getSommetId2() == sommet.getSommet().getIdentifiant()) {
                sommet.getMaincontrol().getGraphe().getArcs().remove(arc);
            }
        }
        sommet.getMaincontrol().getGraphe().getSommets().remove(sommet.getSommet());
        sommet.dispose();

        // Mettre à jour l'affichage de la liste déroulante des sommets
        sommet.getMaincontrol().getGrapheView().updateComboBoxes();

        // Mettre à jour le fichier CSV
        Fichier fichier = new Fichier(graphe.getFichier().getFichierCSV());
        graphe.setFichier(fichier);
        graphe.getFichier().getSommets().clear(); // Supprimer les sommets existants dans la liste du fichier
        graphe.getFichier().getSommets().addAll(sommet.getMaincontrol().getGraphe().getSommets()); // Ajouter les sommets de this.graphe.getSommets() au fichier
        graphe.getFichier().getArcs().clear(); // Supprimer les arcs existants dans la liste du fichier
        for (Arc arc : sommet.getMaincontrol().getGraphe().getArcs()) {
            graphe.getFichier().getArcs().add(arc);
        }
        graphe.getFichier().mettreAJourFichierCSV();
    }

}
