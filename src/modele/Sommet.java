package modele;

import java.util.Objects;

public class Sommet {
    private int positionX;
    private int positionY;
    private int identifiant;

    private String ville;

    public Sommet(int positionX, int positionY, int identifiant, String ville) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.identifiant = identifiant;
        this.ville= ville;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sommet sommet = (Sommet) o;
        return positionX == sommet.positionX && positionY == sommet.positionY && identifiant == sommet.identifiant;
    }

    @Override
    public int hashCode() {
        return Objects.hash(positionX, positionY, identifiant);
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public int getIdentifiant() {
        return identifiant;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public void setIdentifiant(int identifiant) {
        this.identifiant = identifiant;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}