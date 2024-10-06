package appli.accueil;

import appli.StartApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    @FXML
    private Label erreur;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;

    @FXML
    protected void connexion() {
        String email = emailField.getText();
        String password = passwordField.getText();
        System.out.println("Connexion");
        System.out.println("Email : " + email + "\nMot de passe : " + password);
    }
    @FXML
    protected void motDePasseOublie() {
        System.out.println("Mot de passe oublié");
    }
    @FXML
    protected void inscription() throws IOException {
        StartApplication.changeScene("inscriptionView.fxml");
    }
    @FXML
    protected void setEmailField(ActionEvent actionEvent) {
    }
    @FXML
    protected void setPasswordField(ActionEvent actionEvent) {
    }
}