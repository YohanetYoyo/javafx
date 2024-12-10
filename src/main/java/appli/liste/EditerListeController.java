package appli.liste;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Liste;
import repository.ListeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditerListeController implements Initializable {

    private Liste listeSel;

    @FXML
    private Label erreur;
    @FXML
    private Label idListe;
    @FXML
    private TextField nomField;

    public EditerListeController(Liste listeSel) {
        this.listeSel = listeSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idListe.setText("Id. liste : " + this.listeSel.getIdListe());
        this.nomField.setText(this.listeSel.getNom());
    }

    @FXML
    protected void editer() throws SQLException {
        int id = this.listeSel.getIdListe();
        String nom = this.nomField.getText();
        if (nom.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Liste liste = new Liste(
                    id, nom
            );
            ListeRepository listeRepository = new ListeRepository();
            Liste nomCheck = listeRepository.getListeByNom(nom);
            if (nomCheck == null) {
                this.erreur.setVisible(false);
                boolean check = listeRepository.editer(liste);
                if (check == true){
                    this.erreur.setText("Liste modifiée !");
                    this.erreur.setVisible(true);
                    this.nomField.clear();
                } else {
                    this.erreur.setText("Erreur lors de la modification.");
                    this.erreur.setVisible(true);
                }
            } else if (nomCheck.getNom().equals(nom)) {
                this.erreur.setVisible(false);
                boolean check = listeRepository.editer(liste);
                if (check == true){
                    this.erreur.setText("Liste modifiée !");
                    this.erreur.setVisible(true);
                    this.nomField.clear();
                } else {
                    this.erreur.setText("Erreur lors de la modification.");
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
        StartApplication.changeScene("accueil/accueilView.fxml");
    }

}
