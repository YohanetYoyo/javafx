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

public class AjouterTacheController implements Initializable {

    private Liste listeSel;

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

    public AjouterTacheController(Liste listeSel) {
        this.listeSel = listeSel;
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle) {
        this.idListe.setText("Liste : " + this.listeSel.getNom());
        this.etatField.getItems().addAll("À faire", "En cours", "Terminée");
        this.etatField.setValue("À faire");
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
    protected void ajouter() throws SQLException {
        int id = this.listeSel.getIdListe();
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
                    nom, etat, id, type
            );
            TacheRepository tacheRepository = new TacheRepository();
            Tache nomCheck = tacheRepository.getTacheByNom(nom, id);
            if (nomCheck == null) {
                this.erreur.setVisible(false);
                boolean check = tacheRepository.ajout(tache);
                if (check == true) {
                    this.erreur.setText("Tâche ajoutée !");
                    this.erreur.setVisible(true);
                    this.nomField.clear();
                } else {
                    this.erreur.setText("Erreur lors de l'ajout.");
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
