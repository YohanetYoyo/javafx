package appli.todolistjx.accueil;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {
    @FXML
    private Label erreur;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void setEmailField(TextField textField){
    }
    @FXML
    protected void setPasswordField(PasswordField passwordField){
    }

    @FXML
    protected void connexion() {
        System.out.println("Connexion");
        System.out.println(this.emailField.toString());
        System.out.println(this.passwordField.toString());
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