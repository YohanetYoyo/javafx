package appli.accueil;

import appli.StartApplication;
import appli.liste.EditerListeController;
import appli.tache.TachesController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.Liste;
import model.Utilisateur;
import model.UtilisateurConnecte;
import repository.ListeRepository;
import repository.TacheRepository;
import repository.UtilisateurListeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AccueilController implements Initializable {

    @FXML
    private TableView<Liste> tableauListe;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Label erreur;
    @FXML
    private Label welcomeText;

    private Liste listeSel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.welcomeText.setText("Bienvenue, "+UtilisateurConnecte.getInstance().getNom()+" "+UtilisateurConnecte.getInstance().getPrenom()+" !");
        String[][] colonnes = {
                {"Id. liste", "idListe"},
                {"Nom", "nom"},
                {"Utilisateur", "refUtilisateur"}
        };
        UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
        //Parcours de l'ensemble des colonnes
        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Liste,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Liste,String>(colonnes[i][1]));
            if (colonnes[i][1].equals("refUtilisateur")){
                maColonne.setCellValueFactory(colonne -> {
                    try {
                        Utilisateur utilisateur = utilisateurListeRepository.getUtilisateurByRefListe(colonne.getValue().getIdListe());
                        return new SimpleStringProperty(utilisateur.getNom()+" "+utilisateur.getPrenom());
                    } catch (SQLException e){
                        throw new RuntimeException(e);
                    }
                });
            }
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
    protected void lesTypes() throws IOException {
        StartApplication.changeScene("type/lesTypesView.fxml");
    }

    @FXML
    protected void modifierListe(){
        StartApplication.changeScene("liste/editerListeView",new EditerListeController(this.listeSel));
    }

    @FXML
    protected void suppression() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Supprimer cette liste");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette liste ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK) {
                TacheRepository tacheRepository = new TacheRepository();
                boolean check = false;
                try {
                    check = tacheRepository.supprimerTachesListe(listeSel.getIdListe());
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
                    boolean doubleCheck = false;
                    try {
                        doubleCheck = utilisateurListeRepository.suppression(this.listeSel.getIdListe());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    if (doubleCheck == true){
                        ListeRepository listeRepository = new ListeRepository();
                        boolean tripleCheck = false;
                        try {
                            tripleCheck = listeRepository.supprimer(this.listeSel);
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                        if (tripleCheck == true){
                            this.erreur.setText("Liste supprimée !");
                            this.erreur.setVisible(true);
                            this.supprimer.setDisable(true);
                            try {
                                StartApplication.changeScene("accueil/accueilView.fxml");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                } else {
                    this.erreur.setText("Erreur lors de la suppression.");
                    this.erreur.setVisible(true);
                }
            }
        });
    }

    @FXML
    protected void modifierInfos() throws IOException {
        StartApplication.changeScene("accueil/modifierInfosView.fxml");
    }

    @FXML
    protected void deconnexion() throws IOException {
        UtilisateurConnecte.clearInstance();
        StartApplication.changeScene("accueil/loginView.fxml");
    }

    @FXML
    protected void onListeSelection(MouseEvent event) throws SQLException {
        UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
        boolean estProprietaire;
        if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2){
            TablePosition cell = tableauListe.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Liste listeSel = tableauListe.getItems().get(indexLigne);
            System.out.println("Double-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + listeSel);
            estProprietaire = utilisateurListeRepository.estProprietaire(UtilisateurConnecte.getInstance().getIdUtilisateur(), listeSel.getIdListe());
            if (estProprietaire == true){
                StartApplication.changeScene("tache/tachesView",new TachesController(this.listeSel));
            } else {
                this.erreur.setText("Vous ne faites pas partie de la liste.");
                this.erreur.setVisible(true);
                this.modifier.setDisable(true);
                this.supprimer.setDisable(true);
            }
        } else if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 1) {
            TablePosition cell = tableauListe.getSelectionModel().getSelectedCells().get(0);
            int indexLigne = cell.getRow();
            TableColumn colonne = cell.getTableColumn();
            Liste listeSel = tableauListe.getItems().get(indexLigne);
            this.listeSel = listeSel;
            System.out.println("Simple-clique ligne " + indexLigne + ", colonne " + colonne.getText() + ": " + listeSel);
            estProprietaire = utilisateurListeRepository.estProprietaire(UtilisateurConnecte.getInstance().getIdUtilisateur(), listeSel.getIdListe());
            if (estProprietaire == true){
                this.erreur.setVisible(false);
                this.modifier.setDisable(false);
                this.supprimer.setDisable(false);
            } else {
                this.erreur.setText("Vous ne faites pas partie de la liste.");
                this.erreur.setVisible(true);
                this.modifier.setDisable(true);
                this.supprimer.setDisable(true);
            }
        }
    }
}
