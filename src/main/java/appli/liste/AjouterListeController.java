package appli.liste;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Liste;
import repository.ListeRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterListeController {
    @FXML
    private Label erreur;
    @FXML
    private TextField nomField;

    @FXML
    protected void ajout() throws SQLException {
        String nom = this.nomField.getText();
        if (nom.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Liste liste = new Liste(
                    nom
            );
            ListeRepository listeRepository = new ListeRepository();
            Liste nomCheck = listeRepository.getListeByNom(nom);
            if (nomCheck != null) {
                this.erreur.setText("La liste a déjà été créée !");
                this.erreur.setVisible(true);
            } else {
                this.erreur.setVisible(false);
                boolean check = listeRepository.ajout(liste);
                if (check == true){
                    this.erreur.setText("Liste créée !");
                    this.erreur.setVisible(true);
                    this.nomField.clear();
                } else {
                    this.erreur.setText("Erreur lors de l'ajout.");
                    this.erreur.setVisible(true);
                }
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }
}
