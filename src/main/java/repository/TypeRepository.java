package repository;

import database.Database;
import model.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TypeRepository {
    public boolean ajout(Type type) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO type (nom, code_couleur) VALUES (?, ?)"
        );
        requetePrepareInsert.setString(1, type.getNom());
        requetePrepareInsert.setString(2, type.getCodeCouleur());
        requetePrepareInsert.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom FROM type WHERE nom = ?"
        );
        reqPrepareSelect.setString(1, type.getNom());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Type> getTypes() throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM type"
        );
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
            ArrayList<Type> resultats = new ArrayList<Type>();
            while (resultatRequete.next()){
                resultats.add(new Type(resultatRequete.getInt(1), resultatRequete.getString(2), resultatRequete.getString(3)));
            }
            return resultats;
    }

    public Type getTypeByNom(String nom) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom FROM type WHERE nom = ?"
        );
        reqPrepareSelect.setString(1, nom);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            Type type = new Type(resultatRequete.getString(1), resultatRequete.getString(2));
            return type;
        } else {
            return null;
        }
    }
}
