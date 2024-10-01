package repository;

import java.sql.*;
import database.Database;

public class UtilisateurRepository {
    public boolean inscription() throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO Utilisateur (nom, prenom, email, mdp) VALUES (?,?,?,?)"
        );
        requetePrepareInsert.setString(1, "");
        requetePrepareInsert.setString(2, "");
        requetePrepareInsert.setString(3,"");
        requetePrepareInsert.setString(4, "");
        requetePrepareInsert.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM Utilisateur WHERE email = ?"
        );
        reqPrepareSelect.setString(1, "");
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.first()){
            return true;
        } else {
            return false;
        }
    }
}
