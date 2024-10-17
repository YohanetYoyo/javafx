package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Utilisateur;
import model.UtilisateurConnecte;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.sql.SQLException;

public class PasswordForgottenController {
    @FXML
    private Label erreur;
    @FXML
    private Label emailText;
    @FXML
    private TextField emailField;
    @FXML
    private Button emailButton;
    @FXML
    private Label passwordText;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label confirmPasswordText;
    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    private Button passwordButton;

    @FXML
    protected void entrerEmail() throws SQLException {
        String email = emailField.getText();
        if (email.isEmpty()) {
            erreur.setText("Veuillez entrer un email.");
            erreur.setVisible(true);
        } else {
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur check = utilisateurRepository.getUtilisateurByEmail(email);
            if (check != null) {
                erreur.setVisible(false);
                emailText.setVisible(false);
                emailField.setVisible(false);
                emailField.setDisable(true);
                emailButton.setVisible(false);
                emailButton.setDisable(true);
                passwordText.setVisible(true);
                passwordField.setVisible(true);
                confirmPasswordText.setVisible(true);
                confirmPasswordField.setVisible(true);
                passwordField.setDisable(false);
                confirmPasswordField.setDisable(false);
                passwordButton.setVisible(true);
                passwordButton.setDisable(false);
            } else {
                erreur.setText("Adresse e-mail inexistante.");
                erreur.setVisible(true);
            }
        }
    }

    @FXML
    protected void confirmer() throws SQLException, IOException {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (password.isEmpty() || confirmPassword.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else if (!password.equals(confirmPassword)){
            this.erreur.setText("Les mots de passe ne correspondent pas.");
            this.erreur.setVisible(true);
        } else {
            UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
            Utilisateur check = utilisateurRepository.getUtilisateurByEmail(email);
            if (check != null){
                Utilisateur utilisateur = new Utilisateur(
                        check.getNom(), check.getPrenom(), check.getEmail(), encoder.encode(confirmPassword)
                );
                utilisateurRepository.modification(utilisateur);
                if (encoder.matches(confirmPassword, check.getMdp())){
                    this.erreur.setText("Erreur lors de la modification du mot de passe.");
                    this.erreur.setVisible(true);
                } else {
                    this.erreur.setText("Mot de passe bien modifié !");
                    this.erreur.setVisible(true);
                    emailField.clear();
                    passwordField.clear();
                    confirmPasswordField.clear();
                }
            }
        }
    }

    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}