package appli.liste;

import appli.StartApplication;
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
import model.UtilisateurListe;
import repository.UtilisateurListeRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MembresController implements Initializable {

    private Liste listeSel;

    @FXML
    private Label erreur;
    @FXML
    private Label titre;
    @FXML
    private TableView<Utilisateur> tableauMembres;
    @FXML
    private Button retirer;
    @FXML
    private TableView<Utilisateur> tableauUtilisateurs;
    @FXML
    private Button ajouter;

    private Utilisateur membreSel;
    private Utilisateur utilisateurSel;

    public MembresController(Liste listeSel) {
        this.listeSel = listeSel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.titre.setText("Membres de la liste « "+this.listeSel.getNom()+" »");
        String[][] colonnes = {
                {"Id.", "idUtilisateur"},
                {"Nom", "nom"},
                {"Prénom", "prenom"}
        };
        //Parcours de l'ensemble des colonnes
        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Utilisateur,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>(colonnes[i][1]));
            //Ajout de la colonne dans notre tableau
            tableauMembres.getColumns().add(maColonne);
        }
        ArrayList<Utilisateur> membres;
        UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
        try {
            membres = utilisateurListeRepository.getUtilisateursByRefListe(this.listeSel.getIdListe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Utilisateur> observableList = tableauMembres.getItems();
        observableList.setAll(membres);

        for (int i = 0 ; i < colonnes.length ; i++){
            //Création de la colonne avec le titre
            TableColumn<Utilisateur,String> maColonne = new TableColumn<>(colonnes[i][0]);
            //Ligne permettant la liaison automatique de la cellule avec la propriété donnée
            maColonne.setCellValueFactory(new PropertyValueFactory<Utilisateur,String>(colonnes[i][1]));
            //Ajout de la colonne dans notre tableau
            tableauUtilisateurs.getColumns().add(maColonne);
        }
        ArrayList<Utilisateur> utilisateurs;
        try {
            utilisateurs = utilisateurListeRepository.getUtilisateursNotInListe(this.listeSel.getIdListe());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        observableList = tableauUtilisateurs.getItems();
        observableList.setAll(utilisateurs);
    }

    @FXML
    protected void retirer() throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Retirer ce membre");
        alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cette liste ?");
        alert.setContentText("Cette action est irréversible !");
        alert.showAndWait().ifPresent(reponse -> {
            if (reponse == ButtonType.OK){
                UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
                boolean check = false;
                try {
                    check = utilisateurListeRepository.retirerMembre(new UtilisateurListe(this.membreSel.getIdUtilisateur(), this.listeSel.getIdListe()));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                if (check == true){
                    this.retirer.setDisable(true);
                    StartApplication.changeScene("liste/membresView", new MembresController(this.listeSel));
                } else {
                    this.erreur.setText("Erreur lors de la suppression.");
                    this.erreur.setVisible(true);
                }
            }
        });
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("liste/editerListeView",new EditerListeController(this.listeSel));
    }

    @FXML
    protected void onMembreSelection(MouseEvent event) throws IOException {
        TablePosition cell = tableauMembres.getSelectionModel().getSelectedCells().get(0);
        int indexLigne = cell.getRow();
        TableColumn colonne = cell.getTableColumn();
        Utilisateur membreSel = tableauMembres.getItems().get(indexLigne);
        this.membreSel = membreSel;
        System.out.println("Ligne " + indexLigne + ", colonne " + colonne.getText() + ":\n" + membreSel);
        if (UtilisateurConnecte.getInstance().getIdUtilisateur() == this.membreSel.getIdUtilisateur()) {
            this.retirer.setDisable(true);
        } else {
            this.retirer.setDisable(false);
        }
    }

    @FXML
    protected void onUtilisateurSelection(MouseEvent event) throws IOException {
        TablePosition cell = tableauUtilisateurs.getSelectionModel().getSelectedCells().get(0);
        int indexLigne = cell.getRow();
        TableColumn colonne = cell.getTableColumn();
        Utilisateur utilisateurSel = tableauUtilisateurs.getItems().get(indexLigne);
        this.utilisateurSel = utilisateurSel;
        System.out.println("Ligne " + indexLigne + ", colonne " + colonne.getText() + ":\n" + utilisateurSel);
            this.ajouter.setDisable(false);
    }

    @FXML
    protected void ajouter() throws IOException {
        UtilisateurListeRepository utilisateurListeRepository = new UtilisateurListeRepository();
        boolean check = false;
        try {
            check = utilisateurListeRepository.ajout(new UtilisateurListe(this.utilisateurSel.getIdUtilisateur(), this.listeSel.getIdListe()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (check == true){
            this.ajouter.setDisable(true);
            StartApplication.changeScene("liste/membresView", new MembresController(this.listeSel));
        } else {
            this.erreur.setText("Erreur lors de l'ajout.");
            this.erreur.setVisible(true);
        }
    }
}
