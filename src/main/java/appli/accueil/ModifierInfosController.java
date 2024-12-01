package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Utilisateur;
import model.UtilisateurConnecte;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import repository.UtilisateurRepository;

import java.io.IOException;
import java.sql.SQLException;

public class ModifierInfosController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label erreur;

    @FXML
    public void initialize() {
        this.nomField.setPromptText(UtilisateurConnecte.getInstance().getNom());
        this.prenomField.setPromptText(UtilisateurConnecte.getInstance().getPrenom());
        this.emailField.setPromptText(UtilisateurConnecte.getInstance().getEmail());
    }

    @FXML
    protected void confirmer() throws SQLException, IOException {
        String nom = this.nomField.getText();
        String prenom = this.prenomField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (nom.isEmpty() && prenom.isEmpty() && email.isEmpty() && password.isEmpty()) {
            this.erreur.setText("Veuillez remplir au minimum un champ.");
            this.erreur.setVisible(true);
        } else {
            this.erreur.setVisible(false);
            Utilisateur utilisateur = new Utilisateur(
                    nom, prenom, email, UtilisateurConnecte.getInstance().getMdp()
            );
            if (nom.isEmpty()) {
                utilisateur.setNom(UtilisateurConnecte.getInstance().getNom());
            }
            if (prenom.isEmpty()) {
                utilisateur.setPrenom(UtilisateurConnecte.getInstance().getPrenom());
            }
            if (email.isEmpty()) {
                utilisateur.setEmail(UtilisateurConnecte.getInstance().getEmail());
            }
            if (!encoder.matches(password, utilisateur.getMdp()) && !password.isEmpty()) {
                utilisateur.setMdp(encoder.encode(password));
            }
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur emailCheck = utilisateurRepository.getUtilisateurByEmail(email);
            if (emailCheck == null || emailCheck.getEmail().equals(email)) {
                this.erreur.setVisible(false);
                boolean check = utilisateurRepository.modification(utilisateur);
                if (check == true){
                    UtilisateurConnecte.clearInstance();
                    UtilisateurConnecte.initInstance(utilisateur);
                    StartApplication.changeScene("accueil/loginView.fxml");
                } else {
                    this.erreur.setText("Erreur lors de la modification.");
                    this.erreur.setVisible(true);
                }
            } else {
                this.erreur.setText("L'e-mail a déjà été pris !");
                this.erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/accueilView.fxml");
    }
}