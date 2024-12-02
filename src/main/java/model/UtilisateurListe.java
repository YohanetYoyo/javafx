package model;

public class UtilisateurListe {
    private int refUtilisateur;
    private int refListe;

    public UtilisateurListe(int refUtilisateur, int refListe) {
        this.refUtilisateur = refUtilisateur;
        this.refListe = refListe;
    }

    public String toString(){
        return "Ref_utilisateur : "+this.refUtilisateur+"\n"+
                "Ref_liste : "+this.refListe+"\n";
    }

    public int getRefUtilisateur() {
        return refUtilisateur;
    }

    public void setRefUtilisateur(int refUtilisateur) {
        this.refUtilisateur = refUtilisateur;
    }

    public int getRefListe() {
        return refListe;
    }

    public void setRefListe(int refListe) {
        this.refListe = refListe;
    }
}
