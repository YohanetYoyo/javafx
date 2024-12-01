package appli.type;

import appli.StartApplication;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Type;
import repository.TypeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LesTypesController implements Initializable {

    @FXML
    private TableView<Type> tableauType;
    @FXML
    private Button supprimer;
    @FXML
    private Label erreur;

    private Type typeSel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String[][] colonnes = {
                {"Id. type", "idType"},
                {"Nom", "nom"},
                {"Code couleur", "codeCouleur"}
        };
        //Parcours de l'ensemble des colonnes
        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Type,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Type,String>(colonnes[i][1]));
            //Ajout de la colonne dans notre tableau
            if (colonnes[i][1].equals("codeCouleur")){
                maColonne.setCellFactory(colonne -> new TableCell<>(){
                    @Override
                    protected void updateItem(String codeCouleur, boolean empty){
                        super.updateItem(codeCouleur, empty);
                        if (empty || codeCouleur == null || codeCouleur.isBlank()){
                            setText(null);
                            setStyle("");
                        } else {
                            setText(codeCouleur);
                            setStyle("-fx-background-color: "+codeCouleur+";");
                        }
                    }
                });
            }
            tableauType.getColumns().add(maColonne);
        }
        TypeRepository typeRepository = new TypeRepository();
        ArrayList<Type> types;
        try {
            types = typeRepository.getTypes();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Type> observableList = tableauType.getItems();
        observableList.setAll(types);
    }

    @FXML
    protected void ajouterType() throws IOException {
        StartApplication.changeScene("type/ajouterTypeView.fxml");
    }
    @FXML
    protected void suppression() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer ce type");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce type ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK) {
                TypeRepository typeRepository = new TypeRepository();
                boolean check = false;
                try {
                    check = typeRepository.supprimer(this.typeSel);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    this.erreur.setText("Type supprimé !");
                    this.erreur.setVisible(true);
                    this.supprimer.setDisable(true);
                    try {
                        StartApplication.changeScene("type/lesTypesView.fxml");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    this.erreur.setText("Erreur lors de la suppression.");
                    this.erreur.setVisible(true);
                }
            }
        });
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }
    @FXML
    protected void onTypeSelection(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauType.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Type typeSel = tableauType.getItems().get(indexLigne);
            System.out.println("Double-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + typeSel);
            StartApplication.changeScene("type/editerTypeView",new EditerTypeController(typeSel));
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            TablePosition cell = tableauType.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Type typeSel = tableauType.getItems().get(indexLigne);
            this.typeSel = typeSel;
            System.out.println("Simple-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + typeSel);
            this.supprimer.setDisable(false);
        }
    }
}
