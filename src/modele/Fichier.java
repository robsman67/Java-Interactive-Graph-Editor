package modele;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.BufferedWriter;



public class Fichier {
    private List<Sommet> sommets;
    private List<Arc> arcs;

    private String fichierCSV;


    public Fichier(String fichierCSV) {
        sommets = new ArrayList<>();
        arcs = new ArrayList<>();
        this.fichierCSV = fichierCSV;

        String csvSplitBy = ",";
        String line;

        try (BufferedReader br = new BufferedReader(new FileReader(fichierCSV))) { // lire le fichier CSV
            while ((line = br.readLine()) != null) {
                if (line.startsWith("Sommet")) {
                    String[] sommetData = line.substring(line.indexOf("(") + 1, line.indexOf(")")).split(csvSplitBy);
                    int positionX = Integer.parseInt(sommetData[0].trim());
                    int positionY = Integer.parseInt(sommetData[1].trim());
                    int identifiant = Integer.parseInt(sommetData[2].trim());
                    String ville = sommetData[3].trim();
                    Sommet sommet = new Sommet(positionX, positionY, identifiant, ville);
                    sommets.add(sommet);
                } else if (line.startsWith("Arc")) {
                    String[] arcData = line.substring(line.indexOf("(") + 1, line.indexOf(")")).split(csvSplitBy);
                    int sommet1 = Integer.parseInt(arcData[0].trim());
                    int sommet2 = Integer.parseInt(arcData[1].trim());
                    String rue = arcData[2].trim();
                    //Arc arc = new Arc(sommet1, sommet2);
                    Sommet s1 = null;
                    Sommet s2 = null;
                    for(Sommet sommet : sommets){
                        if(sommet1 == sommet.getIdentifiant()){
                            s1 = sommet;
                        }
                        if(sommet2 == sommet.getIdentifiant()){
                            s2 = sommet;
                        }
                    }
                    if(s1 != null && s2 != null){
                        Arc nouvelArc = new Arc(sommet1, sommet2, rue);
                        nouvelArc.setS1(s1);
                        nouvelArc.setS2(s2);
                        arcs.add(nouvelArc);
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void mettreAJourFichierCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichierCSV, false))) {
            // Ajouter les lignes nécessaires au début du fichier
            writer.write("{\\rtf1\\ansi\\ansicpg1252\\cocoartf2709");
            writer.newLine();
            writer.write("\\cocoatextscaling0\\cocoaplatform0{\\fonttbl\\f0\\fswiss\\fcharset0 Helvetica;}");
            writer.newLine();
            writer.write("{\\colortbl;\\red255\\green255\\blue255;\\red0\\green0\\blue0;\\red0\\green0\\blue0;}");
            writer.newLine();
            writer.write("{\\*\\expandedcolortbl;;\\cssrgb\\c0\\c0\\c0;\\csgray\\c0\\c0;}");
            writer.newLine();
            writer.write("\\paperw11900\\paperh16840\\margl1440\\margr1440\\vieww11520\\viewh8400\\viewkind0");
            writer.newLine();
            writer.write("\\deftab720");
            writer.newLine();
            writer.write("\\pard\\pardeftab720\\partightenfactor0");
            writer.newLine();
            writer.write("\\f0\\fs28 \\cf2 \\cb3 \\expnd0\\expndtw0\\kerning0");
            writer.newLine();
            writer.newLine();

            // Écrire les données des sommets
            for (int i = 0; i < sommets.size(); i++) {
                Sommet sommet = sommets.get(i);
                String sommetData = "Sommet(" + sommet.getPositionX() + "," + sommet.getPositionY() + "," + sommet.getIdentifiant() + "," + sommet.getVille() +")";
                writer.write(sommetData);
                writer.newLine();
            }

            // Écrire les données des arcs
            for (int i = 0; i < arcs.size(); i++) {
                Arc arc = arcs.get(i);
                String arcData = "Arc(" + arc.getSommetId1() + "," + arc.getSommetId2() +"," + arc.getRue()+")";
                writer.write(arcData);
                writer.newLine();
            }

            // Ajouter la ligne de fermeture du fichier
            writer.write("}");
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String getFichierCSV() {
        return fichierCSV;
    }

    public List<Sommet> getSommets() {
        return sommets;
    }

    public List<Arc> getArcs() {
        return arcs;
    }
}
