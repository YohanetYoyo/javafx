package appli.tache;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Liste;
import model.Tache;
import model.Type;
import repository.TacheRepository;
import repository.TypeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditerTacheController implements Initializable {

    private Liste listeSel;
    private Tache tacheSel;

    @FXML
    private Label erreur;
    @FXML
    private Label idListe;
    @FXML
    private TextField nomField;
    @FXML
    private ChoiceBox<String> etatField;
    @FXML
    private ChoiceBox<String> typeField;

    public EditerTacheController(Liste listeSel, Tache tacheSel) {
        this.listeSel = listeSel;
        this.tacheSel = tacheSel;
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        this.idListe.setText("Tâche : " + this.tacheSel.getNom() + " pour la liste " + this.listeSel.getNom());
        this.nomField.setText(this.tacheSel.getNom());
        this.etatField.getItems().addAll("À faire", "En cours", "Terminée");
        String etat;
        switch (this.tacheSel.getEtat()) {
            case 1:
                etat = "À faire";
                break;
            case 2:
                etat = "En cours";
                break;
            case 3:
                etat = "Terminée";
                break;
            default:
                etat = "Aucun état";
                break;
        }
        this.etatField.setValue(etat);
        TypeRepository typeRepository = new TypeRepository();
        ArrayList<Type> types;
        try {
             types = typeRepository.getTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        for (Type type : types) {
            this.typeField.getItems().add(type.getNom());
        }
    }

    @FXML
    protected void editer() throws SQLException {
        int idListe = this.listeSel.getIdListe();
        int idTache = this.tacheSel.getIdTache();
        String nom = this.nomField.getText();
        int etat = 0;
        switch (this.etatField.getSelectionModel().getSelectedItem()) {
            case "À faire":
                etat = 1;
                break;
            case "En cours":
                etat = 2;
                break;
            case "Terminée":
                etat = 3;
                break;
        }
        int type = new TypeRepository().getIdTypeByNom(this.typeField.getValue());
        if (nom.isEmpty() || this.etatField.getItems().isEmpty() || this.typeField.getItems().isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Tache tache = new Tache(
                    idTache, nom, etat, idListe, type
            );
            TacheRepository tacheRepository = new TacheRepository();
            Tache nomCheck = tacheRepository.getTacheByNom(nom, idListe);
            if (nomCheck == null || nomCheck.getNom().equals(nom)) {
                this.erreur.setVisible(false);
                boolean check = tacheRepository.editer(tache);
                if (check == true) {
                    this.erreur.setText("Tâche éditée !");
                    this.erreur.setVisible(true);
                    this.nomField.clear();
                } else {
                    this.erreur.setText("Erreur lors de l'édition.");
                    this.erreur.setVisible(true);
                }
            } else {
                this.erreur.setText("Le nom a déjà été pris !");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("tache/tachesView", new TachesController(this.listeSel));
    }
}
