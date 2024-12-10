package appli.type;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.Type;
import repository.TypeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditerTypeController implements Initializable {

    private Type typeSel;

    @FXML
    private Label erreur;
    @FXML
    private Label idListe;
    @FXML
    private TextField nomField;
    @FXML
    private ColorPicker couleurField;

    public EditerTypeController(Type typeSel) {
        this.typeSel = typeSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.idListe.setText("Id. type : " + this.typeSel.getIdType());
        this.nomField.setText(this.typeSel.getNom());
        Color color = Color.web(this.typeSel.getCodeCouleur());
        this.couleurField.setValue(color);
    }

    @FXML
    protected void editer() throws SQLException {
        int id = this.typeSel.getIdType();
        String nom = this.nomField.getText();
        String couleur = this.couleurField.getValue().toString();
        if (nom.isEmpty() || couleur.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Type type = new Type(
                    id, nom, "#"+couleur.substring(2, 8)
            );
            TypeRepository typeRepository = new TypeRepository();
            Type nomCheck = typeRepository.getTypeByNom(nom);
            if (nomCheck == null || nomCheck.getNom().equals(nom)) {
                this.erreur.setVisible(false);
                boolean check = typeRepository.editer(type);
                if (check == true){
                    this.erreur.setText("Type modifié !");
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
        StartApplication.changeScene("type/lesTypesView.fxml");
    }

}
