package repository;

import database.Database;
import model.Liste;
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

    public boolean editer(Type type) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareEdit = base.getConnection().prepareStatement(
                "UPDATE type SET nom = ?, code_couleur = ? WHERE id_type = ?"
        );
        requetePrepareEdit.setString(1, type.getNom());
        requetePrepareEdit.setString(2, type.getCodeCouleur());
        requetePrepareEdit.setInt(3, type.getIdType());
        requetePrepareEdit.executeUpdate();
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

    public boolean supprimer(Type type) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareDelete = base.getConnection().prepareStatement(
                "DELETE FROM type WHERE id_type = ?"
        );
        requetePrepareDelete.setInt(1, type.getIdType());
        requetePrepareDelete.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM type WHERE id_type = ?");
        reqPrepareSelect.setInt(1, type.getIdType());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (!resultatRequete.next()) {
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

    public Type getTypeById(int id) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom, code_couleur FROM type WHERE id_type = ?");
        reqPrepareSelect.setInt(1, id);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()){
            Type type = new Type(resultatRequete.getString(1), resultatRequete.getString(2));
            return type;
        } else {
            return null;
        }
    }

    public Type getTypeByNom(String nom) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom, code_couleur FROM type WHERE nom = ?"
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

    public int getIdTypeByNom(String nom) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT id_type FROM type WHERE nom = ?"
        );
        reqPrepareSelect.setString(1, nom);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            int idType = resultatRequete.getInt(1);
            return idType;
        } else {
            return 0;
        }
    }
}
