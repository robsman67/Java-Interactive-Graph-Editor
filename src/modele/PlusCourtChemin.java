package modele;
import java.util.List;
import java.util.ArrayList;

public class PlusCourtChemin {

    private Graphe graphe;
    private int id_sommet_depart;
    private int id_sommet_arrive;
    private List<String> chemin;

    private double distanceTotal;

    public PlusCourtChemin(Graphe graphe , String ville_depart , String ville_arrive, double distanceTotal) {
        this.graphe = graphe;
        this.calculDistanceArc();       // calcul de la distance de tous les arcs existant
        this.id_sommet_depart = trouverIdParVille(ville_depart);
        this.id_sommet_arrive = trouverIdParVille(ville_arrive);
        chemin = new ArrayList<>();

        // Appel la fonction plusCourtChemin pour calculer le chemin le plus court
        List<Arc> cheminPlusCourt = calculerChemin();

        // Affichage du chemin
        System.out.println("Chemin le plus court :");
        if(cheminPlusCourt != null){
            double distance = 0;
            for (Arc arc : cheminPlusCourt) {
                List<Sommet> s = this.graphe.getSommets();
                Sommet s1 = s.get(arc.getSommetId1());
                String ville_s1 = s1.getVille();
                Sommet s2 = s.get(arc.getSommetId2());
                String ville_s2 = s2.getVille();
                System.out.println("arc (" + arc.getSommetId1() + "," + arc.getSommetId2() + ") , de " + ville_s1 + " à " + ville_s2);
                this.chemin.add("Du lieu "+arc.getS1().getVille()+" à "+arc.getS2().getVille()+", par la rue :"+arc.getRue()+"");
                distance += arc.getDistance();
                //System.out.println(arc.getRue());
            }

            this.distanceTotal = distance;
            System.out.println("D = " + distanceTotal);
        }
        else{
            System.out.println(" Le chemin est inaccessible");
            this.chemin.add(" Le chemin est inaccessible");
        }
    }

    public double getDistanceTotal(){
        return distanceTotal;
    }
    public void calculDistanceArc() {       // calcul de la distance de chaque arc
        for (Arc arc : graphe.getArcs()) {
            arc.setDistance(arc.calculDistance());
            //System.out.println(arc.getDistance());    // affichage distance
        }
    }

    public int trouverIdParVille(String ville) {
        for (Sommet sommet : graphe.getSommets()) {
            if (sommet.getVille().equals(ville)) {
                //System.out.println("DEPART ARRIVE   " + sommet.getIdentifiant());
                return sommet.getIdentifiant();
            }
        }
        //System.out.println(" trouverIdParVille n'a pas trouvé l'id du sommet pour ce nom de ville ");
        return -1; // Retourne -1 si la ville n'est pas trouvée
    }

    public List<Arc> calculerChemin() {
        // Initialisation des distances pour chaque sommets à l'infini, sauf le sommet de départ à 0
        int nbSommets = graphe.getSommets().size();
        double[] distances = new double[nbSommets];
        for (int i = 0; i < nbSommets; i++) {
            distances[i] = Double.MAX_VALUE;
        }
        distances[id_sommet_depart] = 0;

        // on initialise la liste des sommets visités
        boolean[] visites = new boolean[nbSommets];

        // Boucle principale de l'algorithme de Dijkstra
        for (int i = 0; i < nbSommets - 1; i++) {
            // Recherche du sommet non visité avec la plus petite distance actuelle
            int sommetCourant = trouverSommetNonVisite(distances, visites);

            if (sommetCourant == -1) {
                // Tous les sommets restants ont une distance infinie donc le chemin est inaccessible
                break;
            }
            // on marque le sommet courant comme visité
            visites[sommetCourant] = true;

            // on met à jour les distances des sommets adjacents non visités
            mettreAJourDistances(sommetCourant, distances, visites);
        }

        // on vérifie si le sommet d'arrivée a une distance infinie dans ce cas le chemin est inaccessible
        if (distances[id_sommet_arrive] == Double.MAX_VALUE) {
            //System.out.println("Chemin inaccessible.");
            return null; // on renvoit une valeur indiquant l'inaccessibilité du chemin
        }

        // Pour finir on construit le chemin à partir des distances calculées et misent à jour lorsque nous sommes à la fin de l'algorithme
        List<Arc> chemin = construireChemin(distances);

        return chemin;
    }

