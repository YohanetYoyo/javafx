package appli.tache;

import appli.StartApplication;
import javafx.beans.property.SetProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import java.sql.SQLException;
import java.util.ArrayList;

public class TachesController {

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

    @FXML
    public void initialize() {
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
            if (colonnes[i][1].equals("refType")){
                maColonne.setCellValueFactory(colonne -> {
                    try {
                        Type type = typeRepository.getTypeById(colonne.getValue().getRefType());
                        return new SimpleStringProperty(type.getNom());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
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

    }

    @FXML
    protected void modifierTache(){

    }

    @FXML
    protected void suppression(){

    }

    @FXML
    protected void onTacheSelection(MouseEvent event){

    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }
}
