package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import model.Utilisateur;
import model.UtilisateurListe;

public class UtilisateurListeRepository {
    public boolean ajout(UtilisateurListe utilisateurListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO utilisateur_liste VALUES (?, ?)"
        );
        requetePrepareInsert.setInt(1, utilisateurListe.getRefUtilisateur());
        requetePrepareInsert.setInt(2, utilisateurListe.getRefListe());
        requetePrepareInsert.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM utilisateur_liste WHERE ref_utilisateur = ? AND ref_liste = ?");
        reqPrepareSelect.setInt(1, utilisateurListe.getRefUtilisateur());
        reqPrepareSelect.setInt(2, utilisateurListe.getRefListe());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<UtilisateurListe> getUtilisateurListes() throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT ref_utilisateur, ref_liste FROM utilisateur_liste;"
        );
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        ArrayList<UtilisateurListe> resultats = new ArrayList<UtilisateurListe>();
        while (resultatRequete.next()) {
            resultats.add(new UtilisateurListe(resultatRequete.getInt(1), resultatRequete.getInt(2)));
        }
        return resultats;
    }

    public Utilisateur getUtilisateurByRefListe(int refListe) throws SQLException{
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT u.id_utilisateur, u.nom, u.prenom FROM utilisateur_liste as ul INNER JOIN utilisateur as u ON ul.ref_utilisateur = u.id_utilisateur WHERE ul.ref_liste = ?"
        );
        reqPrepareSelect.setInt(1, refListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            Utilisateur utilisateur = new Utilisateur(resultatRequete.getInt(1), resultatRequete.getString(2), resultatRequete.getString(3));
            return utilisateur;
        } else {
            return null;
        }
    }
}
