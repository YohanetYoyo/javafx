package model;

public class Tache {
    private int idTache;
    private String nom;
    private int etat;
    private int refListe;
    private int refType;

    public Tache(int idTache, String nom, int etat, int refListe, int refType){
        this.idTache = idTache;
        this.nom = nom;
        this.etat = etat;
        this.refListe = refListe;
        this.refType = refType;
    }

    public Tache(String nom, int etat, int refListe, int refType) {
        this.nom = nom;
        this.etat = etat;
        this.refListe = refListe;
        this.refType = refType;
    }

    @Override
    public String toString() {
        return "{id_tache=" + idTache +
                ", nom='" + nom + '\'' +
                ", etat=" + etat +
                ", refListe=" + refListe +
                ", refType=" + refType +
                '}';
    }

    public int getIdTache() {
        return idTache;
    }

    public void setIdTache(int idTache) {
        this.idTache = idTache;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getRefListe() {
        return refListe;
    }

    public void setRefListe(int refListe) {
        this.refListe = refListe;
    }

    public int getRefType() {
        return refType;
    }

    public void setRefType(int refType) {
        this.refType = refType;
    }
}