    private int trouverSommetNonVisite(double[] distances, boolean[] visites) {
        double minDistance = Double.MAX_VALUE;
        int sommetMin = -1;

        // on parcourt les sommets non visités et on garde celui avec la plus courte distance et qui n'est pas déjà visité
        for (int i = 0; i < distances.length; i++) {
            if (!visites[i] && distances[i] < minDistance) {
                minDistance = distances[i];
                sommetMin = i;
            }
        }

        if (sommetMin != -1) {
            return sommetMin;
        } else {
            return -1; // Retourne -1 si tous les sommets ont été visités
        }


    }

    private void mettreAJourDistances(int sommetCourant, double[] distances, boolean[] visites) {

        List<Arc> arcsSortants = new ArrayList<>();

        // on construit la liste des arc dont le départ est le sommet courant,
        // le sommet courant est le sommet choisi précèdement qui n'est pas encore visité
        // et qui a la distance la plus courte actuelle
        for (Arc arc : graphe.getArcs()) {
            if (arc.getSommetId1() == sommetCourant) {
                arcsSortants.add(arc);
            }
        }
        // on parcourt notre liste créée juste avant et qui représente les sommets accessibles depuis le sommet courant
        // pour mettre a jour notre tableau distance
        for (Arc arc : arcsSortants) {
            int sommetDestination = arc.getSommetId2();
            double distanceArc = arc.getDistance();

            if (!visites[sommetDestination ] && distances[sommetCourant ] != Double.MAX_VALUE &&
                    distances[sommetCourant ] + distanceArc < distances[sommetDestination ]) {
                distances[sommetDestination ] = distances[sommetCourant ] + distanceArc;
            }
        }
    }

    private List<Arc> construireChemin(double[] distances) {
        List<Arc> chemin = new ArrayList<>();
        int sommetActuel = id_sommet_arrive;

        // on construit le chemin le plus court en part du sommet d'arrivé
        while (sommetActuel != id_sommet_depart) {
            int sommetPrecedent = trouverSommetPrecedent(sommetActuel, distances);
            Arc arc = graphe.getArcUnique(sommetPrecedent, sommetActuel);
            chemin.add(0, arc);
            sommetActuel = sommetPrecedent;
        }

        return chemin;
    }

    private int trouverSommetPrecedent(int sommetActuel, double[] distances) {

        List<Arc> arcsEntrants = new ArrayList<>();

        // construction de la liste des arc qui ont pour destination sommetActuel passé en paramètre
        for (Arc arc : graphe.getArcs()) {
            if (arc.getSommetId2() == sommetActuel) {
                arcsEntrants.add(arc);
            }
        }

        int sommetPrecedent = -1;
        double minDistance = Double.MAX_VALUE;

        // on parcout notre liste précédement créée pour choisir le sommet qui initie l'arc
        // qui a pour destination sommetActuel et qui a le plus court chemin
        // sommetPrecedent est le sommet qui initie l'arc jusqu'à sommetActuel qui a le plus court chemin pour arriver
        // a sommetActuel

        for (Arc arc : arcsEntrants) {
            int sommetSource = arc.getSommetId1();
            double distanceArc = arc.getDistance();

            if (distances[sommetSource ] != Double.MAX_VALUE && distances[sommetSource ] + distanceArc < minDistance) {
                minDistance = distances[sommetSource] + distanceArc;
                sommetPrecedent = sommetSource;
            }
        }

        return sommetPrecedent;
    }

    public List<String> getChemin() {
        return chemin;
    }
}
