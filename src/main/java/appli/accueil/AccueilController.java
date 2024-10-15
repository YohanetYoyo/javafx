package appli.accueil;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Liste;
import repository.ListeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ListeRepository listeRepository = new ListeRepository();
        ArrayList<Liste> listes;
        try {
            listes = listeRepository.getListes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Liste> observableList = tableauListe.getItems();
        observableList.setAll(listes);
    }

    @FXML
    protected void ajouterListe() throws IOException {
        StartApplication.changeScene("liste/ajouterListeView.fxml");
    }

    @FXML
    protected void lesTypes(){

    }

    @FXML
    protected void suppression() {
        
    }

    @FXML
    protected void deconnexion() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }

    @FXML
    protected void onListeSelection(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauListe.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Liste listeSel = tableauListe.getItems().get(indexLigne);
            System.out.println("Double-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + listeSel);
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            TablePosition cell = tableauListe.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Liste listeSel = tableauListe.getItems().get(indexLigne);
            System.out.println("Simple-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + listeSel);
        }
    }
}
