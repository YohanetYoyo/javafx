package repository;

import java.sql.*;
import database.Database;
import model.Utilisateur;

public class UtilisateurRepository {
    public boolean inscription(Utilisateur utilisateur) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO utilisateur (nom, prenom, email, mdp) VALUES (?,?,?,?)"
        );
        requetePrepareInsert.setString(1, utilisateur.getNom());
        requetePrepareInsert.setString(2, utilisateur.getPrenom());
        requetePrepareInsert.setString(3,utilisateur.getEmail());
        requetePrepareInsert.setString(4, utilisateur.getMdp());
        requetePrepareInsert.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?"
        );
        reqPrepareSelect.setString(1, utilisateur.getEmail());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public Utilisateur getUtilisateurByEmail(String email) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM utilisateur WHERE email = ?"
        );
        reqPrepareSelect.setString(1, email);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            Utilisateur utilisateur = new Utilisateur(resultatRequete.getString(1), resultatRequete.getString(2), resultatRequete.getString(3), resultatRequete.getString(4));
            return utilisateur;
        } else {
            return null;
        }
    }
}
