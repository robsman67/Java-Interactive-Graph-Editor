package modele;

import java.util.List;

public class Arc {
    private int sommetId1;
    private int sommetId2;
    private Sommet s1;
    private Sommet s2;
    private String rue;

    private double distance;

    public Arc(int sommet1, int sommet2, String rue) {
        this.sommetId1 = sommet1;
        this.sommetId2 = sommet2;
        this.rue = rue;
    }

    public boolean isClicked(int x, int y) {
        // Vérifiez si les coordonnées (x, y) se trouvent à proximité de l'arc
        if (s1 == null || s2 == null) {
            System.out.println(false);
            return false;
        }
        int threshold = 5; // Tolérance pour le clic
        int x1 = s1.getPositionX()+10;
        int y1 = s1.getPositionY()+11;
        int x2 = s2.getPositionX()+10;
        int y2 = s2.getPositionY()+11;

        double distance = Math.abs((y2 - y1) * x - (x2 - x1) * y + x2 * y1 - y2 * x1) / Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2));

        return distance <= threshold ;
    }

    public void setS1(Sommet s1) {

        this.s1 = s1;
        this.sommetId1 = this.s1.getIdentifiant();
    }

    public void setS2(Sommet s2) {

        this.s2 = s2;
        this.sommetId2 = this.s2.getIdentifiant();
    }

    public double calculDistance(){
        double distance = Math.sqrt(Math.pow(s2.getPositionX() - s1.getPositionX(), 2) + Math.pow(s2.getPositionY() - s1.getPositionY(), 2));
        return distance;
    }

    public double getDistance() {
        return distance;
    }
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public Sommet getS1() {
        return s1;
    }

    public Sommet getS2() {
        return s2;
    }

    public int getSommetId1() {
        return sommetId1;
    }

    public int getSommetId2() {
        return sommetId2;
    }

    public void setSommetId1(int sommetId1) {
        this.sommetId1 = sommetId1;
    }

    public void setSommetId2(int sommetId2) {
        this.sommetId2 = sommetId2;
    }

}