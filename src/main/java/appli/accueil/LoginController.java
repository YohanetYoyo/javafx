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
        System.out.println("Connexion");
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