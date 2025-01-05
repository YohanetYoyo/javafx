package repository;

import database.Database;
import model.Liste;
import model.Tache;
import model.Type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TacheRepository {
    public boolean ajout(Tache tache) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareInsert = base.getConnection().prepareStatement(
                "INSERT INTO tache (nom, etat, ref_liste, ref_type) VALUES (?, ?, ?, ?)"
        );
        requetePrepareInsert.setString(1, tache.getNom());
        requetePrepareInsert.setInt(2, tache.getEtat());
        requetePrepareInsert.setInt(3, tache.getRefListe());
        requetePrepareInsert.setInt(4, tache.getRefType());
        requetePrepareInsert.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom FROM tache WHERE nom = ? AND ref_liste = ?");
        reqPrepareSelect.setString(1, tache.getNom());
        reqPrepareSelect.setInt(2, tache.getRefListe());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean editer(Tache tache) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareEdit = base.getConnection().prepareStatement(
                "UPDATE tache SET nom = ?, etat = ?, ref_type = ? WHERE id_tache = ?"
        );
        requetePrepareEdit.setString(1, tache.getNom());
        requetePrepareEdit.setInt(2, tache.getEtat());
        requetePrepareEdit.setInt(3, tache.getRefType());
        requetePrepareEdit.setInt(4, tache.getIdTache());
        requetePrepareEdit.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT nom FROM tache WHERE nom = ? AND ref_liste = ?"
        );
        reqPrepareSelect.setString(1, tache.getNom());
        reqPrepareSelect.setInt(2, tache.getRefListe());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean supprimer(Tache tache) throws SQLException {
        Database base = new Database();
        PreparedStatement requetePrepareDelete = base.getConnection().prepareStatement(
                "DELETE FROM tache WHERE id_tache = ?"
        );
        requetePrepareDelete.setInt(1, tache.getIdTache());
        requetePrepareDelete.executeUpdate();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement("SELECT * FROM tache WHERE id_tache = ?");
        reqPrepareSelect.setInt(1, tache.getIdTache());
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (!resultatRequete.next()) {
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<Tache> getTachesByIdListe(int idListe) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT * FROM tache WHERE ref_liste = ?"
        );
        reqPrepareSelect.setInt(1, idListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        ArrayList<Tache> resultats = new ArrayList<Tache>();
        while (resultatRequete.next()) {
            resultats.add(new Tache(
                    resultatRequete.getInt(1),
                    resultatRequete.getString(2),
                    resultatRequete.getInt(3),
                    resultatRequete.getInt(4),
                    resultatRequete.getInt(5)
            ));
        }
        return resultats;
    }

    public Tache getTacheByNom(String nom, int idListe) throws SQLException {
        Database base = new Database();
        PreparedStatement reqPrepareSelect = base.getConnection().prepareStatement(
                "SELECT * FROM tache WHERE nom = ? AND ref_liste = ?"
        );
        reqPrepareSelect.setString(1, nom);
        reqPrepareSelect.setInt(2, idListe);
        ResultSet resultatRequete = reqPrepareSelect.executeQuery();
        if (resultatRequete.next()) {
            Tache tache = new Tache(resultatRequete.getInt(1), resultatRequete.getString(2), resultatRequete.getInt(3), resultatRequete.getInt(4), resultatRequete.getInt(5));
            return tache;
        } else {
            return null;
        }
    }
}
