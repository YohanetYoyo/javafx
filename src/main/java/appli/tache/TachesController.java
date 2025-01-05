package appli.tache;

import appli.StartApplication;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Liste;
import model.Type;
import model.Tache;
import repository.TypeRepository;
import repository.TacheRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TachesController implements Initializable {

    private Liste listeSel;
    private Tache tacheSel;

    @FXML
    private Label erreur;
    @FXML
    private Label titre;
    @FXML
    private TableView<Tache> tableauTaches;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;

    public TachesController(Liste listeSel) {
        this.listeSel = listeSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.titre.setText("Tâches de la liste « "+this.listeSel.getNom()+" »");
        String[][] colonnes = {
                {"Id. tâche", "idTache"},
                {"Nom", "nom"},
                {"État", "etat"},
                {"Type", "refType"}
        };
        TypeRepository typeRepository = new TypeRepository();
        //Parcours de l'ensemble des colonnes
        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Tache,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Tache,String>(colonnes[i][1]));
            switch (colonnes[i][1]){
                case "etat":
                    maColonne.setCellValueFactory(colonne -> {
                        String etat;
                        switch (colonne.getValue().getEtat()){
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
                        return new SimpleStringProperty(etat);
                    });
                    break;
                case "refType":
                    maColonne.setCellValueFactory(colonne -> {
                        try {
                            Type type = typeRepository.getTypeById(colonne.getValue().getRefType());
                            return new SimpleStringProperty(type.getNom());
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    });
                    break;
            }
            //Ajout de la colonne dans notre tableau
            tableauTaches.getColumns().add(maColonne);
        }
        TacheRepository tacheRepository = new TacheRepository();
        ArrayList<Tache> taches;
        try {
            taches = tacheRepository.getTachesByIdListe(this.listeSel.getIdListe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Tache> observableList = tableauTaches.getItems();
        observableList.setAll(taches);
    }

    @FXML
    protected void ajouterTache(){
        StartApplication.changeScene("tache/ajouterTacheView", new AjouterTacheController(this.listeSel));
    }

    @FXML
    protected void modifierTache(){
        StartApplication.changeScene("tache/editerTacheView",new EditerTacheController(this.listeSel, this.tacheSel));
    }

    @FXML
    protected void suppression(){

    }

    @FXML
    protected void onTacheSelection(MouseEvent event){
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauTaches.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Tache tacheSel = tableauTaches.getItems().get(indexLigne);
            System.out.println("Double-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + tacheSel);
            StartApplication.changeScene("tache/editerTacheView",new EditerTacheController(this.listeSel, this.tacheSel));
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            TablePosition cell = tableauTaches.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Tache tacheSel = tableauTaches.getItems().get(indexLigne);
            this.tacheSel = tacheSel;
            System.out.println("Simple-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + tacheSel);
            this.modifier.setDisable(false);
            this.supprimer.setDisable(false);
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }

}
