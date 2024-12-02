package repository;

import java.sql.*;
import java.util.ArrayList;

import database.Database;
import model.UtilisateurListe;

public class UtilisateurListeRepository {
    public void ajout(UtilisateurListe utilisateurListe) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO utilisateur_liste VALUES ?, ?"
        );
        requetePrepareInsert.setInt(1, utilisateurListe.getRefUtilisateur());
        requetePrepareInsert.setInt(2, utilisateurListe.getRefListe());
        requetePrepareInsert.executeUpdate();
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
}
