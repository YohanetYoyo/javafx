package appli.type;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ColorPicker;
import model.Type;
import repository.TypeRepository;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterTypeController {
    @FXML
    private Label erreur;
    @FXML
    private TextField nomField;
    @FXML
    private ColorPicker couleurField;

    @FXML
    protected void ajout() throws SQLException {
        String nom = this.nomField.getText();
        String couleur = this.couleurField.getValue().toString();
        System.out.println(this.couleurField.getValue().toString());
        if (nom.isEmpty() || couleur.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Type type = new Type(
                    nom,
                    "#"+couleur.substring(2, 8)
            );
            TypeRepository typeRepository = new TypeRepository();
            Type nomCheck = typeRepository.getTypeByNom(nom);
            if (nomCheck != null) {
                this.erreur.setText("Le type a déjà été créé !");
                this.erreur.setVisible(true);
            } else {
                this.erreur.setVisible(false);
                boolean check = typeRepository.ajout(type);
                if (check == true){
                    this.erreur.setText("Type créé !");
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
        StartApplication.changeScene("type/lesTypesView.fxml");
    }
}
