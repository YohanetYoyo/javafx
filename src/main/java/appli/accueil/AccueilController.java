package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Liste;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private TableView<Liste> tableauListe;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[][] colonnes = {
                {"Id. liste", "idListe"},
                {"Nom", "nom"}
        };
        //Parcours de l'ensemble des colonnes
        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Liste,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Liste,String>(colonnes[i][1]));
            //Ajout de la colonne dans notre tableau
            tableauListe.getColumns().add(maColonne);
        }
    }

    @FXML
    protected void ajouterListe() throws IOException {
        StartApplication.changeScene("liste/ajouterListeView.fxml");
    }

    @FXML
    protected void lesTypes(){

    }

    @FXML
    protected void deconnexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }

}
