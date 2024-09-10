package appli.todolistjx.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label erreur;
    private TextField emailField;
    private PasswordField passwordField;

    @FXML
    protected void afficherEmail(){
        System.out.println(emailField.getText());
    }
    @FXML
    protected void afficherMdp(){
        System.out.println(passwordField.getText());
    }

    @FXML
    protected void connexion() {
        System.out.println("Connexion");
    }
    @FXML
    protected void motDePasseOublie() {
        System.out.println("Mot de passe oublié");
    }
    @FXML
    protected void inscription() {
        System.out.println("Inscription");
    }
}