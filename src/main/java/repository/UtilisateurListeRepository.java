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

    public boolean suppression(int refListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareDelete = base.getConnection().prepareStatement(
                "DELETE FROM utilisateur_liste WHERE ref_liste = ?"
        );
        requetePrepareDelete.setInt(1, refListe);
        requetePrepareDelete.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM utilisateur_liste WHERE ref_liste = ?");
        reqPrepareSelect.setInt(1, refListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (!resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean retirerMembre(UtilisateurListe utilisateurListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareDelete = base.getConnection().prepareStatement(
                "DELETE FROM utilisateur_liste WHERE ref_utilisateur = ? AND ref_liste = ?"
        );
        requetePrepareDelete.setInt(1, utilisateurListe.getRefUtilisateur());
        requetePrepareDelete.setInt(2, utilisateurListe.getRefListe());
        requetePrepareDelete.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM utilisateur_liste WHERE ref_utilisateur = ? AND ref_liste = ?");
        reqPrepareSelect.setInt(1, utilisateurListe.getRefUtilisateur());
        reqPrepareSelect.setInt(2, utilisateurListe.getRefListe());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (!resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean estMembre(int refUtilisateur, int refListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareSelect = base.getConnection().prepareStatement(
                "SELECT COUNT(*) FROM utilisateur_liste WHERE ref_utilisateur = ? AND ref_liste = ?"
        );
        requetePrepareSelect.setInt(1 ,refUtilisateur);
        requetePrepareSelect.setInt(2, refListe);
        ResultSet resultatRequete = requetePrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return resultatRequete.getInt(1) > 0;
        }
        return false;
    }

    public boolean estProprietaire(int refUtilisateur, int refListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareSelect = base.getConnection().prepareStatement(
                "SELECT u.id_utilisateur, u.nom, u.prenom FROM utilisateur_liste as ul INNER JOIN utilisateur as u ON ul.ref_utilisateur = u.id_utilisateur WHERE ul.ref_liste = ? LIMIT 1"
        );
        requetePrepareSelect.setInt(1 ,refListe);
        ResultSet resultatRequete = requetePrepareSelect.executeQuery();
        if (resultatRequete.next()){
            if (refUtilisateur == resultatRequete.getInt(1)){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public ArrayList<Utilisateur> getUtilisateursNotInListe(int refListe) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT id_utilisateur, nom, prenom FROM utilisateur WHERE id_utilisateur NOT IN (SELECT ref_utilisateur FROM utilisateur_liste WHERE ref_liste = ?);"
        );
        reqPrepareSelect.setInt(1, refListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        ArrayList<Utilisateur> resultats = new ArrayList<Utilisateur>();
        while (resultatRequete.next()) {
            resultats.add(new Utilisateur(resultatRequete.getInt(1), resultatRequete.getString(2), resultatRequete.getString(3)));
        }
        return resultats;
    }

    public ArrayList<Utilisateur> getUtilisateursByRefListe(int refListe) throws SQLException{
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT u.id_utilisateur, u.nom, u.prenom FROM utilisateur_liste as ul INNER JOIN utilisateur as u ON ul.ref_utilisateur = u.id_utilisateur WHERE ul.ref_liste = ?"
        );
        reqPrepareSelect.setInt(1, refListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        ArrayList<Utilisateur> resultats = new ArrayList<Utilisateur>();
        while (resultatRequete.next()) {
            resultats.add(new Utilisateur(resultatRequete.getInt(1), resultatRequete.getString(2), resultatRequete.getString(3)));
        }
        return resultats;
    }
}
