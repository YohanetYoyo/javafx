package model;

public class Utilisateur {
    private String nom;
    private String prenom;
    private String email;
    private String mdp;

    public Utilisateur(String nom, String prenom, String email, String mdp) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
    }

    public String toString(){
        return "Nom : "+this.nom+"\n"+
                "Prénom : "+this.prenom+"\n"+
                "E-mail : "+this.email+"\n"+
                "Mot de passe : "+this.mdp+"\n";
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}
