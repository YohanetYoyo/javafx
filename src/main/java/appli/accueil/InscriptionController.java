package appli.accueil;

import appli.StartApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import model.Utilisateur;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import repository.UtilisateurRepository;

import java.io.IOException;
import java.sql.SQLException;

public class InscriptionController {

    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField confirmePasswordField;
    @FXML
    private Label erreur;

    @FXML
    protected void inscription() throws SQLException {
        String nom = this.nomField.getText();
        String prenom = this.prenomField.getText();
        String email = this.emailField.getText();
        String password = this.passwordField.getText();
        String confirmePassword = this.confirmePasswordField.getText();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || confirmePassword.isEmpty()) {
            this.erreur.setText("Veuillez remplir tous les champs.");
            this.erreur.setVisible(true);
        } else {
            if (password.equals(confirmePassword)) {
                this.erreur.setVisible(false);
                Utilisateur utilisateur = new Utilisateur(
                        nom, prenom, email, encoder.encode(password)
                );
                UtilisateurRepository utilisateurRepository = new UtilisateurRepository();
                Utilisateur emailCheck = utilisateurRepository.getUtilisateurByEmail(email);
                if (emailCheck != null) {
                    this.erreur.setText("L'adresse e-mail est déjà utilisé !");
                    this.erreur.setVisible(true);
                } else {
                    this.erreur.setVisible(false);
                    boolean check = utilisateurRepository.inscription(utilisateur);
                    if (check == true){
                        this.erreur.setText("Utilisateur bien ajouté !");
                        this.erreur.setVisible(true);
                        this.nomField.clear();
                        this.prenomField.clear();
                        this.emailField.clear();
                        this.passwordField.clear();
                        this.confirmePasswordField.clear();
                    } else {
                        this.erreur.setText("Erreur lors de l'ajout.");
                        this.erreur.setVisible(true);
                    }
                }
            } else {
                this.erreur.setText("Erreur, les mots de passe ne coïncident pas !");
                this.erreur.setVisible(true);
            }
        }
    }
    @FXML
    protected void retour() throws IOException {
        StartApplication.changeScene("accueil/loginView.fxml");
    }
}