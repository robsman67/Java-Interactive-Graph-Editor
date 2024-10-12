package modele;

import java.util.ArrayList;
import java.util.List;

public class Graphe {
    private List<Sommet> sommets;
    private List<Arc> arcs;
    private Fichier fichier;

    public Graphe(){
        fichier = new Fichier("src/Graphe.csv");

        this.sommets = new ArrayList<>();
        this.arcs = new ArrayList<>();

        this.sommets = fichier.getSommets();
        this.arcs = fichier.getArcs();

        for(Arc arc : arcs){
            for(Sommet sommet : sommets){
                if(sommet.getIdentifiant()== arc.getSommetId1()){
                    arc.setS1(sommet);
                }
                if(sommet.getIdentifiant()== arc.getSommetId2()){
                    arc.setS2(sommet);
                }
            }
        }
    }

    public List<Sommet> getSommets() {
        return sommets;
    } //creation des liste contenant tous les differents sommet et arc

    public List<Arc> getArcs (){return arcs;}

    public Arc getArcUnique(int sommetId1, int sommetId2) {
        for (Arc arc : arcs) {
            if (arc.getSommetId1() == sommetId1 && arc.getSommetId2() == sommetId2) {
                return arc;
            }
        }
        return null;
    }

    public Fichier getFichier() {
        return fichier;
    }

    public void setFichier(Fichier fichier) {
        this.fichier = fichier;
    }
}
